package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.List;

import org.bukkit.entity.Player;

public class SkillBackStab extends ActionSkill {

	public SkillBackStab(String n) {
		super(n);
	}

	@Override
	boolean okToRelease(List<SkillAction> action) {
		if (action == null) { return false; }
		if (action.size() < 3) return false;
		SkillAction a1 = action.get(action.size() - 3);
		SkillAction a2 = action.get(action.size() - 2);
		SkillAction a3 = action.get(action.size() - 1);
		if (!a1.mat.name().contains("SWORD") ||
			!a2.mat.name().contains("SWORD") ||
			!a3.mat.name().contains("SWORD")) {
			return false;
		}
		if (a1.action != SkillAction.Action.SHIFT ||
			a2.action != SkillAction.Action.SHIFT ||
			a3.action != SkillAction.Action.RIGHT) {
			return false;
		}
		if (a2.time - a1.time > 1000 || a3.time - a2.time > 1000) {
			return false;
		}
		return true;
	}

	@Override
	void tryUse(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

}
