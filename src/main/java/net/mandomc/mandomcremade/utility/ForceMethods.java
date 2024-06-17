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
        ForcePowers.leap(player, level);
    }

    public static void pull(Player player, int level){
        // Set the double jump velocity
        switch(level){
            case 1:
                Particles.oscillatingBeam(player, "pullI", plugin.getConfig().getInt("PullIRange"));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PullIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity2) {
                        ForcePowers.pull(livingEntity2, player);
                    }
                }
                Particles.circleInwards(player, player.getLocation(), radius);
        }
    }

    public static void push(Player player, int level){
        // Set the double jump velocity
        switch(level){
            case 1:
                Particles.orbitingBeam(player, "pushI", plugin.getConfig().getInt("PushIRange"));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PushIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        ForcePowers.push(livingEntity, player);
                    }
                }
                Particles.circleOutwards(player, player.getLocation(), radius);
        }
    }

    public static void dash(Player player){
        Particles.beam(player);
        ForcePowers.dash(player);
    }

}
