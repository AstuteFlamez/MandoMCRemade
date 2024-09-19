package net.mandomc.mandomcremade.db.data;

import org.bukkit.Bukkit;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.UUID;

public record QuestRewards(List<RewardItem> itemRewards, List<RewardEvent> eventRewards) {

    public void givePlayer(String uuid)
    {
        PlayerInventory inventory = Bukkit.getPlayer(UUID.fromString(uuid)).getInventory();

        for (RewardItem item : itemRewards){
            inventory.addItem(item.getItemStack());
        }

        // TODO: trigger reward events
    }
}
