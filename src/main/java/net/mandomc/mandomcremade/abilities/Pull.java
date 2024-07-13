package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static net.mandomc.mandomcremade.utility.Utilities.genVec;

public class Pull {

    public static void pull(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.oscillatingBeam(player,
                        "pullI",
                        plugin.getConfig().getInt("PullIRange"),
                        Utilities.hexToColor(config.getString("PullHexCode1")),
                        Utilities.hexToColor(config.getString("PullHexCode2")));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PullIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        Vector vector2 = genVec(livingEntity.getLocation(), player.getLocation());
                        livingEntity.setVelocity(vector2);
                    }
                }
                Particles.circleInwards(player.getLocation(),
                        radius,
                        Utilities.hexToColor(config.getString("PullHexCode1")),
                        Utilities.hexToColor(config.getString("PullHexCode2")));
        }
    }

}
