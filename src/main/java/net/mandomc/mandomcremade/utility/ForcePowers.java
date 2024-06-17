package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static net.mandomc.mandomcremade.utility.Utilities.genVec;

public class ForcePowers {

    public static void leap(Player player, int level){
        switch(level){
            case 1:
                player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
                break;
            case 2:
                player.setVelocity(player.getLocation().getDirection().multiply(5).setY(1));
        }
        new BukkitRunnable(){

            @Override
            public void run() {
                if(player.getLocation().subtract(0,1,0).getBlock().getType() == Material.AIR){
                    player.getWorld().spawnParticle(Particle.FIREWORK, player.getLocation(), 0);
                }else{
                    cancel();
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 5, 1);
    }

    public static void pull(LivingEntity livingEntity, Player player){
        Vector vector = genVec(livingEntity.getLocation(), player.getLocation());
        livingEntity.setVelocity(vector);
    }

    public static void push(LivingEntity livingEntity, Player player){
        Vector vector = genVec(player.getLocation(), livingEntity.getLocation());
        vector.setY(1);
        livingEntity.setVelocity(vector);
    }

    public static void dash(Player player){
        int distance = MandoMCRemade.getInstance().getConfig().getInt("DashIRange");
        Location originalLocation = player.getLocation();
        Vector direction = originalLocation.getDirection().normalize();

        // Calculate the destination location
        double dx = direction.getX() * distance;
        double dy = direction.getY() * distance;
        double dz = direction.getZ() * distance;

        Location destination = originalLocation.clone().add(dx, dy, dz);

        // Check for blocks in the way and adjust destination to the closest non-solid block
        int i = 0;
        while (destination.getBlock().getType().isSolid() && i<=distance) {
            destination.add(direction.multiply(-1)); // Move backwards one step
            i++;
            Bukkit.broadcastMessage("ea");
        }

        // Set the final location
        player.teleport(destination);
    }
}
