package net.mandomc.mandomcremade.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class CustomScoreboard {
    private ScoreboardManager manager;
    private Scoreboard scoreboard;
    private Objective objective;

    public CustomScoreboard() {
        this.manager = Bukkit.getScoreboardManager();
        this.scoreboard = manager.getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("stamina", "dummy", "Stamina");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateScore(Player player, int stamina) {

        if (objective == null) {
            Bukkit.getLogger().severe("Objective is null!");
            return;
        }

        Score score = objective.getScore("Energy: ");
        score.setScore(stamina);
        player.setScoreboard(scoreboard);
    }
}
