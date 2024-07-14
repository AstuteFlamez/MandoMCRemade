package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.handlers.Handlers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Heal {

    public static void heal(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        double heal, health;
        switch(level){
            case 1:
                heal = config.getDouble("HealIAmount");
                health = player.getHealth();
                if (player.getHealth() < (20-heal)) {
                    player.setHealth(health + heal);
                } else if (player.getHealth() >= (20-heal) && player.getHealth() <= 20.00) {
                    player.setHealth(20.00);
                }
                break;
            case 2:
                heal = config.getDouble("HealIIAmount");
                health = player.getHealth();
                if (player.getHealth() < (20-heal)) {
                    player.setHealth(health + heal);
                } else if (player.getHealth() >= (20-heal) && player.getHealth() <= 20.00) {
                    player.setHealth(20.00);
                }
                break;
        }
        Particles.sphere(player.getLocation(),
                Handlers.hexToColor(config.getString("HealHexCode1")),
                Handlers.hexToColor(config.getString("HealHexCode2")),
                Handlers.hexToColor(config.getString("HealHexCode3")),
                Handlers.hexToColor(config.getString("HealHexCode4")));
    }

}
