package com.gmail.zhou1992228.rpgsuit.skill;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkillAction {
	enum Action {
		LEFT,
		RIGHT,
		SHIFT,
		DASH,
	}
	
	@SuppressWarnings("unused")
	private SkillAction() {}
	
	public SkillAction(Action a, long t, double p, double y, ItemStack it) {
		action = a;
		time = t;
		// lowest: 90, highest: -90
		pitch = p;
		yaw = y;
		mat = it == null ? null : it.getType();
	}
	
	public Action action;
	public long time;
	public double pitch, yaw;
	public Material mat;
}
