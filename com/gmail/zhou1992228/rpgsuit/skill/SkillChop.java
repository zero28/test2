package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.listener.DamageListener;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGDamageType;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntity;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntityManager;
import com.gmail.zhou1992228.rpgsuit.util.DamageHandler;
import com.gmail.zhou1992228.rpgsuit.util.TargetUtils;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class SkillChop extends ActionSkill implements DamageCalculator {

	public SkillChop(String n) {
		super(n);
		SkillPaternUtil.ins.register(this);
		this.expa = 0.4;
		this.mona = 2.5;
	}

	@Override
	boolean okToRelease(List<SkillAction> action) {
		if (action == null) { return false; }
		if (action.size() < 2) return false;
		SkillAction a1 = action.get(action.size() - 2);
		SkillAction a2 = action.get(action.size() - 1);
		if (!a1.mat.name().contains("SWORD") ||
			!a2.mat.name().contains("SWORD")) {
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
		int lv = this.getLevel(p);
		if (lv == 0) return;
		if (UserInfoUtils.getInt(p, DamageHandler.ANGRY_KEY) < 20) {
			p.sendMessage("你没有足够的怒气使用剑气！");
			return;
		}
		List<LivingEntity> ee = TargetUtils.getConeTargets(p, 20, 5 + lv / 15);
		for (LivingEntity e : ee) {
			if (!RPGEntityManager.Damage(p, e, RPGDamageType.SKILL_CHOP)) {
				DamageHandler.Damage(p, e, this, false);
			}
			if (e instanceof Player) {
				((Player)e).sendMessage(p.getName() + " 对你使用了剑气！");
			}
		}
		if (ee.size() > 0) {
			p.sendMessage("使用了 剑气！");
			UserInfoUtils.minus(p, DamageHandler.ANGRY_KEY, 20);
		}
	}

	@Override
	public String getDescription() {
		return "小伙子，我看你骨骼惊奇，我将的的绝学“剑气”传授给你吧，攻击前方直线上的敌人";
	}

	@Override
	public String getDescription(Player p) {
		int lv = this.getLevel(p);
		double ts = (2 + (lv / 30.0)) / 3.0;
		return "当前等级为: " + lv + ",   造成  " + ts + " 倍 伤害, 距离 " + (5 + lv / 15) + " 格";
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
		return (str - def) * (2 + (this.getLevel((Player) a) / 30.0));
	}

	@Override
	public double CalDamage(RPGEntity attacker, RPGEntity defender) {
		return (attacker.getStr() - defender.getDef()) * (2 + (this.getLevel(attacker)) / 30.0);
	}

}
