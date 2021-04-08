package fr.enissay.commands;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.enissay.TaupePlugin;
import fr.enissay.guis.TaupeFontGUI;
import fr.enissay.guis.TaupeGUI;
import fr.enissay.stats.PlayerStat;

public class TaupeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			final PlayerStat user = TaupePlugin.getInstance().getStatsManager().getUser(player.getUniqueId());

			if (args.length < 1) {
				new TaupeGUI().create(player);

				new BukkitRunnable() {

					int timer = 30;

					@Override
					public void run() {
						if (user != null && player != null) {
							if (timer <= 0) {
								player.closeInventory();

								player.sendMessage("");
								player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + " STATISTIQUES");
								player.sendMessage("");
								player.sendMessage(ChatColor.GOLD + " Total : " + ChatColor.YELLOW + user.getTotalScore());
								player.sendMessage(ChatColor.GOLD + " Score/s : " + ChatColor.YELLOW + Double.valueOf(user.getTotalScore() / 20));
								player.sendMessage("");
								
								if (user.getTotalScore() / 20 >= 2)
									player.sendTitle(ChatColor.AQUA + "Wow quelle vitesse !", "", 0, 5, 0);

								// TaupePlugin.getInstance().getStatsManager().removeUser(user);
								this.cancel();

								final Location loc = player.getLocation();
								final Firework f = player.getWorld().spawn(loc, Firework.class);
								final FireworkMeta fm = f.getFireworkMeta();
								fm.addEffect(FireworkEffect.builder().flicker(true).trail(true)
										.with(FireworkEffect.Type.BALL_LARGE).withColor(Color.AQUA).build());
								fm.setPower((int) 0.5);
								f.setFireworkMeta(fm);
							}
							timer--;
						} else
							cancel();
					}
				}.runTaskTimer(TaupePlugin.getInstance(), 0L, 20L);
			} else if (args.length >= 1 && args[0].equalsIgnoreCase("font")){
				new TaupeFontGUI().create(player);
			}
		}
		return true;
	}
}
