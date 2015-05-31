package com.gmail.zhou1992228.rpgsuit.util;

import java.text.DateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class VIPUtil implements CommandExecutor{
	
	public static void handleVipDaily(Player p) {
		String type = UserInfoUtils.getString(p, "VIP类别", "novip");
		long vip_end_time = UserInfoUtils.getInt(p, "vip_end_time");
		if (type.equalsIgnoreCase("novip") || vip_end_time < Util.Now()) return;
		String items = RPGSuit.ins.getConfig().getString("vip." + type + ".daily");
		UserInfoUtils.giveItems(p, items);
	}
	public static void handleVipWeekly(Player p) {
		String type = UserInfoUtils.getString(p, "VIP类别", "novip");
		long vip_end_time = UserInfoUtils.getInt(p, "vip_end_time");
		if (type.equalsIgnoreCase("novip") || vip_end_time < Util.Now()) return;
		String items = RPGSuit.ins.getConfig().getString("vip." + type + ".weekly");
		UserInfoUtils.giveItems(p, items);
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0.isOp()) {
			if (arg3.length < 3) {
				arg0.sendMessage("use /<command> id type time");
				return true;
			}
			OfflinePlayer p = (Player) Bukkit.getServer().getOfflinePlayer(arg3[0]);
			if (p == null) { arg0.sendMessage("no such player"); return true; }
			String type = arg3[1];
			int time = Integer.parseInt(arg3[2]);
			UserInfoUtils.set(p, "VIP类别", type);
			long end_time = Util.Now() + time * 24 * 60 * 60 * 1000;
			UserInfoUtils.set(p, "vip_end_time", end_time);
			Calendar cc = Calendar.getInstance();
			cc.setTimeInMillis(end_time);
			UserInfoUtils.set(p, "VIP结束时间", DateFormat.getDateInstance().format(cc.getTime()));
			Bukkit.getLogger().info("you've set " + p.getName() + " to " + type + " for " + time + " days!");
			arg0.sendMessage("you've set " + p.getName() + " to " + type + " for " + time + " days!");
		}
		return true;
	}
}
