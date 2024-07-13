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

public class Choke {

    public static void choke(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.spinningBeam(player, "chokeI",
                        config.getInt("ChokeIRange"),
                        Utilities.hexToColor(config.getString("ChokeHexCode1")),
                        Utilities.hexToColor(config.getString("ChokeHexCode2")));
                break;
            case 2:
                int radius = config.getInt("ChokeIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, plugin.getConfig().getInt("ChokeIIDuration")*20, 0));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, plugin.getConfig().getInt("ChokeIIDuration")*20, 0));
                    }
                }
                Particles.circleOutwards(player.getLocation(),
                        radius,
                        Utilities.hexToColor(config.getString("ChokeHexCode1")),
                        Utilities.hexToColor(config.getString("ChokeHexCode2")));
                break;
        }
    }

}
