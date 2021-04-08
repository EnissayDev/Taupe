package fr.enissay.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import fr.enissay.TaupePlugin;
import fr.enissay.stats.StatsManager;
import fr.enissay.utils.gui.GUI;
import fr.enissay.utils.items.ItemBuilder;

public class TaupeFontGUI extends GUI {

	private StatsManager manager = TaupePlugin.getInstance().getStatsManager();
	
	private ItemStack voidItem = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build();

	public TaupeFontGUI() {
		super("Taupe Font", 6);
		for (int i = 0; i < this.inv.getSize(); i++)
			this.inv.setItem(i, voidItem);
		manager.getFont().entrySet()
				.forEach(entry -> this.inv.setItem(entry.getValue(), entry.getKey()));
	}

	@Override
	public boolean onClick(Player p, ItemStack current, int slot, ClickType click) {
		
		if (current.isSimilar(voidItem)) {
			ItemStack newItem = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("" + manager.getFont().size()).build();

			manager.addItem(newItem, slot);
			this.inv.setItem(slot, newItem);
			return true;
		} 
		manager.removeItem(current);
		this.inv.remove(current);
		this.inv.setItem(slot, voidItem);
		
		return true;
	}
}