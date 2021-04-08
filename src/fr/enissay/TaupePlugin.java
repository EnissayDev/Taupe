package fr.enissay;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.enissay.commands.RankingCommand;
import fr.enissay.commands.TaupeCommand;
import fr.enissay.listeners.JoinListener;
import fr.enissay.stats.StatsManager;
import fr.enissay.utils.gui.Inventories;

public class TaupePlugin extends JavaPlugin{
	
	public static TaupePlugin instance;
	public StatsManager statsManager;
	
	@Override
	public void onEnable() {
		
		instance = this;
		statsManager = new StatsManager();
		
		// Listeners
		Bukkit.getPluginManager().registerEvents(new Inventories(), this);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		
		// Commands
		this.getCommand("taupe").setExecutor(new TaupeCommand());
		this.getCommand("ranking").setExecutor(new RankingCommand());
	}

	public static TaupePlugin getInstance() {
		return instance;
	}

	public StatsManager getStatsManager() {
		return statsManager;
	}
	
}
