package fr.enissay.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.enissay.TaupePlugin;
import fr.enissay.stats.PlayerStat;
import fr.enissay.stats.ranking.Ranking;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class RankingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String args, String[] label) {

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			final LinkedList<PlayerStat> players = TaupePlugin.getInstance().getStatsManager().getStats();

			if (players == null) {
				player.sendMessage("");
				player.sendMessage(ChatColor.RED + "AUCUN CLASSEMENT POUR LE MOMENT");
				player.sendMessage("");
				return true;
			}

			player.sendMessage("");
			player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + " CLASSEMENT");
			player.sendMessage("");

			int[] score = { Integer.MIN_VALUE };
			int[] no = { 0 };
			int[] rank = { 0 };

			/*
			 * AVEC LIMITE DE 10
			 */
			List<Ranking> ranking = players.stream().sorted((a, b) -> b.getBestScore() - a.getBestScore()).map(p -> {
				++no[0];
				if (score[0] != p.getBestScore())
					rank[0] = no[0];
				return new Ranking(rank[0], score[0] = p.getBestScore(), p.getUUID());
			}).limit(10).collect(Collectors.toList());

			ranking.forEach(ranki -> {
				final Player rankingPlayer = Bukkit.getPlayer(ranki.getUUID());
				final String str = String.valueOf(ChatColor.AQUA + ChatColor.BOLD.toString() + " #" + ranki.getRank()
						+ ChatColor.WHITE + " " + rankingPlayer.getName() + ChatColor.DARK_AQUA + " -> "
						+ ChatColor.YELLOW.toString() + ChatColor.BOLD + ranki.getScore());

				rankingPlayer.sendMessage(str);
			});

			/*
			 * AUCUNE LIMITE
			 */
			List<Ranking> ranks = players.stream().sorted((a, b) -> b.getBestScore() - a.getBestScore()).map(p -> {
				++no[0];
				if (score[0] != p.getBestScore())
					rank[0] = no[0];
				return new Ranking(rank[0], score[0] = p.getBestScore(), p.getUUID());
			}).collect(Collectors.toList());

			final Ranking rankOfPlayer = ranks.stream()
					.filter(rankingPlayers -> rankingPlayers.getUUID() == player.getUniqueId()).findAny().orElse(null);

			if (rankOfPlayer != null) {
				player.sendMessage("");
				player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + " VOTRE CLASSEMENT : "
						+ ChatColor.YELLOW + "#" + rankOfPlayer.getRank() + " " + ChatColor.GRAY + "(" + ChatColor.AQUA
						+ rankOfPlayer.getScore() + ChatColor.GRAY + ")");
				player.sendMessage("");
			}
		}
		return true;
	}
}