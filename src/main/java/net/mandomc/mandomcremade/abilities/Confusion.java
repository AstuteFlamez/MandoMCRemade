package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.handlers.Handlers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Confusion {

    public static void confusion(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        int radius;
        switch(level){
            case 1:
                radius = MandoMCRemade.getInstance().getConfig().getInt("ConfusionIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, plugin.getConfig().getInt("ConfusionIDuration")*20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, plugin.getConfig().getInt("ConfusionIDuration")*20, 1));
                    }
                }
                break;
            case 2:
                radius = MandoMCRemade.getInstance().getConfig().getInt("ConfusionIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, plugin.getConfig().getInt("ConfusionIIDuration")*20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, plugin.getConfig().getInt("ConfusionIIDuration")*20, 1));
                    }
                }
                break;
        }
        Particles.spiralGround(player.getLocation(),
                Handlers.hexToColor(config.getString("ConfusionHexCode1")),
                Handlers.hexToColor(config.getString("ConfusionHexCode2")));
    }

}
