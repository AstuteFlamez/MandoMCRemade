package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.handlers.Handlers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Light {

    public static void light(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        if (level == 1) {
            player.setAbsorptionAmount(plugin.getConfig().getInt("LightIAmount"));
            Particles.sphere(player.getLocation(),
                    Handlers.hexToColor(config.getString("LightHexCode1")),
                    Handlers.hexToColor(config.getString("LightHexCode2")),
                    Handlers.hexToColor(config.getString("LightHexCode3")),
                    Handlers.hexToColor(config.getString("LightHexCode4")));
        }
    }

}
