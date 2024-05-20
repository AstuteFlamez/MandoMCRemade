package net.mandomc.mandomcremade.tasks;

import net.mandomc.mandomcremade.objects.Koth;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class KothRunnable extends BukkitRunnable {

    private final JavaPlugin plugin;
    Koth koth;
    private int time = 0;
    UUID uuid;
    BossBar bossBar;
    ArrayList<Player> playersInContestion = new ArrayList<>();
    int playerTime = 0;

    public KothRunnable(JavaPlugin plugin) {
        this.plugin = plugin;
        uuid = UUID.randomUUID();
        koth = new Koth(uuid);
        bossBar = koth.getBossBar();
    }

    @Override
    public void run() {

        time++;

        for (Player player : Bukkit.getOnlinePlayers()) {

            String world = player.getWorld().getName();
            double x = player.getLocation().getX();
            double z = player.getLocation().getZ();
            bossBar.addPlayer(player);
            bossBar.setVisible(true);

            if (world.equals("Hoth") && ((x <= -128 && x >= -143) && (z <= 143 && z >= 128)) && player.getGameMode() == GameMode.SURVIVAL && !player.isInsideVehicle()) {
                if (!playersInContestion.contains(player)) {
                    playersInContestion.add(player);
                } else {
                    if(playersInContestion.size()>1) {
                        bossBar.setTitle("&4&lMass Contest");
                        playerTime = 0;
                    }else {
                        playerTime++;

                        if (playerTime == 20) {
                            Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " is &a20% &7done capturing KOTH!"));
                        } else if (playerTime == 50) {
                            Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " is &a50% &7done capturing KOTH!"));
                        } else if (playerTime == 80) {
                            Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " is &a80% &7done capturing KOTH!"));
                        } else if (playerTime == 100) {
                            Bukkit.broadcastMessage(Messages.str("&7" + player.getName() + " captured KOTH!"));
                            bossBar.removeAll();
                            bossBar.setVisible(false);
                            cancel();
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "excellentcrates key give " + player.getName() + " koth_key 1");
                        }

                        bossBar.setProgress((double) playerTime / 100);
                        bossBar.setTitle("&4&lSolo Contest - " + player.getName());
                    }
                }
            }
        }
        if (time >= 3600) {
            Bukkit.broadcastMessage(Messages.str("&7It took too long for someone to capture KOTH!"));
            cancel();
        }
    }
}
