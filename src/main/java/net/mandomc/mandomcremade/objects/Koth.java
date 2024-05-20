package net.mandomc.mandomcremade.objects;

import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Koth {

    private UUID uuid;
    private BossBar bossBar;

    public Koth(UUID uuid) {
        this.uuid = uuid;
        bossBar = Bukkit.createBossBar(Messages.str("&4&lUncontested"), BarColor.RED, BarStyle.SOLID);
        bossBar.setProgress(0.0);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }

}