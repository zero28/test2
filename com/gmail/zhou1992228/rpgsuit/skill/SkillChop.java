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
			p.sendMessage("��û���㹻��ŭ��ʹ�ý�����");
			return;
		}
		List<LivingEntity> ee = TargetUtils.getConeTargets(p, 20, 5 + lv / 15);
		for (LivingEntity e : ee) {
			if (!RPGEntityManager.Damage(p, e, RPGDamageType.SKILL_CHOP)) {
				DamageHandler.Damage(p, e, this, false);
			}
			if (e instanceof Player) {
				((Player)e).sendMessage(p.getName() + " ����ʹ���˽�����");
			}
		}
		if (ee.size() > 0) {
			p.sendMessage("ʹ���� ������");
			UserInfoUtils.minus(p, DamageHandler.ANGRY_KEY, 20);
		}
	}

	@Override
	public String getDescription() {
		return "С���ӣ��ҿ���������棬�ҽ��ĵľ�ѧ�����������ڸ���ɣ�����ǰ��ֱ���ϵĵ���";
	}

	@Override
	public String getDescription(Player p) {
		int lv = this.getLevel(p);
		double ts = (2 + (lv / 30.0)) / 3.0;
		return "��ǰ�ȼ�Ϊ: " + lv + ",   ���  " + ts + " �� �˺�, ���� " + (5 + lv / 15) + " ��";
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
