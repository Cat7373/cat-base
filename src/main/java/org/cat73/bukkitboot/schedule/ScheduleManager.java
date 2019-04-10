package org.cat73.bukkitboot.schedule;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.cat73.bukkitboot.annotation.Scheduled;
import org.cat73.bukkitboot.context.IManager;
import org.cat73.bukkitboot.context.PluginContext;
import org.cat73.bukkitboot.util.Lang;
import org.cat73.bukkitboot.util.reflect.Reflects;

import javax.annotation.Nonnull;

/**
 * 定时任务管理器
 * <!-- TODO 详细说明 -->
 */
public class ScheduleManager implements IManager {
    /**
     * Bukkit 的定时任务
     */
    private final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    @Override
    public void register(@Nonnull PluginContext context, @Nonnull Object bean) {
        Reflects.forEachMethodByAnnotation(bean.getClass(), Scheduled.class, (method, annotation) -> {
            long delay = Math.max(annotation.delay(), 0L);
            long period = annotation.period();
            boolean async = annotation.async();
            Plugin plugin = context.getPlugin();
            Runnable task = ((Lang.ThrowableRunnable) () -> method.invoke(bean)).wrap();

            if (period < 0) {
                if (delay == 0L) {
                    if (async) {
                        this.scheduler.runTaskAsynchronously(plugin, task);
                    } else {
                        this.scheduler.runTask(plugin, task);
                    }
                } else {
                    if (async) {
                        this.scheduler.runTaskLaterAsynchronously(plugin, task, delay);
                    } else {
                        this.scheduler.runTaskLater(plugin, task, delay);
                    }
                }
            } else {
                if (async) {
                    this.scheduler.runTaskTimerAsynchronously(plugin, task, delay, period);
                } else {
                    this.scheduler.runTaskTimer(plugin, task, delay, period);
                }
            }
        });
    }
}
