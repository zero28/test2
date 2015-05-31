package com.gmail.zhou1992228.rpgsuit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class TaskMinuteUpdater implements Runnable {

	public static String KEY_STRENGTH = "ÃÂ¡¶";
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			int lv = PlayerUtil.getLevel(p);
			int new_value = (int) Math.min(UserInfoUtils.getInt(p, KEY_STRENGTH) + lv / 10 + 1, 100 + lv / 10 * 10);
			UserInfoUtils.set(p, KEY_STRENGTH, new_value);
		}
	}

}
