package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static net.mandomc.mandomcremade.utility.Particles.plugin;
import static net.mandomc.mandomcremade.utility.Utilities.genVec;

public class ForceMethods {

    public static void leap(Player player, int level){
        // Set the double jump velocity
        switch(level){
            case 1:
                player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
                break;
            case 2:
                player.setVelocity(player.getLocation().getDirection().multiply(5).setY(1));
        }

        Particles.playSpiralHelix(player.getLocation());
    }

    public static void pull(Player player, int level){
        // Set the double jump velocity
        switch(level){
            case 1:
                Particles.playOscillatingBeam(player, "pullI", plugin.getConfig().getInt("PullIRange"));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PullIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity2) {
                        Vector vector = genVec(livingEntity2.getLocation(), player.getLocation());
                        livingEntity2.setVelocity(vector);
                    }
                }
                Particles.playCircleInwards(player, player.getLocation(), radius);
        }
    }

    public static void push(Player player, int level){
        // Set the double jump velocity
        switch(level){
            case 1:
                Particles.playOrbitingBeam(player, "pushI", plugin.getConfig().getInt("PushIRaange"));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PushIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        Vector vector = genVec(player.getLocation(), livingEntity.getLocation());
                        vector.setY(1);
                        livingEntity.setVelocity(vector);
                    }
                }
                Particles.playCircleOutwards(player, player.getLocation(), radius);
        }
    }

}
