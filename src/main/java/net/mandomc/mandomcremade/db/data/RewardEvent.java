package net.mandomc.mandomcremade.db.data;

public class RewardEvent {
    private int id;
    private String eventName;
    private String metaData;
    private int poolId;

    public RewardEvent(int id, String eventName, String metaData, int poolId) {
        this.id = id;
        this.eventName = eventName;
        this.metaData = metaData;
        this.poolId = poolId;
    }
}
