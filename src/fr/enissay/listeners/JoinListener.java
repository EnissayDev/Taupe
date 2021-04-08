package fr.enissay.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.enissay.TaupePlugin;
import fr.enissay.stats.PlayerStat;

public class JoinListener implements Listener{
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		TaupePlugin.getInstance().getStatsManager().addUser(new PlayerStat(player.getUniqueId(), 0, 0));
	}
}
