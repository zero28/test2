package com.gmail.zhou1992228.rpgsuit.guild;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class GuildSkillAddExp extends GuildSkill implements Listener {
	public static String KEY_EXP_MUTIPLIER = "经验加成系数";
	private String name;
	public GuildSkillAddExp(String n) {
		name = n;
	}

	@Override
	public void applyToPlayer(Player p, Guild g) {
		int level = g.getSkillLevel(name);
		UserInfoUtils.set(p, KEY_EXP_MUTIPLIER, level * 5);
	}

	@Override
	public void resetPlayer(Player p) {
		UserInfoUtils.set(p, KEY_EXP_MUTIPLIER, 1);
	}

	@Override
	public String upgradeCost(int level) {
		return "建设度:" + level * 50 + " 金钱:" + level * level * 10000; 
	}

	@Override
	public String getDescription() {
		return "击杀怪物获得额外的经验值(5% * 等级)";
	}

	@Override
	public String name() {
		return name;
	}
}
