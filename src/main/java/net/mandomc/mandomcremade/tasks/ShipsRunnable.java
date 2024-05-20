package net.mandomc.mandomcremade.tasks;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.objects.Vehicles;
import net.mandomc.mandomcremade.vehicles.TieFighter;
import net.mandomc.mandomcremade.vehicles.XWing;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ShipsRunnable extends BukkitRunnable {

    MandoMCRemade plugin;

    public ShipsRunnable(MandoMCRemade plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        //looks through all online players
        for(Player player : Bukkit.getOnlinePlayers()) {

            ArrayList<Vehicles> allActiveTieFighters = TieFighter.getAllActiveTieFighters();
            for (Vehicles tieFighter : allActiveTieFighters) {
                if (tieFighter != null) {
                    if (tieFighter.getPilot() == player) {

                        Entity seat1 = tieFighter.getSeatOne();
                        Entity model = tieFighter.getModel();
                        LivingEntity seat1Living = (Phantom) seat1;
                        seat1Living.setAI(true);

                        seat1.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
                        seat1.setVelocity(seat1.getLocation().getDirection().multiply(2));

                        model.teleport(seat1.getLocation());
                    }
                    if(tieFighter.getPilot() == null){
                        Entity seat1 = tieFighter.getSeatOne();
                        Entity model = tieFighter.getModel();

                        seat1.teleport(model);
                    }
                }
            }
            ArrayList<Vehicles> allActiveXWings = XWing.getAllActiveXWings();
            for (Vehicles xWing : allActiveXWings) {
                if (xWing != null) {
                    if (xWing.getPilot() == player) {

                        Entity seat1 = xWing.getSeatOne();
                        Entity model = xWing.getModel();
                        LivingEntity seat1Living = (Phantom) seat1;
                        seat1Living.setAI(true);

                        seat1.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
                        seat1.setVelocity(seat1.getLocation().getDirection().multiply(1.5));

                        Location seat1Location = seat1.getLocation(); // Get seat1's location
                        Location targetLocation = seat1Location.clone(); // Clone the location to avoid modifying seat1's location
                        targetLocation.setY(targetLocation.getY() - 1); // Adjust the Y-coordinate to be one block lower
                        model.teleport(targetLocation); // Teleport the model to the adjusted location
                    }
                    if(xWing.getPilot() == null){
                        Entity seat1 = xWing.getSeatOne();
                        Entity model = xWing.getModel();

                        seat1.teleport(model);
                    }
                }
            }

        }
    }
}
