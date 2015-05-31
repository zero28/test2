package com.gmail.zhou1992228.rpgsuit.skill;

import org.bukkit.entity.LivingEntity;

import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntity;

public interface DamageCalculator {
	abstract public double CalDamage(LivingEntity a, LivingEntity b);
	abstract public double CalDamage(RPGEntity attacker, RPGEntity defender);
}
