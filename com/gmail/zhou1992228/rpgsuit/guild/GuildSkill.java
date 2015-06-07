package com.gmail.zhou1992228.rpgsuit.guild;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public abstract class GuildSkill {
	public static Map<String, GuildSkill> list = new HashMap<String, GuildSkill>();
	public static void Init() {
		list.put("练功房", new GuildSkillAddExp("练功房"));
	}
	abstract public void applyToPlayer(Player p, Guild g);
	abstract public void resetPlayer(Player p);
	abstract public String upgradeCost(int level);
	abstract public String getDescription();
	abstract public String name();
}
