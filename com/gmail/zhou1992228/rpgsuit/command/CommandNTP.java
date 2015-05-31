package com.gmail.zhou1992228.rpgsuit.command;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandNTP implements CommandExecutor{

	Random random = new Random();
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			int lv = PlayerUtil.getLevel(p);
			if (!UserInfoUtils.haveRequire(p, "$" + lv)) {
				p.sendMessage("你需要$" + lv + "来使用此命令！");
				return true;
			}
			if (!Util.InMobWorld(p)) {
				p.sendMessage("此命令只能在怪物世界使用");
				return true;
			}
			try {
				random.setSeed(2222222);
				int x = Integer.parseInt(arg3[0]) + random.nextInt(2000) - 1000;
				int z = Integer.parseInt(arg3[1]) + random.nextInt(2000) - 1000;
				p.teleport(new Location(p.getWorld(), x, 80, z));
				UserInfoUtils.takeItem(p, "$" + lv);
			} catch (Exception e) {
				p.sendMessage("请输入/ntp x z 来进行模糊传送");
			}
		}
		return true;
	}
}
