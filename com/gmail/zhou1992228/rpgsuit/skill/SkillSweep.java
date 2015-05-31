package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.listener.DamageListener;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGDamageType;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntity;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntityManager;
import com.gmail.zhou1992228.rpgsuit.task.TaskSweep;
import com.gmail.zhou1992228.rpgsuit.util.DamageHandler;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class SkillSweep extends ActionSkill implements DamageCalculator {

	public SkillSweep(String n) {
		super(n);
		SkillPaternUtil.ins.register(this);
		this.expa = 0.3;
		this.mona = 2.5;
	}

	@Override
	boolean okToRelease(List<SkillAction> action) {
		if (action == null) { return false; }
		if (action.size() < 2) return false;
		SkillAction a1 = action.get(action.size() - 2);
		SkillAction a2 = action.get(action.size() - 1);
		if (a1.mat.name().contains("SWORD") ||
			a2.mat.name().contains("SWORD")) {
			return false;
		}
		if (a1.action != SkillAction.Action.RIGHT ||
			a2.action != SkillAction.Action.LEFT) {
			return false;
		}
		if (a2.time - a1.time > 1000) {
			return false;
		}
		return true;
	}

	@Override
	void tryUse(Player p) {
		int level = this.getLevel(p);
		if (PlayerUtil.getStr(p) == 0) { return; }
		if (level == 0) return;
		if (UserInfoUtils.getInt(p, DamageHandler.ANGRY_KEY) < 25) {
			p.sendMessage("你没有足够的怒气使用旋风轮！");
			return;
		}
		double range = 2 + level / 30.0;
		List<Entity> list = p.getNearbyEntities(range, 3, range);
		boolean has_target = false;
		for (Entity ee : list) {
			if (ee instanceof LivingEntity) {
				if (RPGEntityManager.Damage(p, (LivingEntity) ee, RPGDamageType.SKILL_SWEEP)) {
					Throw(p, (LivingEntity) ee, 1.4 + level / 100.0);
					has_target = true;
					continue;
				}
			}
			if (ee instanceof LivingEntity) {
				if (ee.hasMetadata("NPC")) {
					has_target = true;
					p.sendMessage("居然想袭击我，太年轻了");
					p.getWorld().strikeLightning(p.getLocation());
				} else if (ee instanceof Player && ((Player)ee).isOp()) {
					DamageHandler.Damage((LivingEntity) ee, p, this, true);
					Throw((LivingEntity) ee, p, 1.4 + level / 100.0);
					p.sendMessage("你的攻击被反弹回来了！");
				} else {
					has_target = true;
					DamageHandler.Damage(p, (LivingEntity) ee, this, true);
					if (ee instanceof Player) {
						((Player)ee).sendMessage(p.getName() + " 对你使用了旋风轮！");
					}
					Throw(p, (LivingEntity) ee, 1.4 + level / 100.0);
				}
			}
		}
		if (has_target) {
			UserInfoUtils.minus(p, DamageHandler.ANGRY_KEY, 25);
			p.sendMessage("使用了 旋风轮！");
			RPGSuit.ins.getServer().getScheduler().scheduleSyncDelayedTask(
					RPGSuit.ins, new TaskSweep(RPGSuit.ins, (Player) p, 1, 20, 45));
		} else {
			p.sendMessage("周围没有敌人！");
		}
	}

	@Override
	public String getDescription() {
		return "使用棍子转圈就可以将敌人都扫开了，什么？你会晕？跟我练习一下就不晕了(手持RPG棍子 右-左 释放)";
	}

	@Override
	public String getDescription(Player p) {
		int level = this.getLevel(p);
		double ts = (1 + (level / 30.0)) / 3.0;
		return "当前等级为: " + this.getLevel(p) + ",   造成  " + ts + " 倍 伤害" + "  攻击范围: " + (2 + level / 30.0);
	}

	@Override
	public double CalDamage(LivingEntity a, LivingEntity b) {
		long str = DamageListener.getStr(a);
		long def = DamageListener.getDef(b);
		if (str == -1 || def == -1) {
			return 0;
		}
		if (!(a instanceof Player)) {
			return 0;
		}
		return (str - def) * (1 + (this.getLevel((Player) a) / 30.0));
	}

	public static void Throw(LivingEntity a, LivingEntity b, double mul) {
		Location bLoc = a.getLocation();
	    Location loc = b.getLocation();
	    Vector v = new Vector(loc.getX() - bLoc.getX(), 0.0D, loc.getZ() - bLoc.getZ());
	    b.setVelocity(v.normalize().multiply(mul).setY(0.8D));
	}

	@Override
	public double CalDamage(RPGEntity attacker, RPGEntity defender) {
		return (attacker.getStr() - defender.getDef()) * (1 + (this.getLevel(attacker) / 30.0));
	}
}
