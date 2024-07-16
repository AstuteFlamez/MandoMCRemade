package net.mandomc.mandomcremade.koth;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class KOTHManager {
    private final MandoMCRemade plugin;
    private final Location kothLocation;
    private final double captureRadius;
    private boolean kothActive = false;
    private final HashMap<UUID, Integer> playerPoints = new HashMap<>();
    private BossBar bossBar;
    private UUID currentLeader = null;

    public KOTHManager(MandoMCRemade plugin, Location kothLocation, double captureRadius) {
        this.plugin = plugin;
        this.kothLocation = kothLocation;
        this.captureRadius = captureRadius;
    }

    public void startKOTH() {
        kothActive = true;
        playerPoints.clear();
        bossBar = Bukkit.createBossBar("KOTH Event", BarColor.BLUE, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        bossBar.setProgress(1.0); // Start with a full bar
        Bukkit.broadcastMessage("KOTH event has started! Head to the hill to capture the point!");

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
        }.runTaskTimer(plugin, 0L, 20L); // Run every second (20 ticks)
    }

    public void endKOTH() {
        kothActive = false;
        if (bossBar != null) {
            bossBar.removeAll();
        }
        String winner = currentLeader != null ? Bukkit.getPlayer(currentLeader).getName() : "No one";
        Bukkit.broadcastMessage("KOTH event has ended! Congratulations to " + winner + " for winning the event!");
    }

    private void updatePlayerPoints() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(kothLocation) <= captureRadius) {
                UUID playerId = player.getUniqueId();
                int newPoints = playerPoints.getOrDefault(playerId, 0) + 1;
                playerPoints.put(playerId, newPoints);

                if (newPoints >= 100) {
                    currentLeader = playerId;
                    endKOTH();
                    return;
                }
            }
        }
    }

    private void updateBossBar() {
        UUID newLeader = null;
        int maxPoints = -1;

        for (UUID playerId : playerPoints.keySet()) {
            int points = playerPoints.get(playerId);
            if (points > maxPoints) {
                maxPoints = points;
                newLeader = playerId;
            }
        }

        currentLeader = newLeader;

        if (currentLeader != null) {
            Player leader = Bukkit.getPlayer(currentLeader);
            if (leader != null) {
                double progress = 1.0 - (playerPoints.get(currentLeader) / 100.0); // Progress goes backwards
                bossBar.setProgress(Math.max(0, progress)); // Ensure the progress is not negative
                bossBar.setTitle(leader.getName() + " is leading with " + playerPoints.get(currentLeader) + " points.");
            }
        }
    }

    private void spawnCaptureParticles() {
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
            double x = captureRadius * Math.cos(theta);
            double z = captureRadius * Math.sin(theta);
            Location particleLocation = kothLocation.clone().add(x, 0, z);
            kothLocation.getWorld().spawnParticle(Particle.DRIPPING_HONEY, particleLocation, 1);
        }
    }

    public boolean isKOTHActive() {
        return kothActive;
    }
}
