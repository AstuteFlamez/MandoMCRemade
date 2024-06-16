package net.mandomc.mandomcremade.objects;

import org.bukkit.entity.LivingEntity;

public class LivingEntityWrapper {
    private LivingEntity livingEntity;

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public void setLivingEntity(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }
}