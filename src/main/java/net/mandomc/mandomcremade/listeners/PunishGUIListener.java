package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.PunishmentConfig;
import net.mandomc.mandomcremade.guis.PunishGUI;
import net.mandomc.mandomcremade.utility.Glow;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.mandomc.mandomcremade.listeners.PlayerJoinListener.formatTimeDifference;

public class PunishGUIListener implements Listener {

    boolean isTime = false;
    boolean isPunish = false;
    ItemStack time = new ItemStack(Material.AIR);
    ItemStack punishment = new ItemStack(Material.AIR);

    MandoMCRemade plugin;

    public PunishGUIListener(MandoMCRemade plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (title.contains(Messages.str("Player List"))) {
            event.setCancelled(true);
            String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            Player victim = Bukkit.getPlayer(displayName);
            PunishGUI.openPlayerMenu(victim);
        }

        if (title.contains(Messages.str("Punish GUI"))) {
            event.setCancelled(true);
            int[] punishSlots = {28, 32};
            int[] timeSlots = {36, 37, 38, 39, 40, 41, 42, 43, 44};
            ItemStack glow;
            Player victim = Bukkit.getPlayer(event.getInventory().getItem(13).getItemMeta().getItemName());

            switch(event.getSlot()){
                case 22:
                    if(isPunish && isTime){
                        punish(victim, punishment, time);
                        player.closeInventory();
                    }
                    break;
                case 30:
                    assert victim != null;
                    victim.kickPlayer(Messages.str(plugin.getConfig().getString("KickMessage").replace("\\n","\n")));
                    player.closeInventory();
                    break;
                case 28, 32:

                    for (int slot : timeSlots) {
                        if (Glow.isGlow(event.getInventory().getItem(slot))) {
                            isTime = true;
                            time = event.getInventory().getItem(slot);
                        }
                    }

                    for (int punishSlot : punishSlots) {
                        if (Glow.isGlow(event.getInventory().getItem(punishSlot))) {
                            glow = Glow.removeGlow(event.getInventory().getItem(punishSlot));
                            event.getInventory().setItem(punishSlot, glow);
                        }
                        glow = Glow.addGlow(event.getCurrentItem());
                        event.getInventory().setItem(event.getSlot(), glow);
                        isPunish = true;
                        punishment = event.getCurrentItem();
                    }
                    break;
                case 34:
                    assert victim != null;
                    Messages.msg(victim, "You have been warned! Further rules broken will result in more severe consequences!");
                    player.closeInventory();
                    break;
                case 36, 37, 38, 39, 40, 41, 42, 43, 44:

                    for (int punishSlot : punishSlots) {
                        if (Glow.isGlow(event.getInventory().getItem(punishSlot))) {
                            isPunish = true;
                            punishment = event.getInventory().getItem(punishSlot);
                        }
                    }

                    for (int timeSlot : timeSlots) {
                        if (Glow.isGlow(event.getInventory().getItem(timeSlot))) {
                            glow = Glow.removeGlow(event.getInventory().getItem(timeSlot));
                            event.getInventory().setItem(timeSlot, glow);
                        }
                        glow = Glow.addGlow(event.getCurrentItem());
                        event.getInventory().setItem(event.getSlot(), glow);
                        isTime = true;
                        time = event.getCurrentItem();
                    }
                    break;
                case 53:
                    PunishGUI.openBanMenu((Player) event.getWhoClicked());
                    break;

            }
        }

    }
    public void punish(Player player, ItemStack punish, ItemStack time) {
        String punishment = ChatColor.stripColor(punish.getItemMeta().getDisplayName());

        LocalDateTime localDate = LocalDateTime.now();
        String displayName = time.getItemMeta().getDisplayName();

        if (displayName.contains("1 Minute")) {
            localDate = localDate.plusMinutes(1);
        } else if (displayName.contains("1 Hour")) {
            localDate = localDate.plusHours(1);
        } else if (displayName.contains("3 Hours")) {
            localDate = localDate.plusHours(3);
        } else if (displayName.contains("1 Day")) {
            localDate = localDate.plusDays(1);
        } else if (displayName.contains("7 Days")) {
            localDate = localDate.plusDays(7);
        } else if (displayName.contains("14 Days")) {
            localDate = localDate.plusDays(14);
        } else if (displayName.contains("30 Days")) {
            localDate = localDate.plusDays(30);
        } else if (displayName.contains("1 Year")) {
            localDate = localDate.plusYears(1);
        } else if (displayName.contains("Permanent")) {
            localDate = localDate.plusYears(100);
        }

        Date date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
        List<String> list = new ArrayList<>();
        list.add(player.getName());
        list.add(punishment);
        list.add(formatDate(date));

        Bukkit.getConsoleSender().sendMessage(player.getName());
        Bukkit.getConsoleSender().sendMessage(punishment);
        Bukkit.getConsoleSender().sendMessage(formatDate(date));

        PunishmentConfig.get().addDefault(player.getUniqueId().toString(), list);
        PunishmentConfig.save();
        player.kickPlayer("Your ban ends in " + formatTimeDifference(date));
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return formatter.format(date);
    }
}
