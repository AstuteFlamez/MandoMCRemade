package net.mandomc.mandomcremade.listeners;

import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponReloadCompleteEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponScopeEvent;
import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.objects.Stamina;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WeaponMechanicsListener implements Listener {

    private final StaminaManager staminaManager;

    public WeaponMechanicsListener(StaminaManager staminaManager) {
        this.staminaManager = staminaManager;
    }
    @EventHandler
    public void onWeaponReloadComplete(WeaponReloadCompleteEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina != null) {
                if(event.getWeaponTitle().contains("HFE")){
                    staminaManager.handleStaminaDecrease(player, stamina, 100);
                }
                else{
                    staminaManager.handleStaminaDecrease(player, stamina, 250);
                }
                
            }
        }
    }

    @EventHandler
    public void onWeaponScope(WeaponScopeEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina != null) {
                staminaManager.handleStaminaDecrease(player, stamina, 125);
            }
        }
    }

}
