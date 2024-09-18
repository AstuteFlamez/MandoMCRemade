package net.mandomc.mandomcremade.db.data;

import org.bukkit.inventory.ItemStack;

public class RewardItem {
    private int id;
    private String itemName;
    private int count;
    private String metaData;
    private int poolId;

    public RewardItem(int id, String itemName, int count, String metaData, int poolId) {
        this.id = id;
        this.itemName = itemName;
        this.count = count;
        this.metaData = metaData;
        this.poolId = poolId;
    }

//    public ItemStack asItemStack() {
//
//    }
}
