package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Drain {

    public static void drain(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.oscillatingBeam(player, "drainI",
                        config.getInt("DrainIRange"),
                        Utilities.hexToColor(config.getString("DrainHexCode1")),
                        Utilities.hexToColor(config.getString("DrainHexCode2")));
                break;
            case 2:
                int radius = config.getInt("DrainIIRadius");
                int damage = config.getInt("DrainIIDamage");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.damage(damage, player);
                        double health = player.getHealth();
                        if (player.getHealth() < (20.0-damage)) {
                            player.setHealth(health + damage);
                        } else if (player.getHealth() >= (20-damage) && player.getHealth() <= 20.00) {
                            player.setHealth(20.00);
                        }
                    }
                }
                Particles.spiralGround(player.getLocation(),
                        Utilities.hexToColor(config.getString("DrainHexCode1")),
                        Utilities.hexToColor(config.getString("DrainHexCode2")));
                break;
        }
    }

}
