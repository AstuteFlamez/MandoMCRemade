package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Stasis {

    public static void stasis(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        int radius;
        switch(level){
            case 1:
                radius = MandoMCRemade.getInstance().getConfig().getInt("StasisIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, plugin.getConfig().getInt("StasisIDuration")*20, 5));
                    }
                }
                break;
            case 2:
                radius = MandoMCRemade.getInstance().getConfig().getInt("StasisIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, plugin.getConfig().getInt("StasisIIDuration")*20, 5));
                    }
                }
                break;
        }
        Particles.radialWaves(player.getLocation(), Utilities.hexToColor(config.getString("StasisHexCode1")),
                Utilities.hexToColor(config.getString("StasisHexCode2")),
                Utilities.hexToColor(config.getString("StasisHexCode3")),
                Utilities.hexToColor(config.getString("StasisHexCode4")));
    }

}
