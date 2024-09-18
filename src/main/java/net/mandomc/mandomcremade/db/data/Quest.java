package net.mandomc.mandomcremade.db.data;

public class Quest {
    private String QuestName;
    private String QuestDesc;
    private String Parent;
    private String QuestTrigger;
    private Integer RewardsPool;

    public Quest(String questName, String questDesc, String trigger, Integer rewardsPool, String parent) {
        QuestName = questName;
        QuestDesc = questDesc;
        Parent = parent;
        QuestTrigger = trigger;
        RewardsPool = rewardsPool;
    }

    public Quest(String questName) {
        QuestName = questName;
    }

    public String getQuestName() {
        return QuestName;
    }

    public void setQuestName(String questName) {
        QuestName = questName;
    }

    public String getQuestDesc() {
        return QuestDesc;
    }

    public void setQuestDesc(String questDesc) {
        QuestDesc = questDesc;
    }

    public String getParent() {
        return Parent;
    }

    public void setParent(String parent) {
        Parent = parent;
    }

    public String getQuestTrigger() {return QuestTrigger;}

    public void setQuestTrigger(String questTrigger) {QuestTrigger = questTrigger;}

    public Integer getRewardsPool() {return RewardsPool;}

    public void setRewardsPool(Integer rewardsPool) {RewardsPool = rewardsPool;}
}
