package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.quest.Quest;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandITWNPC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0.isOp()) {
			if (arg3.length < 2) return true;
			Player p = Bukkit.getPlayer(arg3[0]);
			if (p == null) return true;
			for (String questName : UserInfoUtils.getString(p, "任务列表", "").split(",")) {
				if (!questName.isEmpty() &&
					Quest.list.containsKey(questName) &&
					Quest.list.get(questName).OnInteractWith(arg3[1], p)) {
					return true;
				}
			}
			Quest.tryRandomRequest(p, arg3[1]);
		}
		return true;
	}
}
