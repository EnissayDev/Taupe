package fr.enissay.stats;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class StatsManager {

	public final LinkedList<PlayerStat> stats = new LinkedList<>();
	public final Map<ItemStack, Integer> font = new HashMap<>();

	public void addUser(final PlayerStat player) {
		if (!stats.contains(player))
			stats.add(player);
	}

	public void removeUser(final PlayerStat player) {
		stats.removeIf(user -> !stats.contains(player));
	}

	public void addItem(final ItemStack item, final Integer slot) {
		if (!font.containsKey(item))
			font.put(item, slot);
	}

	public void removeItem(final ItemStack item) {
		if (font.containsKey(item))
			font.remove(item);
	}

	public PlayerStat getUser(final UUID uuid) {
		return stats.stream().filter(stat -> stat != null && stat.getUUID().equals(uuid)).findAny().orElse(null);
	}

	public LinkedList<PlayerStat> getStats() {
		return stats;
	}

	public Map<ItemStack, Integer> getFont() {
		return font;
	}
}
