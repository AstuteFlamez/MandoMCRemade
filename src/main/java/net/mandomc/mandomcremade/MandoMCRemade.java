package net.mandomc.mandomcremade;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.mandomc.mandomcremade.commands.*;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.listeners.*;
import net.mandomc.mandomcremade.tasks.KothScheduler;
import net.mandomc.mandomcremade.tasks.ShipsRunnable;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class MandoMCRemade extends JavaPlugin implements Listener {

    public static MandoMCRemade instance;
    public static String kothTime = Messages.str("The next KOTH will be in ");
    private final HashMap<UUID, Long> lightsaberCooldown;

    public MandoMCRemade() {
        lightsaberCooldown = new HashMap<>();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("[MandoMC]: Plugin is disabled");

        instance = this;

        new ShipsRunnable(this).runTaskTimer(this, 0L, 1L);
        KothScheduler kothScheduler = new KothScheduler();
        kothScheduler.start();
        kothTime += kothScheduler.timeUntilNextTask();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        WarpConfig.setup();
        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();
        SaberConfig.setup();
        SaberConfig.get().options().copyDefaults(true);
        SaberConfig.save();

        getCommand("mmc").setExecutor(new MMC(this));
        getCommand("vehicle").setExecutor(new Vehicle());

        getServer().getPluginManager().registerEvents(new VehicleSafetyListener(), this);
        getServer().getPluginManager().registerEvents(new WarpGUIListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new SaberThrowListener(lightsaberCooldown, this), this);
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
