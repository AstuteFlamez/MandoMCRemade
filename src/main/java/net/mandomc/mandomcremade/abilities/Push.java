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

public class Push {

    public static void push(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.orbitingBeam(player,
                        "pushI",
                        plugin.getConfig().getInt("PushIRange"),
                        Utilities.hexToColor(config.getString("PushHexCode1")),
                        Utilities.hexToColor(config.getString("PushHexCode2")),
                        Utilities.hexToColor(config.getString("PushHexCode3")),
                        Utilities.hexToColor(config.getString("PushHexCode4")));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PushIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        Vector vector = genVec(player.getLocation(), livingEntity.getLocation());
                        vector.setY(1);
                        livingEntity.setVelocity(vector);
                    }
                }
                Particles.circleOutwards(player.getLocation(),
                        radius,
                        Utilities.hexToColor(config.getString("PushHexCode1")),
                        Utilities.hexToColor(config.getString("PushHexCode2")));
        }
    }

}
