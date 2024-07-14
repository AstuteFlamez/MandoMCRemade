package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.handlers.Handlers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Crush {

    public static void crush(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.orbitingBeam(player, "crushI",
                        config.getInt("CrushIRange"),
                        Handlers.hexToColor(config.getString("CrushHexCode1")),
                        Handlers.hexToColor(config.getString("CrushHexCode2")),
                        Handlers.hexToColor(config.getString("CrushHexCode3")),
                        Handlers.hexToColor(config.getString("CrushHexCode4")));
                break;
            case 2:
                int radius = config.getInt("CrushIIRadius");
                int damage = config.getInt("CrushIIDamage");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.damage(damage, player);
                    }
                }
                Particles.circleInwards(player.getLocation(),
                        radius,
                        Handlers.hexToColor(config.getString("CrushHexCode1")),
                        Handlers.hexToColor(config.getString("CrushHexCode2")));
                break;
        }
    }

}
