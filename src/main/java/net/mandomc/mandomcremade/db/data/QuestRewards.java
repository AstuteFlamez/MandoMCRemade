package net.mandomc.mandomcremade.db.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuestRewards {
    private final List<RewardItem> itemRewards;
    private final List<RewardEvent> eventRewards;

    public QuestRewards(List<RewardItem> itemRewards, List<RewardEvent> eventRewards) {
        this.itemRewards = itemRewards;
        this.eventRewards = eventRewards;
    }
}
