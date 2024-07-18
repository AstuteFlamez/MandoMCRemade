package net.mandomc.mandomcremade.guis.ItemsGUI;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class ItemHub extends InventoryGUI {

    private final GUIManager guiManager;

    public ItemHub(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, str("&c&lCustom Items"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(0, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.tieFighter()));
        this.addButton(1, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.xWing("red")));
        this.addButton(2, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.xWing("green")));
        this.addButton(3, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.xWing("blue")));
        this.addButton(4, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.kyber("RedKyber")));
        this.addButton(5, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.kyber("GreenKyber")));
        this.addButton(6, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.kyber("BlueKyber")));
        this.addButton(7, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.kyber("PurpleKyber")));
        this.addButton(8, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.kyber("YellowKyber")));
        this.addButton(9, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.kyber("WhiteKyber")));
        this.addButton(10, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.lightsaberCore()));
        this.addButton(11, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.hilt("LukeSkywalker")));
        this.addButton(12, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.hilt("AnakinSkywalker")));
        this.addButton(13, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.lightSaber("LukeSkywalker")));
        this.addButton(14, this.createGetButton(net.mandomc.mandomcremade.utility.CustomItems.lightSaber("AnakinSkywalker")));


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
