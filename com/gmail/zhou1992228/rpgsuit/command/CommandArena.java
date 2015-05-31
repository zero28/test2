package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.arena.ArenaManager;

public class CommandArena implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			ArenaManager.ins.tryJoin((Player) arg0, -1);
			return true;
		}
		return false;
	}
}
