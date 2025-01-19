package net.mandomc.mandomcremade.listeners;

import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponReloadCompleteEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponScopeEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponPreReloadEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponReloadEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponMeleeMissEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponMeleeHitEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponDamageEntityEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponShootEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponEquipEvent;
import me.deecaad.weaponmechanics.weapon.projectile.weaponprojectile.WeaponProjectile;
import me.deecaad.weaponmechanics.weapon.weaponevents.ProjectileHitEntityEvent;
import org.bukkit.ChatColor;

import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.listeners.SaberListener;
import net.mandomc.mandomcremade.objects.Stamina;
import net.mandomc.mandomcremade.utility.VectorUtils;
import net.mandomc.mandomcremade.utility.NBTUtils;
import org.bukkit.util.Vector;

import io.lumine.mythic.bukkit.utils.nbt.NBT;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class WeaponMechanicsListener implements Listener {

    private final StaminaManager staminaManager;
    private final HashMap<UUID, Long> ks23Cooldowns = new HashMap<>();
    private final JavaPlugin plugin;
    private final HashMap<String, Integer> durabilityMap = new HashMap<>() {{
        put("maul", 212);
        put("a280", 271);
        put("thrust_pack", 50);
        put("z6r", 782);
        put("dc15a", 421);
        put("dc15s", 309);
        put("el_16hfe", 164);
    }};

    public WeaponMechanicsListener(StaminaManager staminaManager, JavaPlugin plugin) {
        this.staminaManager = staminaManager;
        this.plugin = plugin;
    }


    @EventHandler
    public void onWeaponReload(WeaponReloadEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina != null) {
                if(event.getWeaponTitle().contains("HFE")){
                    staminaManager.handleStaminaDecrease(player, stamina, 100);
                }
                else{
                    staminaManager.handleStaminaDecrease(player, stamina, 200);
                }
                
            }
        }
    }

    @EventHandler
    public void onWeaponPreReload(WeaponPreReloadEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina.getStaminaAmount() <= 0) {
                event.setCancelled(true); 
            }
        }
    }

    @EventHandler
    public void onWeaponScope(WeaponScopeEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina != null) {
                if (stamina.getStaminaAmount() != 0) {
                    staminaManager.handleStaminaDecrease(player, stamina, 125);
                }
                else{
                    event.setCancelled(true); 

                }
            }
        }
    }





    @EventHandler
    public void onWeaponShoot(WeaponShootEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter == null) return;
    
        ItemStack item = event.getWeaponStack();
        
        decreaseDurability(item, shooter);
    }
    

    
    @EventHandler
    public void onWeaponEquip(WeaponEquipEvent event) {
        LivingEntity entity = event.getShooter();
        if (entity == null) return;
    
        ItemStack item = event.getWeaponStack();
        String weaponTitle = event.getWeaponTitle().toLowerCase(); 
    
        
        Integer maxDurability = durabilityMap.get(weaponTitle);
        if (maxDurability == null) {
            Bukkit.getLogger().warning("Weapon title '" + event.getWeaponTitle() + "' not found in durabilityMap.");
            return;
        }
    
        
        if (!NBTUtils.hasTag(item, "mdura")) {
            NBTUtils.setIntegerTag(item, "mdura", maxDurability);
        }
        if (!NBTUtils.hasTag(item, "cdura")) {
            NBTUtils.setIntegerTag(item, "cdura", maxDurability);
        }

        int currentDurability = NBTUtils.getIntegerTag(item, "cdura");

        updateItemDisplayName(item, currentDurability, maxDurability);
    }
    
    

    @EventHandler
    public void onWeaponMeleeHit(WeaponMeleeHitEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter == null) return;

        ItemStack item = event.getWeaponStack();
        
        decreaseDurability(item, shooter);
    }


    private void decreaseDurability(ItemStack item, LivingEntity shooter) {
        // Decrease durability
        int currentDurability = NBTUtils.getIntegerTag(item, "cdura") - 1;
        NBTUtils.setIntegerTag(item, "cdura", currentDurability);
    
        // Get maximum durability
        Integer maxDurability = NBTUtils.getIntegerTag(item, "mdura");
    
        // Check if durability is zero or less
        if (currentDurability <= 0 && shooter instanceof Player) {
            Player player = (Player) shooter;
    
            // Remove the item and play break sound
            if (player.getInventory().getItemInMainHand().equals(item)) {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            } else if (player.getInventory().getItemInOffHand().equals(item)) {
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        }

        updateItemDisplayName(item, currentDurability, maxDurability);

        }

    private void updateItemDisplayName(ItemStack item, int currentDurability, int maxDurability) {
        double durabilityPercentage = (double) currentDurability / maxDurability;
        ChatColor color = durabilityPercentage > 0.75 ? ChatColor.GREEN :
                            durabilityPercentage > 0.25 ? ChatColor.YELLOW : ChatColor.RED;
    
        String newName = color + String.valueOf(currentDurability) +
                            ChatColor.GRAY + " / " + ChatColor.GREEN + String.valueOf(maxDurability);
    
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(newName);
            item.setItemMeta(meta);
        }
    }
        
    }
    
