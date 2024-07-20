package net.mandomc.mandomcremade;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.mandomc.mandomcremade.commands.*;
import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.managers.KOTHManager;
import net.mandomc.mandomcremade.listeners.*;
import net.mandomc.mandomcremade.guis.GUIListener;
import net.mandomc.mandomcremade.managers.EnergyManager;
import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.managers.VehicleManager;
import net.mandomc.mandomcremade.objects.Energy;
import net.mandomc.mandomcremade.objects.Vehicle;
import net.mandomc.mandomcremade.tasks.VehicleTask;
import net.mandomc.mandomcremade.utility.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getConsoleSender;

public final class MandoMCRemade extends JavaPlugin implements Listener {

    public static MandoMCRemade instance;
    private Database database;
    private KOTHManager kothManager;
    private final HashMap<UUID, Long> lightsaberCooldown;
    public static ArrayList<Energy> energyList;

    public MandoMCRemade() {
        lightsaberCooldown = new HashMap<>();
    }


    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getConsoleSender().sendMessage("[MandoMC]: Plugin is enabled");

        instance = this;

        energyList = new ArrayList<>();

        setUpConfigs();

        getServer().getPluginManager().registerEvents(this, this);


        GUIManager guiManager = new GUIManager();
        GUIListener guiListener = new GUIListener(guiManager);
        Bukkit.getPluginManager().registerEvents(guiListener, this);
        EnergyManager energyManager = new EnergyManager(this);

        this.database = new Database();

        try {
            this.database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.database.initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Recipes.registerRecipes();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, database), this);
        getServer().getPluginManager().registerEvents(new SaberThrowListener(lightsaberCooldown, this, database), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PlayerClickListener(), this);
        getServer().getPluginManager().registerEvents(new EnergyManager(this), this);
        getServer().getPluginManager().registerEvents(new VehicleListener(), this);

        setUpKOTH();

        setUpCommands(guiManager);

        setUpServerList();

        new VehicleTask().runTaskTimer(this, 0L, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        getServer().getConsoleSender().sendMessage("[MandoMC]: Plugin is disabled");
        kothManager.endKOTH();

        for(Vehicle vehicle : VehicleManager.vehicles){
            VehicleManager.removeVehicle(vehicle);
        }

    }

    public static MandoMCRemade getInstance(){
        return instance;
    }

    public void setUpConfigs(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        LangConfig.setup();
        LangConfig.get().options().copyDefaults(true);
        LangConfig.save();

        WarpConfig.setup();
        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();

        SaberConfig.setup();
        SaberConfig.get().options().copyDefaults(true);
        SaberConfig.save();
    }

    public void setUpCommands(GUIManager guiManager){
        getCommand("give").setExecutor(new GiveCommand());
        getCommand("perk").setExecutor(new PerkCommand(this.database));
        getCommand("get").setExecutor(new GetCommand(guiManager));
        getCommand("yaw").setExecutor(new YawCommand());
        getCommand("pitch").setExecutor(new PitchCommand());
        getCommand("reload").setExecutor(new ReloadCommand(this));
        getCommand("maintenance").setExecutor(new MaintenanceCommand(this));
        getCommand("recipes").setExecutor(new RecipesCommand(guiManager));
    }

    public void setUpKOTH(){
        //Define the KOTH location and capture radius
        String world = getConfig().getString("KOTHWorld");
        int x = getConfig().getInt("KOTHx");
        int y = getConfig().getInt("KOTHy");
        int z = getConfig().getInt("KOTHz");
        double radius = getConfig().getDouble("KOTHRadius");

        assert world != null;
        Location kothLocation = new Location(Bukkit.getWorld(world), x, y, z);

        kothManager = new KOTHManager(this, kothLocation, radius);
        this.getCommand("koth").setExecutor(new KOTHCommand(kothManager));

        getServer().getPluginManager().registerEvents(kothManager, this);

        // Schedule KOTH event every 4 hours
        new BukkitRunnable() {
            @Override
            public void run() {
                if(kothManager.isKOTHActive()){
                    ConsoleCommandSender console = Bukkit.getConsoleSender();
                    console.sendMessage("[MMC] Could not start scheduled KOTH. There is already an active instance.");
                    cancel();
                }
                kothManager.startKOTH();
            }
        }.runTaskTimer(this, TimeUnit.MINUTES.toSeconds(0) * 20, TimeUnit.HOURS.toSeconds(4) * 20);
    }

    public void setUpServerList(){
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Status.Server.SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing packet = event.getPacket().getServerPings().read(0);

                packet.setPlayersMaximum(66);

                if (getConfig().getBoolean("Maintenance")) {
                    packet.setVersionProtocol(1);
                    packet.setVersionName("Maintenance");
                    packet.setMotD(str(getConfig().getString("MaintenanceMOTD")));
                } else {
                    packet.setVersionName(getConfig().getString("VersionName"));
                    packet.setMotD(str(getConfig().getString("MOTD")));
                }

                // Create WrappedGameProfile objects for the custom player list
                List<WrappedGameProfile> profiles = new ArrayList<>();
                for (int i = 0; i < getConfig().getStringList("ServerListHoverText").size(); i++) {
                    String hoverText = getConfig().getStringList("ServerListHoverText").get(i);
                    UUID uuid = UUID.randomUUID(); // Generate a random UUID
                    profiles.add(new WrappedGameProfile(uuid.toString(), hoverText));
                }

                packet.setPlayers(profiles);

                event.getPacket().getServerPings().write(0, packet);
            }
        });
    }

    public static String str(String string){
        if (string == null) return "";
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
