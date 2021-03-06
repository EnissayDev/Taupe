package fr.enissay.utils.gui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Inventories implements Listener {

	private static Map<Player, GUI> g = new HashMap<>();
	private static List<InventoryType> notHoldable = Arrays.asList(InventoryType.DISPENSER, InventoryType.DROPPER, InventoryType.FURNACE, InventoryType.BREWING, InventoryType.HOPPER);

	private static boolean close = false;

	public static void closeAll() {
		for (Iterator<Player> iterator = g.keySet().iterator(); iterator.hasNext();) {
			Player p = iterator.next();
			iterator.remove();
			p.closeInventory();
		}
	}

	public static void closeAndExit(Player p) {
		g.remove(p);
		p.closeInventory();
	}

	public static void closeWithoutExit(Player p) {
		if (!g.containsKey(p)) {
			return;
		}
		if (p.getOpenInventory().getType() == InventoryType.CRAFTING) {
			return;
		}
		close = true;
		p.closeInventory();
	}

	public static <T extends GUI> T create(Player p, T inv) {
		closeWithoutExit(p);
		p.openInventory(inv.getInventory());
		g.put(p, inv);
		return inv;
	}

	private static GUI getGUI(Inventory inv) {
		if (inv == null) {
			return null;
		}
		InventoryHolder holder = inv.getHolder();
		if (holder != null && holder instanceof GUI) {
			return (GUI) holder;
		}
		if (notHoldable.contains(inv.getType())) {
			for (GUI gui : g.values()) {
				if (gui.getInventory().equals(inv)) {
					return gui;
				}
			}
		}
		return null;
	}

	public static boolean isInSystem(Player p) {
		return g.containsKey(p);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		ItemStack current = e.getCurrentItem();

		GUI gui = getGUI(inv);
		if (gui == null) {
			return;
		}

		e.setCancelled(true);

		if (inv == p.getInventory()) {
			if (e.isShiftClick()) {
				e.setCancelled(true);
			}
			return;
		}
		if (e.getCursor().getType() == Material.AIR) {
			if (current == null || current.getType() == Material.AIR) {
				return;
			}
			if (gui.onClick(p, current, e.getSlot(), e.getClick())) {
				e.setCancelled(true);
			}
		} else {
			if (gui.onClickCursor(p, current, e.getCursor(), e.getSlot())) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (close) {
			close = false;
			return;
		}
		if (g.containsKey(p)) {
			GUI gui = g.get(p);
			if (!e.getInventory().equals(gui.getInventory())) {
				return;
			}
			if (gui.onClose(p)) {
				g.remove(p);
			}
		}
	}

}