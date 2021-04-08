package fr.enissay.task;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.scheduler.CraftScheduler;
import org.bukkit.craftbukkit.v1_16_R3.scheduler.CraftTask;
import org.bukkit.scheduler.BukkitRunnable;

import fr.enissay.TaupePlugin;

public class TaskUtil {
	
    public static Field CRAFTTASK_PERIOD;

    static {
        try {
            CRAFTTASK_PERIOD = CraftTask.class.getDeclaredField("period");
            CRAFTTASK_PERIOD.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateTaskPeriod(int id, long period) {
        CraftScheduler scheduler = (CraftScheduler) Bukkit.getScheduler();
        scheduler.getPendingTasks().forEach(task -> {
            if (task.getTaskId() == id) {
                try {
                    CRAFTTASK_PERIOD.setLong(task, period);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}