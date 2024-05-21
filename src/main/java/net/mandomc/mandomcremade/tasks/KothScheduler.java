package net.mandomc.mandomcremade.tasks;

import io.lumine.mythic.bukkit.utils.tasks.TaskScheduler;
import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class KothScheduler {
    private static final ZoneId CST_ZONE = ZoneId.of("America/Chicago");

    public static void main(String[] args) {
        KothScheduler scheduler = new KothScheduler();
        scheduler.start();
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = this::runTask;

        // Schedule the initial delay
        long initialDelay = calculateInitialDelay();
        long period = TimeUnit.HOURS.toMillis(4);

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    private long calculateInitialDelay() {
        LocalDateTime now = LocalDateTime.now(CST_ZONE);
        LocalDateTime nextRun = now.withHour(12).withMinute(0).withSecond(0).withNano(0);

        while (now.isAfter(nextRun) || now.equals(nextRun)) {
            nextRun = nextRun.plusHours(4);
        }

        ZonedDateTime nowZoned = now.atZone(CST_ZONE);
        ZonedDateTime nextRunZoned = nextRun.atZone(CST_ZONE);

        return nextRunZoned.toInstant().toEpochMilli() - nowZoned.toInstant().toEpochMilli();
    }

    public String timeUntilNextTask() {
        long millis = calculateInitialDelay();
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder timeString = new StringBuilder();
        if (hours > 0) {
            timeString.append(hours).append(" hours ");
        }
        if (minutes > 0) {
            timeString.append(minutes).append(" minutes ");
        }
        if (seconds > 0) {
            timeString.append(seconds).append(" seconds");
        }

        if (timeString.isEmpty()) {
            timeString.append("less than a second");
        }

        return timeString.toString().trim();
    }

    private void runTask() {
        new KothRunnable(MandoMCRemade.getInstance()).runTaskTimer(MandoMCRemade.getInstance(), 0L, 20L);
    }
}
