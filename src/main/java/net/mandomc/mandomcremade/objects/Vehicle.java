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
    private double baseSpeed;
    private double speedUp;
    private double speedDown;


    public Vehicle(UUID owner, LivingEntity vehicleMob, LivingEntity modelMob) {
        this.owner = owner;
        this.vehicleMob = vehicleMob;
        this.modelMob = modelMob;
        baseSpeed = 1;
        speedUp = 1.5;
        speedDown = 0.5;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public LivingEntity getVehicleMob() {
        return vehicleMob;
    }

    public void setVehicleMob(LivingEntity vehicleMob) {
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

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public double getSpeedUp() {
        return speedUp;
    }

    public void setSpeedUp(double speedUp) {
        this.speedUp = speedUp;
    }

    public double getSpeedDown() {
        return speedDown;
    }

    public void setSpeedDown(double speedDown) {
        this.speedDown = speedDown;
    }
}
