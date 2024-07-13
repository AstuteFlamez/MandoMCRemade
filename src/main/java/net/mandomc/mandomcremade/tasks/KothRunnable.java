package net.mandomc.mandomcremade.tasks;

import net.mandomc.mandomcremade.objects.Koth;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class KothRunnable extends BukkitRunnable {

    private final JavaPlugin plugin;
    private final Koth koth;
    private int time = 0;
    private final UUID uuid;
    private final BossBar bossBar;
    private final Set<Player> playersInContestion = new HashSet<>();
    private int playerTime = 0;
    private final double x1, x2, z1, z2;

    public KothRunnable(JavaPlugin plugin) {
        this.plugin = plugin;
        uuid = UUID.randomUUID();
        koth = new Koth(uuid);
        bossBar = koth.getBossBar();
        FileConfiguration config = plugin.getConfig();
        x1 = config.getDouble("KothX1");
        x2 = config.getDouble("KothX2");
        z1 = config.getDouble("KothZ1");
        z2 = config.getDouble("KothZ2");
    }

    @Override
    public void run() {
        time++;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isPlayerInKothArea(player) && player.getGameMode() == GameMode.SURVIVAL && !player.isInsideVehicle()) {
                handlePlayerInKothArea(player);
            }
        }

        if (time >= 3600) {
            Bukkit.broadcastMessage(Messages.str("&7It took too long for someone to capture KOTH!"));
            cancel();
        }
    }

    private boolean isPlayerInKothArea(Player player) {
        String world = player.getWorld().getName();
        double x = player.getLocation().getX();
        double z = player.getLocation().getZ();
        return world.equals("Hoth") && (x <= x1 && x >= x2) && (z <= z1 && z >= z2);
    }

    private void handlePlayerInKothArea(Player player) {
        bossBar.addPlayer(player);
        bossBar.setVisible(true);

        if (!playersInContestion.contains(player)) {
            playersInContestion.add(player);
        } else {
            if (playersInContestion.size() > 1) {
                bossBar.setTitle("&4&lMass Contest");
                playerTime = 0;
            } else {
                updatePlayerTime(player);
            }
        }
    }

    private void updatePlayerTime(Player player) {
        playerTime++;

        switch (playerTime) {
            case 20:
                Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " is &a20% &7done capturing KOTH!"));
                break;
            case 50:
                Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " is &a50% &7done capturing KOTH!"));
                break;
            case 80:
                Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " is &a80% &7done capturing KOTH!"));
                break;
            case 100:
                Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " captured KOTH!"));
                bossBar.removeAll();
                bossBar.setVisible(false);
                cancel();
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "excellentcrates key give " + player.getName() + " koth_key 1");
                break;
        }

        bossBar.setProgress((double) playerTime / 100);
        bossBar.setTitle("&4&lSolo Contest - " + player.getName());
    }
}
