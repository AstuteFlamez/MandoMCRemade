package net.mandomc.mandomcremade.db.data;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestRewards {
    private final List<RewardItem> itemRewards;
    private final List<RewardEvent> eventRewards;

    public QuestRewards(List<RewardItem> itemRewards, List<RewardEvent> eventRewards) {
        this.itemRewards = itemRewards;
        this.eventRewards = eventRewards;
    }

    @NotNull
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap();

        result.put("items", itemRewards);
        result.put("events", eventRewards);


        return result;
    }

//    @NotNull
//    public static QuestRewards deserialize(@NotNull Map<String, Object> args) {
//
//
//
//        QuestRewards result = new QuestRewards();
//
//        return result;
//    }

//    public static QuestRewards fromJsonString(String json) {
//        Gson gson = new Gson();
//
//    }
}
