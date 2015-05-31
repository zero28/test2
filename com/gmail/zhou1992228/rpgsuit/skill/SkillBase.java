package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGDamageType;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntity;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public abstract class SkillBase {
	static public Map<String, SkillBase> list = new HashMap<String, SkillBase>();
	static public void InitSkills() {
		list.put("�Ṧ", new SkillFallProtect("�Ṧ"));
		list.put("����", new SkillLighting("����"));
		list.put("����", new SkillChop("����"));
		RPGDamageType.SKILL_CHOP.setDamageCalculator((DamageCalculator) list.get("����"));
		list.put("������", new SkillSweep("������"));
		RPGDamageType.SKILL_SWEEP.setDamageCalculator((DamageCalculator) list.get("������"));
		list.put("���", new SkillShotArrow("���"));
		list.put("�����", new SkillDoubleJump("�����"));
		list.put("��ҩ", new SkillMakeMed("��ҩ"));
	}
	public String name;
	public double expe, expa, expb;
	public double mone, mona, monb;
	public Set<String> learnSet = new HashSet<String>();
	public int needExp(int lv) {
		int base = MobUtil.exp[lv] * PlayerUtil.mobCountToLevelUp(lv);
		return (int) (expa * Math.pow(base, expe) + expb);
	}
	public int needMoney(int lv) {
		int base = MobUtil.exp[lv];
		return (int) (mona * Math.pow(base, mone) + monb);
	}
	public SkillBase(String n) {
		name = n;
		FileConfiguration config = RPGSuit.getConfigWithName("skills.yml");
		expe = config.getDouble(name + ".expe", 1);
		expa = config.getDouble(name + ".expa", 0.3);
		expb = config.getDouble(name + ".expb", 50);
		mone = config.getDouble(name + ".mone", 1.1);
		mona = config.getDouble(name + ".mona", 2.5);
		monb = config.getDouble(name + ".monb", 0);
	}
	public int getLevel(Player p) {
		return (int) UserInfoUtils.getInt(p, "����" + name + "�ȼ�");
	}
	public int getLevel(RPGEntity p) {
		if (!(p.getEntity() instanceof Player)) return 0;
		return (int) UserInfoUtils.getInt((Player) p.getEntity(), "����" + name + "�ȼ�");
	}
	abstract public String getDescription();
	abstract public String getDescription(Player p);
	public boolean okToLearn(Player p) {
		int lv = this.getLevel(p);
		String req = "$" + this.needMoney(lv) + " s:����ֵ:" + this.needExp(lv);
		return UserInfoUtils.haveRequires(p, req) && lv < PlayerUtil.getLevel(p);
	}
	public void onLearn(Player p) {
		UserInfoUtils.add(p, "����" + name + "�ȼ�", 1);
		int lv = this.getLevel(p);
		String req = "$" + this.needMoney(lv) + " s:����ֵ:" + this.needExp(lv);
		UserInfoUtils.takeItems(p, req);
		p.sendMessage("���ܵȼ�������");
	}
	public String getLearnDescription(Player p) {
		return "ѧϰ��һ����Ҫ����ֵ " + this.needExp(this.getLevel(p)) +
			   "  ��Ǯ " + this.needMoney(this.getLevel(p));
	}
	public String unableToLearnMessage() {
		return "��û���㹻�ĵȼ�/��Ǯ/������ѧϰ�����";
	}
	public void learn(Player p) {
		if (learnSet.contains(p.getName())) {
			if (okToLearn(p)) {
				onLearn(p);
			} else {
				p.sendMessage(this.unableToLearnMessage());
			}
			learnSet.remove(p.getName());
		} else {
			learnSet.add(p.getName());
			p.sendMessage(getDescription());
			p.sendMessage(getDescription(p));
			p.sendMessage(getLearnDescription(p));
		}
	}
	
}
