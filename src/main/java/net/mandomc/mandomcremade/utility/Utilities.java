package net.mandomc.mandomcremade.utility;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.mandomc.mandomcremade.config.SaberConfig;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Utilities {

    public static boolean isMobSpawningEnabled(Location location, Player player){
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = regionContainer.createQuery();

        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);

        if(player.getWorld().getName().equals("JabbasPalace")){
            return false;
        }

        return query.testState(loc, localPlayer, Flags.MOB_SPAWNING);
    }

    public static void takeOneItemAway(Player player){
        if(player.getItemInUse() == null) return;
        if(player.getItemInUse().getAmount() > 1){
            player.getItemInUse().setAmount(player.getItemInUse().getAmount()-1);
        }else{
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
        }
    }

    public static Vector genVec(Location a, Location b) {
        double dX = a.getX() - b.getX();
        double dY = a.getY() - b.getY();
        double dZ = a.getZ() - b.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);

        Vector vector = new Vector(x, z, y);
        vector = vector.normalize();

        return vector;
    }

    public static Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static Color hexToColor(String hexCode) {
        // Remove the hash at the beginning if it's there
        if (hexCode.startsWith("#")) {
            hexCode = hexCode.substring(1);
        }

        // Parse the hex code
        int red = Integer.parseInt(hexCode.substring(0, 2), 16);
        int green = Integer.parseInt(hexCode.substring(2, 4), 16);
        int blue = Integer.parseInt(hexCode.substring(4, 6), 16);

        return Color.fromRGB(red, green, blue);
    }
}
