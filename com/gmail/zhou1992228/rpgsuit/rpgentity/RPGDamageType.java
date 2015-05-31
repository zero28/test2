package com.gmail.zhou1992228.rpgsuit.rpgentity;

import com.gmail.zhou1992228.rpgsuit.skill.DamageCalculator;

public enum RPGDamageType { 
	NORMAL(),
	ARROW_NORMAL(),
	SKILL_CHOP(),
	SKILL_SWEEP(),
	UNKNOWN();
	public void setDamageCalculator(DamageCalculator calculator) {
		this.damageCalculator = calculator;
	}
	public DamageCalculator getDamageCalculator() {
		return damageCalculator;
	}
	private DamageCalculator damageCalculator;
}
