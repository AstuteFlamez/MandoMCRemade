package net.mandomc.mandomcremade.objects;


import org.bukkit.entity.Player;


public class Stamina {
    private final Player player;
    private int staminaAmount;
    private boolean effectCooldown;
    private long effectCooldownEndTime;
    private long lastEnergySpentTime;

    public Stamina(Player player, int initialStamina) {
        this.player = player;
        this.staminaAmount = initialStamina; // Initial stamina amount
        this.effectCooldown = false;
        this.effectCooldownEndTime = 0;
        this.lastEnergySpentTime = 0;

    }

    public Player getPlayer() {
        return player;
    }

    public int getStaminaAmount() {
        return staminaAmount;
    }

    public void setStaminaAmount(int staminaAmount) {
        this.staminaAmount = Math.max(0, Math.min(100, staminaAmount)); // Ensure stamina is within 0-100
    }

    public void addStamina(int amount) {
        if (!isRegenerationOnCooldown()) {
            setStaminaAmount(this.staminaAmount + amount);
        }
    }

    public void removeStamina(int amount) {
        setStaminaAmount(this.staminaAmount - amount);
        lastEnergySpentTime = System.currentTimeMillis();
    }

    public boolean isEffectOnCooldown() {
        return effectCooldown && System.currentTimeMillis() < effectCooldownEndTime;
    }

    public void startEffectCooldown(long durationMillis) {
        this.effectCooldown = true;
        this.effectCooldownEndTime = System.currentTimeMillis() + durationMillis;
    }

    public boolean isRegenerationOnCooldown() {
        return System.currentTimeMillis() - lastEnergySpentTime < 2500;
    }

}