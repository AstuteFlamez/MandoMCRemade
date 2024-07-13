package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Light {

    public static void light(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        if (level == 1) {
            player.setAbsorptionAmount(plugin.getConfig().getInt("LightIAmount"));
            Particles.sphere(player.getLocation(),
                    Utilities.hexToColor(config.getString("LightHexCode1")),
                    Utilities.hexToColor(config.getString("LightHexCode2")),
                    Utilities.hexToColor(config.getString("LightHexCode3")),
                    Utilities.hexToColor(config.getString("LightHexCode4")));
        }
    }

}
