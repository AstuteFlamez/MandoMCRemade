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

public class Blind {

    public static void blind(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        int radius;
        switch(level){
            case 1:
                Particles.spinningBeam(player, "blindI", plugin.getConfig().getInt("BlindIRange"),
                        Handlers.hexToColor(config.getString("BlindHexCode1")),
                        Handlers.hexToColor(config.getString("BlindHexCode2")));
                break;
            case 2:
                radius = MandoMCRemade.getInstance().getConfig().getInt("BlindIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, plugin.getConfig().getInt("BlindIIDuration")*20, 1));
                    }
                }
                Particles.spiralGround(player.getLocation(),
                        Handlers.hexToColor(config.getString("BlindHexCode1")),
                        Handlers.hexToColor(config.getString("BlindHexCode2")));
                break;
        }
    }

}
