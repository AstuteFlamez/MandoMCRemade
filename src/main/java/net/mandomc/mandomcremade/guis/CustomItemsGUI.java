package net.mandomc.mandomcremade.guis;

import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class CustomItemsGUI implements Listener {

    public static Inventory customItems(Player player){
        Inventory inv = Bukkit.createInventory(player, 27, Messages.str("&c&lCustom Items"));

        inv.setItem(0, CustomItems.tieFighter());
        inv.setItem(1, CustomItems.xWing("red"));
        inv.setItem(2, CustomItems.xWing("green"));
        inv.setItem(3, CustomItems.xWing("blue"));
        inv.setItem(4, CustomItems.kyber("redkyber"));
        inv.setItem(5, CustomItems.kyber("greenkyber"));
        inv.setItem(6, CustomItems.kyber("bluekyber"));
        inv.setItem(7, CustomItems.kyber("purplekyber"));
        inv.setItem(8, CustomItems.kyber("yellowkyber"));
        inv.setItem(9, CustomItems.kyber("whitekyber"));
        inv.setItem(10, CustomItems.lightsaberCore());
        inv.setItem(11, CustomItems.hilt("lukeskywalker"));
        inv.setItem(12, CustomItems.hilt("anakinskywalker"));
        inv.setItem(13, CustomItems.lightSaber("lukeskywalker"));
        inv.setItem(14, CustomItems.lightSaber("anakinskywalker"));

        return inv;
    }

}
