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
				p.sendMessage("����Ҫ$" + lv + "��ʹ�ô����");
				return true;
			}
			if (!Util.InMobWorld(p)) {
				p.sendMessage("������ֻ���ڹ�������ʹ��");
				return true;
			}
			try {
				random.setSeed(2222222);
				int x = Integer.parseInt(arg3[0]) + random.nextInt(2000) - 1000;
				int z = Integer.parseInt(arg3[1]) + random.nextInt(2000) - 1000;
				p.teleport(new Location(p.getWorld(), x, 80, z));
				UserInfoUtils.takeItem(p, "$" + lv);
			} catch (Exception e) {
				p.sendMessage("������/ntp x z ������ģ������");
			}
		}
		return true;
	}
}
