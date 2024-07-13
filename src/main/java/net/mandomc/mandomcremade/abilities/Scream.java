package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Scream {

    public static void scream(Player player, int level, MandoMCRemade plugin) {
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, plugin.getConfig().getInt("ScreamIDuration")*20, 0));
                Particles.sphere(player.getLocation(),
                        Utilities.hexToColor(config.getString("ScreamHexCode1")),
                        Utilities.hexToColor(config.getString("ScreamHexCode2")),
                        Utilities.hexToColor(config.getString("ScreamHexCode3")),
                        Utilities.hexToColor(config.getString("ScreamHexCode4")));
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, plugin.getConfig().getInt("ScreamIIDuration")*20, 1));
                Particles.sphereLoop(player.getLocation(), Utilities.hexToColor(config.getString("ScreamHexCode1")),
                        Utilities.hexToColor(config.getString("ScreamHexCode2")));
                break;
        }
    }

}
