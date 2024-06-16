package net.mandomc.mandomcremade;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import net.mandomc.mandomcremade.commands.*;
import net.mandomc.mandomcremade.config.PunishmentConfig;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.enchants.JedisLuckEnchant;
import net.mandomc.mandomcremade.listeners.*;
import net.mandomc.mandomcremade.objects.ConvoTrait;
import net.mandomc.mandomcremade.tasks.KothScheduler;
import net.mandomc.mandomcremade.tasks.ShipsRunnable;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public final class MandoMCRemade extends JavaPlugin implements Listener {

    public static MandoMCRemade instance;
    public static String kothTime = Messages.str("The next KOTH will be in ");
    private final HashMap<UUID, Long> lightsaberCooldown;
    private File path = new File(this.getDataFolder() + "/custom_items");
    private final ArrayList<ItemStack> customItemsArray = new ArrayList<>();

    public MandoMCRemade() {
        lightsaberCooldown = new HashMap<>();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("[MandoMC]: Plugin is disabled");

        instance = this;

        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(ConvoTrait.class));

        new ShipsRunnable(this).runTaskTimer(this, 0L, 1L);
        KothScheduler kothScheduler = new KothScheduler();
        kothScheduler.start();
        kothTime += kothScheduler.timeUntilNextTask();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        PunishmentConfig.setup();
        PunishmentConfig.get().options().copyDefaults(true);
        WarpConfig.setup();
        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();
        SaberConfig.setup();
        SaberConfig.get().options().copyDefaults(true);
        SaberConfig.save();

        getCommand("mmctest").setExecutor(new Test());
        getCommand("punish").setExecutor(new Punish());
        getCommand("summonainpc").setExecutor(new SummonAiNPC());
        getCommand("warp").setExecutor(new Warp());
        getCommand("forcestartkoth").setExecutor(new ForceStartKoth());
        getCommand("yaw").setExecutor(new Yaw());
        getCommand("pitch").setExecutor(new Pitch());
        getCommand("mmcreload").setExecutor(new Reload(this));
        getCommand("recipes").setExecutor(new Recipes());
        getCommand("give").setExecutor(new Give());
        getCommand("get").setExecutor(new Get());
        getCommand("vehicle").setExecutor(new Vehicle());
        getCommand("enchant").setExecutor(new Enchant());
        getCommand("maintenance").setExecutor(new Maintenance(this));

        getServer().getPluginManager().registerEvents(new PunishGUIListener(this), this);
        getServer().getPluginManager().registerEvents(new NPCListeners(this), this);
        getServer().getPluginManager().registerEvents(new VehicleSafetyListener(), this);
        getServer().getPluginManager().registerEvents(new WarpGUIListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new SaberThrowListener(lightsaberCooldown, this), this);
        getServer().getPluginManager().registerEvents(new JedisLuckEnchant(0.00), this);
        getServer().getPluginManager().registerEvents(this, this);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Status.Server.SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing packet = event.getPacket().getServerPings().read(0);

                packet.setPlayersOnline(66);
                packet.setPlayersMaximum(66);

                if (getConfig().getBoolean("Maintenance")) {
                    packet.setVersionProtocol(1);
                    packet.setVersionName("Maintenance");;
                    packet.setMotD(Messages.str(getConfig().getString("MaintenanceMOTD")));
                } else {
                    packet.setVersionName(getConfig().getString("VersionName"));;
                    packet.setMotD(Messages.str(getConfig().getString("MOTD")));
                }

                // Create WrappedGameProfile objects for the custom player list
                List<WrappedGameProfile> profiles = new ArrayList<>();
                for (int i = 0; i < getConfig().getStringList("ServerListHoverText").size(); i++) {
                    profiles.add(new WrappedGameProfile(String.valueOf(i + 1), getConfig().getStringList("ServerListHoverText").get(i)));
                }
                packet.setPlayers(profiles);

                event.getPacket().getServerPings().write(0, packet);
            }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        getServer().getConsoleSender().sendMessage("[MandoMC]: Plugin is disabled");

    }

    public static MandoMCRemade getInstance(){
        return instance;
    }

}
