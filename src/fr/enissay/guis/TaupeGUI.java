package fr.enissay.guis;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import fr.enissay.TaupePlugin;
import fr.enissay.stats.PlayerStat;
import fr.enissay.task.TaskUtil;
import fr.enissay.utils.gui.GUI;

public class TaupeGUI extends GUI {

	private final Map<ItemStack, Integer> itemInfo = new HashMap<>();
	private final Map<ItemStack, Long> multiplierInfo = new HashMap<>();

	private ItemStack cItem = new ItemStack(Material.PLAYER_HEAD);
	private int total = 0;
	private long multiplier = 1, max = 0;

	@SuppressWarnings("deprecation")
	public TaupeGUI() {
		super("Taupe", 6);
		int slotChoosed = (int) (Math.random() * this.getInventory().getSize());
		this.inv.setItem(slotChoosed, new ItemStack(Material.PLAYER_HEAD));
		
		itemInfo.put(cItem, 2);

		TaupePlugin.getInstance().getStatsManager().getFont().entrySet()
				.forEach(entry -> this.inv.setItem(entry.getValue(), entry.getKey()));

		multiplierInfo.put(cItem, (long) 40);

		int scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(TaupePlugin.getInstance(), () -> {

			final int time = itemInfo.get(cItem);
			if (time <= 0) {
				this.inv.clear();

				TaupePlugin.getInstance().getStatsManager().getFont().entrySet()
						.forEach(entry -> this.inv.setItem(entry.getValue(), entry.getKey()));

				itemInfo.put(cItem, 2);
				int newSlotChoosed = (int) (Math.random() * this.getInventory().getSize());
				if (this.inv.getItem(slotChoosed) == null || this.inv.getItem(slotChoosed).getType() == Material.AIR)
					this.inv.setItem(newSlotChoosed, cItem);
				else 
					this.inv.setItem((int)(Math.random() * this.getInventory().getSize()), cItem);

			}
			itemInfo.put(cItem, itemInfo.get(cItem) - 1);
		}, 0, (long) multiplierInfo.get(cItem));

		Bukkit.getScheduler().scheduleAsyncRepeatingTask(TaupePlugin.getInstance(),
				() -> TaskUtil.updateTaskPeriod(scheduler, (long) multiplierInfo.get(cItem)), 0, 0);
		TaskUtil.updateTaskPeriod(scheduler, (long) multiplierInfo.get(cItem));
	}

	@Override
	public boolean onClick(Player p, ItemStack current, int slot, ClickType click) {
		final PlayerStat stat = TaupePlugin.getInstance().getStatsManager().getUser(p.getUniqueId());

		if (stat != null) {
			if (current != null && current.isSimilar(cItem)) {
				total++;

				stat.setTotalScore(total);
				if (stat.getBestScore() < total) 
					stat.setBestScore(total);

				this.inv.remove(current);

				itemInfo.put(cItem, 2);
				int newSlotChoosed = (int) (Math.random() * this.getInventory().getSize());

				if (this.inv.getItem(newSlotChoosed) == null || this.inv.getItem(newSlotChoosed).getType() == Material.AIR)
					this.inv.setItem(newSlotChoosed, cItem);
				else 
					this.inv.setItem((int)(Math.random() * this.getInventory().getSize()), cItem);

				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 0);

				if (multiplierInfo.get(cItem) >= max)
					multiplierInfo.put(cItem, multiplierInfo.get(cItem) - multiplier);
			}
		}
		return true;
	}
}
