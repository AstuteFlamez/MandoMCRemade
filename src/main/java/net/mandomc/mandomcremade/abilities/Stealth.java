package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Stealth {

    public static void stealth(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, plugin.getConfig().getInt("StealthIDuration")*20, 1));
                Particles.spiralHelixBoom(player.getLocation(),
                        Utilities.hexToColor(config.getString("StealthHexCode1")),
                        Utilities.hexToColor(config.getString("StealthHexCode2")));
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, plugin.getConfig().getInt("StealthIIDuration")*20, 1));
                Particles.spiralHelixBoom(player.getLocation(),
                        Utilities.hexToColor(config.getString("StealthHexCode1")),
                        Utilities.hexToColor(config.getString("StealthHexCode2")));
                break;
        }
    }

}
