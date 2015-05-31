package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.List;

import org.bukkit.entity.Player;


public abstract class ActionSkill extends SkillBase {

	public ActionSkill(String n) {
		super(n);
	}
	abstract boolean okToRelease(List<SkillAction> action);
	abstract void tryUse(Player p);
}
