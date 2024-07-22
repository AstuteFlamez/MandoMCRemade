package net.mandomc.mandomcremade.db.data;

public class Quest {
    private String QuestName;
    private String QuestDesc;
    private String Parent;

    public Quest(String questName, String questDesc, String parent) {
        QuestName = questName;
        QuestDesc = questDesc;
        Parent = parent;
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
}
