package net.mandomc.mandomcremade.guis;

import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class PunishGUI {

    public static void openBanMenu(Player player){

        //Make and open the ban gui
        Inventory bangui = Bukkit.createInventory(player, 45, Messages.str("&9Player List"));
        //For every player, add their name to gui
        for(Player victim : Bukkit.getOnlinePlayers()){
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            //Set player info on the item
            assert meta != null;
            meta.setDisplayName(victim.getName());
            meta.setOwningPlayer(victim);
            skull.setItemMeta(meta);
            //Add player head to gui
            bangui.addItem(skull);
        }
        player.openInventory(bangui);

    }

    public static void openPlayerMenu(Player player){

        //Make and open the ban gui
        Inventory bangui = Bukkit.createInventory(player, 54, Messages.str("&cPunish GUI"));

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        //Set player info on the item
        assert meta != null;
        meta.setDisplayName(player.getName());
        meta.setOwningPlayer(player);
        skull.setItemMeta(meta);
        bangui.setItem(13, skull);

        ItemStack axe = new ItemStack(Material.NETHERITE_AXE, 1);
        ItemMeta axeMeta = axe.getItemMeta();
        //Set player info on the item
        assert axeMeta != null;
        axeMeta.setDisplayName(Messages.str("&4BAN HAMMER!! (confirm button)"));
        axe.setItemMeta(axeMeta);
        bangui.setItem(22, axe);

        ItemStack ban = new ItemStack(Material.COPPER_BLOCK, 1);
        ItemMeta banMeta = ban.getItemMeta();
        //Set player info on the item
        assert banMeta != null;
        banMeta.setDisplayName(Messages.str("&6&lBAN"));
        ban.setItemMeta(banMeta);
        bangui.setItem(28, ban);

        ItemStack kick = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta kickMeta = kick.getItemMeta();
        //Set player info on the item
        assert kickMeta != null;
        kickMeta.setDisplayName(Messages.str("&a&lKICK"));
        kick.setItemMeta(kickMeta);
        bangui.setItem(30, kick);

        ItemStack mute = new ItemStack(Material.LAPIS_BLOCK, 1);
        ItemMeta muteMeta = mute.getItemMeta();
        //Set player info on the item
        assert muteMeta != null;
        muteMeta.setDisplayName(Messages.str("&9&lMUTE"));
        mute.setItemMeta(muteMeta);
        bangui.setItem(32, mute);

        ItemStack warn = new ItemStack(Material.QUARTZ_BLOCK, 1);
        ItemMeta warnMeta = warn.getItemMeta();
        //Set player info on the item
        assert warnMeta != null;
        warnMeta.setDisplayName(Messages.str("&7&lWARN"));
        warn.setItemMeta(warnMeta);
        bangui.setItem(34, warn);

        ItemStack min1 = new ItemStack(Material.REDSTONE, 1);
        ItemMeta min1Meta = min1.getItemMeta();
        //Set player info on the item
        assert min1Meta != null;
        min1Meta.setDisplayName(Messages.str("&c&l1 Minute"));
        min1.setItemMeta(min1Meta);
        bangui.setItem(36, min1);

        ItemStack hr1 = new ItemStack(Material.COPPER_INGOT, 1);
        ItemMeta hr1Meta = hr1.getItemMeta();
        //Set player info on the item
        assert hr1Meta != null;
        hr1Meta.setDisplayName(Messages.str("&6&l1 Hour"));
        hr1.setItemMeta(hr1Meta);
        bangui.setItem(37, hr1);

        ItemStack hr3 = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta hr3Meta = hr3.getItemMeta();
        //Set player info on the item
        assert hr3Meta != null;
        hr3Meta.setDisplayName(Messages.str("&e&l3 Hours"));
        hr3.setItemMeta(hr3Meta);
        bangui.setItem(38, hr3);

        ItemStack day1 = new ItemStack(Material.EMERALD, 1);
        ItemMeta day1Meta = day1.getItemMeta();
        //Set player info on the item
        assert day1Meta != null;
        day1Meta.setDisplayName(Messages.str("&a&l1 Day"));
        day1.setItemMeta(day1Meta);
        bangui.setItem(39, day1);

        ItemStack day7 = new ItemStack(Material.DIAMOND, 1);
        ItemMeta day7Meta = day7.getItemMeta();
        //Set player info on the item
        assert day7Meta != null;
        day7Meta.setDisplayName(Messages.str("&b&l7 Days"));
        day7.setItemMeta(day7Meta);
        bangui.setItem(40, day7);

        ItemStack day14 = new ItemStack(Material.LAPIS_LAZULI, 1);
        ItemMeta day14Meta = day14.getItemMeta();
        //Set player info on the item
        assert day14Meta != null;
        day14Meta.setDisplayName(Messages.str("&9&l14 Days"));
        day14.setItemMeta(day14Meta);
        bangui.setItem(41, day14);

        ItemStack day30 = new ItemStack(Material.AMETHYST_SHARD, 1);
        ItemMeta day30Meta = day30.getItemMeta();
        //Set player info on the item
        assert day30Meta != null;
        day30Meta.setDisplayName(Messages.str("&d&l30 days"));
        day30.setItemMeta(day30Meta);
        bangui.setItem(42, day30);

        ItemStack year1 = new ItemStack(Material.QUARTZ, 1);
        ItemMeta year1Meta = year1.getItemMeta();
        //Set player info on the item
        assert year1Meta != null;
        year1Meta.setDisplayName(Messages.str("&7&l1 Year"));
        year1.setItemMeta(year1Meta);
        bangui.setItem(43, year1);

        ItemStack perm = new ItemStack(Material.NETHERITE_INGOT, 1);
        ItemMeta permMeta = perm.getItemMeta();
        //Set player info on the item
        assert permMeta != null;
        permMeta.setDisplayName(Messages.str("&8&lPermanent"));
        perm.setItemMeta(permMeta);
        bangui.setItem(44, perm);

        bangui.setItem(53, CustomItems.back());

        player.openInventory(bangui);

    }
}
