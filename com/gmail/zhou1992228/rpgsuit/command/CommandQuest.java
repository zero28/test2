package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.quest.Quest;

public class CommandQuest implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			for (Quest q : Quest.list.values()) {
				if (Quest.haveQuest(p, q.name)) {
					p.sendMessage(q.getDescription(p));
				}
			}
		}
		return true;
	}
}
