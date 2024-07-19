package net.mandomc.mandomcremade.managers;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.LangConfig;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class KOTHManager implements Listener {

    private final MandoMCRemade plugin;
    private final Location kothLocation;
    private final double captureRadius;
    private boolean kothActive = false;
    private final HashMap<UUID, Integer> playerPoints = new HashMap<>();
    private BossBar bossBar;
    private UUID currentLeader = null;
    private final ArrayList<Player> players;
    FileConfiguration config = LangConfig.get();
    String prefix = config.getString("EventPrefix");

    public KOTHManager(MandoMCRemade plugin, Location kothLocation, double captureRadius) {
        this.plugin = plugin;
        this.kothLocation = kothLocation;
        this.captureRadius = captureRadius;

        players = new ArrayList<>();
    }

    public void startKOTH() {
        kothActive = true;
        playerPoints.clear();

        createBossBar();

        String start = str("&7The &b&lKOTH &7Event has started!");
        Bukkit.broadcastMessage(prefix + start);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!kothActive) {
                    this.cancel();
                    return;
                }

                updatePlayerPoints();
                updateBossBar();
                spawnCaptureParticles();
            }
        }.runTaskTimer(plugin, 0L, 20L); // Run every second (20 ticks = 1 second)
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(isKOTHActive()) createBossBar();
    }

    private void createBossBar(){
        World world = kothLocation.getWorld();
        String worldName = world.getName();
        double x = kothLocation.getX();
        double z = kothLocation.getZ();
        String name = str("&b&lKOTH &7is uncontested! &c&lPlanet: " + worldName + ", X: " + x + ", Z: " + z);
        bossBar = Bukkit.createBossBar(name, BarColor.BLUE, BarStyle.SEGMENTED_20);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        bossBar.setProgress(1.0); // Start with a full bar
    }

    private void updatePlayerPoints() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.getWorld() != kothLocation.getWorld()) return;
            if (player.getLocation().distance(kothLocation) <= captureRadius) {
                if(!players.contains(player)) players.add(player);

                if(players.size()>1) return;

                UUID playerId = player.getUniqueId();
                int newPoints = playerPoints.getOrDefault(playerId, 30) - 1;
                playerPoints.put(playerId, newPoints);

                if (newPoints <= 0) {
                    currentLeader = playerId;
                    endKOTH();
                    return;
                }
            }else{
                players.remove(player);
                UUID playerId = player.getUniqueId();
                playerPoints.put(playerId, 30);
            }
        }
    }

    private void updateBossBar() {
        UUID newLeader = null;
        int minPoints = 101;

        for (UUID playerId : playerPoints.keySet()) {
            int points = playerPoints.get(playerId);
            if (points < minPoints) {
                minPoints = points;
                newLeader = playerId;
            }
        }

        currentLeader = newLeader;

        if (currentLeader != null) {
            Player leader = Bukkit.getPlayer(currentLeader);
            if (leader != null) {

                double progress = (playerPoints.get(currentLeader) / 30.0);
                bossBar.setProgress(Math.max(0, progress));
                // Ensure the progress is not negative
                int seconds = playerPoints.get(currentLeader);
                String plural = "";
                if(seconds==1) plural = "s";

                String title = str("&r" + leader.getName() + " &7has &6" + seconds + " &7second" + plural + " left to capture &b&lKOTH!");
                if(players.size()>1) {
                    title = str("&b&lKOTH &7is contested by multiple players.");
                }else if(players.isEmpty()) {
                    World world = kothLocation.getWorld();
                    String worldName = world.getName();
                    double x = kothLocation.getX();
                    double z = kothLocation.getZ();
                    title = str("&b&lKOTH &7is uncontested! &c&lPlanet: " + worldName + ", &c&lX: " + x + ", &b&lZ: " + z);
                }

                bossBar.setTitle(title);
            }
        }
    }

    private void spawnCaptureParticles() {
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
            double x = captureRadius * Math.cos(theta);
            double z = captureRadius * Math.sin(theta);
            Location particleLocation = kothLocation.clone().add(x, 0, z);
            World world = kothLocation.getWorld();
            assert world != null;
            world.spawnParticle(Particle.DRIPPING_HONEY, particleLocation, 1);
        }
    }

    public boolean isKOTHActive() {
        return kothActive;
    }

    public void endKOTH() {
        kothActive = false;
        if (bossBar != null) {
            bossBar.removeAll();
        }
        Player player = Bukkit.getPlayer(currentLeader);
        if(player == null) return;
        String name = player.getName();

        String winner = currentLeader != null ? name : "No one";
        String end = str("&b&lKOTH &7event has ended! Congratulations to " + winner + " for winning the event!");
        Bukkit.broadcastMessage(prefix + end);
    }
}
