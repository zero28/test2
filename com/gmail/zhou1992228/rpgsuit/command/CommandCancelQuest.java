package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.quest.Quest;

public class CommandCancelQuest implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0.isOp()) {
			String name = arg3[0];
			Player p = Bukkit.getServer().getPlayer(arg3[1]);
			Quest.list.get(name).onCancel(p);
		}
		return true;
	}
}
