package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.player.RPGScoreBoard;

public class CommandRefresh implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player && arg0.isOp()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				PlayerUtil.updateUserInfo(p);
				RPGScoreBoard.UpdateScoreBoardFor(p);
			}
		}
		return true;
	}
}
