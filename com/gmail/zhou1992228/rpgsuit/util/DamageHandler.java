package com.gmail.zhou1992228.rpgsuit.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.listener.DamageListener;
import com.gmail.zhou1992228.rpgsuit.skill.DamageCalculator;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class DamageHandler {
	final public static String ANGRY_KEY = "Å­ÆøÖµ";
	public static void Damage(LivingEntity a, LivingEntity b, DamageCalculator c, boolean add_angry) {
		double damage = c.CalDamage(a, b);
		if (b instanceof Player) {
			damage = DamageListener.damageTransfer(damage, (Player) b);
		}
		b.damage(-damage, a);
		if (add_angry) {
			if (b instanceof Monster) {
				GetCPWithDamageMob((Player)a, 0);
			} else {
				GetCPWithDamagePlayer((Player)a, damage);
			}
		}
		if (b instanceof Player) {
			GetCPWithDamaged((Player)b, damage);
		}
	}
	
	public static void GetCPWithDamage(Player p, LivingEntity e, double damage) {
		if (e instanceof Monster) {
			GetCPWithDamageMob(p, 0);
		} else {
			GetCPWithDamagePlayer(p, damage);
		}
	}
	
	public static void GetCPWithDamagePlayer(Player p, double damage) {
		if (p == null) return;
		int count = 0;
		if (damage > 15.0) count += 1;
		if (damage > 10.0) count += 1;
		if (damage > 6.0) count += 1;
		if (damage > 3.0) count += 1;
		if (damage > 1.0) count += 1;
		if (damage > 0.0) count += 1;
		long newCP = Math.min(count + UserInfoUtils.getInt(p, ANGRY_KEY), 200);
		UserInfoUtils.set(p, ANGRY_KEY, newCP);
	}
	public static void GetCPWithDamageMob(Player p, double percent) {
		if (p == null) return;
		int count = 2;
		long newCP = Math.min(count + UserInfoUtils.getInt(p, ANGRY_KEY), 200);
		UserInfoUtils.set(p, ANGRY_KEY, newCP);
	}
	public static void GetCPWithDamaged(Player p, double damage) {
		if (p == null) return;
		int count = 0;
		if (damage > 15.0) count += 1;
		if (damage > 10.0) count += 1;
		if (damage > 6.0) count += 1;
		if (damage > 3.0) count += 1;
		if (damage > 1.0) count += 1;
		if (damage > 0.0) count += 1;
		long newCP = Math.min(count + UserInfoUtils.getInt(p, ANGRY_KEY), 200);
		UserInfoUtils.set(p, ANGRY_KEY, newCP);
	}
}
