package com.gmail.zhou1992228.rpgsuit.command;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;

public class CommandRank implements CommandExecutor{

	public Random random = new Random();
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		int page = 1;
		try {
			page = Integer.parseInt(arg3[0]);
		} catch (Exception e) {
		}
		for (String s : PlayerUtil.rank.getPage(page)) {
			arg0.sendMessage(s);
		}
		return true;
	}

}
