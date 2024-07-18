package net.mandomc.mandomcremade.db;

import java.util.UUID;

public class Perks {

    private final UUID playerUUID;
    private int lightsaberThrow;

    public Perks(UUID playerUUID, int lightsaberThrow) {
        this.playerUUID = playerUUID;
        this.lightsaberThrow = lightsaberThrow;
    }

    public Perks(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.lightsaberThrow = 0;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getLightsaberThrow() {
        return lightsaberThrow;
    }

    public void setLightsaberThrow(int lightsaberThrow) {
        this.lightsaberThrow = lightsaberThrow;
    }
}
