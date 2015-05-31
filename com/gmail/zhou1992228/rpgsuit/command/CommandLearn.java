package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.skill.SkillBase;

public class CommandLearn implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			try {
				SkillBase.list.get(arg3[0]).learn((Player) arg0);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
