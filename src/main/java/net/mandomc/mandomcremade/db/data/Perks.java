package net.mandomc.mandomcremade.db.data;

import java.util.UUID;

public class Perks {

    private final UUID playerUUID;
    private boolean lightsaberThrow;

    public Perks(UUID playerUUID, boolean lightsaberThrow) {
        this.playerUUID = playerUUID;
        this.lightsaberThrow = lightsaberThrow;
    }

    public Perks(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.lightsaberThrow = false;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public boolean getLightsaberThrow() {
        return lightsaberThrow;
    }

    public void setLightsaberThrow(boolean lightsaberThrow) {
        this.lightsaberThrow = lightsaberThrow;
    }
}
