package com.gmail.zhou1992228.rpgsuit.guild;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class GuildSkillAddExp extends GuildSkill implements Listener {
	public static String KEY_EXP_MUTIPLIER = "经验加成系数";
	public GuildSkillAddExp() {
	}

	@Override
	public void applyToPlayer(Player p, int level) {
		UserInfoUtils.set(p, KEY_EXP_MUTIPLIER, level * 0.05 + 1);
	}

	@Override
	public void resetPlayer(Player p) {
		UserInfoUtils.set(p, KEY_EXP_MUTIPLIER, 1);
	}

	@Override
	public String upgradeCost(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
