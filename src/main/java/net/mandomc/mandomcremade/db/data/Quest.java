package net.mandomc.mandomcremade.db.data;

public class Quest {
    private String QuestName;
    private String QuestDesc;

    public Quest(String questName, String questDesc) {
        QuestName = questName;
        QuestDesc = questDesc;
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
}
