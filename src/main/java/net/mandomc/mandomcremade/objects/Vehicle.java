package net.mandomc.mandomcremade.objects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Vehicle {

    private UUID owner;
    private LivingEntity vehicleMob;
    private LivingEntity modelMob;
    private UUID pilot;
    private ItemStack modelItem;


    public Vehicle(UUID owner, LivingEntity vehicleMob, LivingEntity modelMob) {
        this.owner = owner;
        this.vehicleMob = vehicleMob;
        this.modelMob = modelMob;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public LivingEntity getVehicle() {
        return vehicleMob;
    }

    public void setVehicle(LivingEntity vehicleMob) {
        this.vehicleMob = vehicleMob;
    }

    public LivingEntity getModelMob() {
        return modelMob;
    }

    public void setModelMob(LivingEntity modelMob) {
        this.modelMob = modelMob;
    }

    public UUID getPilot() {
        return pilot;
    }

    public void setPilot(UUID pilot) {
        this.pilot = pilot;
    }

    public ItemStack getModelItem() {
        return modelItem;
    }

    public void setModelItem(ItemStack modelItem) {
        this.modelItem = modelItem;
    }
}
