package net.mandomc.mandomcremade.guis.ItemsGUI;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemHub extends InventoryGUI {

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, MandoMCRemade.str("&c&lCustom Items"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(0, this.createGetButton(CustomItems.tieFighter()));
        this.addButton(1, this.createGetButton(CustomItems.xWing("red")));
        this.addButton(2, this.createGetButton(CustomItems.xWing("green")));
        this.addButton(3, this.createGetButton(CustomItems.xWing("blue")));
        this.addButton(4, this.createGetButton(CustomItems.kyber("RedKyber")));
        this.addButton(5, this.createGetButton(CustomItems.kyber("GreenKyber")));
        this.addButton(6, this.createGetButton(CustomItems.kyber("BlueKyber")));
        this.addButton(7, this.createGetButton(CustomItems.kyber("PurpleKyber")));
        this.addButton(8, this.createGetButton(CustomItems.kyber("YellowKyber")));
        this.addButton(9, this.createGetButton(CustomItems.kyber("WhiteKyber")));
        this.addButton(10, this.createGetButton(CustomItems.lightsaberCore()));
        this.addButton(11, this.createGetButton(CustomItems.hilt("LukeSkywalker")));
        this.addButton(12, this.createGetButton(CustomItems.hilt("AnakinSkywalker")));
        this.addButton(13, this.createGetButton(CustomItems.lightSaber("LukeSkywalker")));
        this.addButton(14, this.createGetButton(CustomItems.lightSaber("AnakinSkywalker")));


        super.decorate(player);
    }

    private InventoryButton createGetButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.getInventory().addItem(itemStack);
                });
    }

}
