package nn.iamj.borne.modules.server.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import nn.iamj.borne.Borne;

public final class Scheduler {

    private Scheduler() {}

    public static BukkitTask asyncHandleRate(final BukkitRunnable runnable, final long startAt, final long delayAt) {
        return runnable.runTaskTimerAsynchronously(Borne.getBorne().getPlugin(), startAt, delayAt);
    }

    public static BukkitTask asyncHandleRate(final Runnable runnable, final long startAt, final long delayAt) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(Borne.getBorne().getPlugin(), runnable, startAt, delayAt);
    }

    public static BukkitTask handleRate(final BukkitRunnable runnable, final long startAt, final long delayAt) {
        return runnable.runTaskTimer(Borne.getBorne().getPlugin(), startAt, delayAt);
    }

    public static BukkitTask handleRate(final Runnable runnable, final long startAt, final long delayAt) {
        return Bukkit.getScheduler().runTaskTimer(Borne.getBorne().getPlugin(), runnable, startAt, delayAt);
    }

    public static BukkitTask asyncHandleRate(final BukkitRunnable runnable, final long startAt) {
        return runnable.runTaskLaterAsynchronously(Borne.getBorne().getPlugin(), startAt);
    }

    public static BukkitTask asyncHandleRate(final Runnable runnable, final long startAt) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(Borne.getBorne().getPlugin(), runnable, startAt);
    }

    public static BukkitTask handleRate(final BukkitRunnable runnable, final long startAt) {
        return runnable.runTaskLater(Borne.getBorne().getPlugin(), startAt);
    }

    public static BukkitTask handleRate(final Runnable runnable, final long startAt) {
        return Bukkit.getScheduler().runTaskLater(Borne.getBorne().getPlugin(), runnable, startAt);
    }

    public static BukkitTask asyncHandle(final Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(Borne.getBorne().getPlugin(), runnable);
    }

    public static BukkitTask asyncHandle(final BukkitRunnable runnable) {
        return runnable.runTaskAsynchronously(Borne.getBorne().getPlugin());
    }

    public static BukkitTask handle(final Runnable runnable) {
        return Bukkit.getScheduler().runTask(Borne.getBorne().getPlugin(), runnable);
    }

    public static BukkitTask handle(final BukkitRunnable runnable) {
        return runnable.runTask(Borne.getBorne().getPlugin());
    }

    public static void cancel(final int id) {
        Bukkit.getScheduler().cancelTask(id);
    }

    public static void shutdown() {
        Bukkit.getScheduler().cancelTasks(Borne.getBorne().getPlugin());
    }

}
