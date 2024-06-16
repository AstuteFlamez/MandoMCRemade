package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.PunishmentConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PlayerJoinListener implements Listener {

    MandoMCRemade plugin;

    public PlayerJoinListener(MandoMCRemade plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) throws ParseException {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        FileConfiguration config = PunishmentConfig.get();

        if(config.contains(uuid)){
            if(config.getStringList(uuid).get(1).equals("BAN")){
                Date date = parseDate(config.getStringList(uuid).get(2));
                if(date.before(new Date())){
                    config.set(uuid, null);
                    PunishmentConfig.save();
                }else{
                    player.kickPlayer("Your bans ends in " + formatTimeDifference(date));
                }
            }
        }

        if(plugin.getConfig().getBoolean("Maintenance")){
            if(!player.hasPermission("mmc.staff.maintenancebypass")){
                player.kickPlayer("&4we in maintenance big g join later");
            }
        }
    }

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return formatter.parse(dateString);
    }

    public static String formatTimeDifference(Date date) {
        // Get current time and the difference in milliseconds
        long currentTime = System.currentTimeMillis();
        long givenTime = date.getTime();
        long diffInMillis = Math.abs(currentTime - givenTime);

        // Calculate days, hours, and minutes
        long days = TimeUnit.MILLISECONDS.toDays(diffInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMillis));

        // Build the result string
        StringBuilder result = new StringBuilder();
        if (days > 0) {
            result.append(days).append(" days");
        }
        if (hours > 0) {
            if (!result.isEmpty()) result.append(", ");
            result.append(hours).append(" hours");
        }
        if (minutes > 0) {
            if (!result.isEmpty()) result.append(", ");
            result.append(minutes).append(" minutes");
        }

        // Return the result or "0 minutes" if all values are zero
        return !result.isEmpty() ? result.toString() : "0 minutes";
    }
}
