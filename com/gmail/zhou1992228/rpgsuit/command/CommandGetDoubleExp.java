package com.gmail.zhou1992228.rpgsuit.command;

import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandGetDoubleExp implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			if (UserInfoUtils.haveRequire(p, "s:" + RPGSuit.DOUBLE_EXP_LEFT_HOURE + ":1")) {
				long newv = Math.max(RPGSuit.now(), UserInfoUtils.getInt(p, RPGSuit.DOUBLE_EXP_END_TIME)) + 60 * 60 * 1000;
				UserInfoUtils.set(p, RPGSuit.DOUBLE_EXP_END_TIME, newv);
				UserInfoUtils.minus(p, RPGSuit.DOUBLE_EXP_LEFT_HOURE, 1);
				int left = (int) UserInfoUtils.getInt(p, RPGSuit.DOUBLE_EXP_LEFT_HOURE);
				Date d = new Date(newv);
				p.sendMessage("你的双倍经验截止时间为: " + d.toString() + "。快去杀怪吧~。本周剩余:" + left + "小时");
			} else {
				arg0.sendMessage("你本周的双倍经验时间已经用完，下周再来吧╮(╯▽╰)╭");
				return true;
			}
		}
		return true;
	}
}
