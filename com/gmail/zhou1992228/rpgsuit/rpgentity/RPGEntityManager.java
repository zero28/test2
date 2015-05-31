package com.gmail.zhou1992228.rpgsuit.rpgentity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;

public class RPGEntityManager {
	public static Map<LivingEntity, RPGEntity> entities = new HashMap<LivingEntity, RPGEntity>();
	public static void AddEntity(LivingEntity e) {
		if (e instanceof Player || (e instanceof Monster && MobUtil.isRPGMob((Monster) e))) {
			entities.put(e, new RPGEntity(e));
		}
	}
	public static void RemoveEntity(LivingEntity e) {
		entities.remove(e);
	}
	public static boolean Damage(LivingEntity attacker, LivingEntity defender, RPGDamageType type) {
		if (!entities.containsKey(attacker) || ! entities.containsKey(defender)) {
			return false;
		}
		entities.get(defender).DamageBy(entities.get(attacker), type);
		return true;
	}
	public static void Update() {
		for (RPGEntity e : entities.values()) {
			e.Update();
		}
		Set<LivingEntity> remove_list = new HashSet<LivingEntity>();
		for (LivingEntity e : entities.keySet()) {
			if (!e.isValid()) {
				remove_list.add(e);
			}
		}
		for (LivingEntity e : remove_list) {
			entities.remove(e);
		}
	}
}
