package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Leap {

    public static void leap(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                player.setVelocity(player.getLocation().getDirection().multiply(plugin.getConfig().getInt("LeapIVelocity")).setY(1));
                break;
            case 2:
                player.setVelocity(player.getLocation().getDirection().multiply(plugin.getConfig().getInt("LeapIIVelocity")).setY(1));
        }
        new BukkitRunnable(){

            @Override
            public void run() {
                if(player.getLocation().subtract(0,1,0).getBlock().getType() == Material.AIR){
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Utilities.hexToColor(config.getString("LeapHexCode1")), Utilities.hexToColor(config.getString("LeapHexCode1")), 1);
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, player.getLocation(), 1, dustOptions);
                }else{
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 5, 1);
    }

}
