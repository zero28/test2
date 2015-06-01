package com.gmail.zhou1992228.rpgsuit.guild;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public abstract class GuildSkill {
	public static Map<String, GuildSkill> list = new HashMap<String, GuildSkill>();
	public static void Init() {
		list.put("Á·¹¦·¿", new GuildSkillAddExp());
	}
	abstract public void applyToPlayer(Player p, int level);
	abstract public void resetPlayer(Player p);
	abstract public String upgradeCost(int level);
}
