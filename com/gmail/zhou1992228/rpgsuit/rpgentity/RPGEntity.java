package com.gmail.zhou1992228.rpgsuit.rpgentity;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.DamageHandler;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class RPGEntity {
	public enum RPGEntityType {
		PLAYER,
		MONSTER,
	}

	public RPGEntity(LivingEntity e) {
		this.entity = e;
		if (e instanceof Player) {
			str = PlayerUtil.getStr((Player) e);
			def = PlayerUtil.getDef((Player) e);
			maxhp = PlayerUtil.getHp((Player) e);
			type = RPGEntityType.PLAYER;
		} else if (e instanceof Monster ){
			str = MobUtil.getStr((Monster) e);
			def = MobUtil.getDef((Monster) e);
			maxhp = ((Damageable)entity).getHealth();
			type = RPGEntityType.MONSTER;
		}
	}
	
	public static void addAngry(LivingEntity p, int count) {
		long newCP = Math.min(count + UserInfoUtils.getInt((Player) p, DamageHandler.ANGRY_KEY), 200);
		UserInfoUtils.set((Player) p, DamageHandler.ANGRY_KEY, newCP);
	}
	
	public static void getAngry(RPGEntity attacker, RPGEntity defender, RPGDamageType type, double actual_damage) {
		switch (type) {
			case NORMAL:
				if (attacker.getType() == RPGEntityType.PLAYER) {
					if (attacker.getType() == RPGEntityType.MONSTER) {
						addAngry(attacker.getEntity(), 2);
					}
				} else {
					if (defender.getType() == RPGEntityType.PLAYER) {
						int angry = 0;
						if (actual_damage > 15.0) angry += 1;
						if (actual_damage > 10.0) angry += 1;
						if (actual_damage > 6.0) angry += 1;
						if (actual_damage > 3.0) angry += 1;
						if (actual_damage > 1.0) angry += 1;
						if (actual_damage > 0.0) angry += 1;
						addAngry(defender.getEntity(), angry);
					}
				}
				break;
			case ARROW_NORMAL:
				break;
			case SKILL_CHOP:
				break;
			case SKILL_SWEEP:
				break;
			default:
				break;
		}
	}
	
	public void DamageBy(RPGEntity attacker, RPGDamageType type) {
		double actual_damage;
		if (this.type == RPGEntityType.MONSTER &&
			attacker.getType() == RPGEntityType.MONSTER) {
			return;
		}
		if (type == RPGDamageType.NORMAL) {
			actual_damage = (attacker.getStr() - getDef()) * 4;
		} else if  (type == RPGDamageType.ARROW_NORMAL) {
			actual_damage = (attacker.getStr() - getDef()) * 4;
		} else {
			actual_damage = type.getDamageCalculator().CalDamage(attacker, this);
		}
		if ((entity instanceof Monster && !MobUtil.isBoss((Monster) entity)) || entity instanceof Player) {
			actual_damage = Math.max(maxhp * 0.008, actual_damage);
		}
		actual_damage = Damage(actual_damage);
		getAngry(attacker, this, type, actual_damage);
	}
	
	public double Damage(double damage) {
		if (damage < 0) return 0;
		double health = ((Damageable)entity).getHealth();
		if (type == RPGEntityType.PLAYER) {
			damage = DamageTransfer(damage);
		}
		health -= damage;
		if (health < 0) health = 0;
		((Damageable)entity).setHealth(health);
		return damage;
	}
	
	public long getStr() {
		return str;
	}

	public long getDef() {
		return def;
	}
	
	public void Update() {
		if (type == RPGEntityType.PLAYER) {
			PlayerUtil.updateUserInfo((Player) entity);
			str = PlayerUtil.getStr((Player) entity);
			def = PlayerUtil.getDef((Player) entity);
			maxhp = PlayerUtil.getHp((Player) entity);
		}
	}

	private double DamageTransfer(double damage) {
		return damage * 20.0 / maxhp;
	}
	
	private LivingEntity entity;
	private RPGEntityType type;
	public RPGEntityType getType() {
		return type;
	}

	private long str, def;
	public LivingEntity getEntity() {
		return entity;
	}

	public void setDef(long def) {
		this.def = def;
	}

	private double maxhp;
}
