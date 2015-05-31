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
		list.put("轻功", new SkillFallProtect("轻功"));
		list.put("打雷", new SkillLighting("打雷"));
		list.put("剑气", new SkillChop("剑气"));
		RPGDamageType.SKILL_CHOP.setDamageCalculator((DamageCalculator) list.get("剑气"));
		list.put("旋风轮", new SkillSweep("旋风轮"));
		RPGDamageType.SKILL_SWEEP.setDamageCalculator((DamageCalculator) list.get("旋风轮"));
		list.put("射箭", new SkillShotArrow("射箭"));
		list.put("多段跳", new SkillDoubleJump("多段跳"));
		list.put("炼药", new SkillMakeMed("炼药"));
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
		return (int) UserInfoUtils.getInt(p, "技能" + name + "等级");
	}
	public int getLevel(RPGEntity p) {
		if (!(p.getEntity() instanceof Player)) return 0;
		return (int) UserInfoUtils.getInt((Player) p.getEntity(), "技能" + name + "等级");
	}
	abstract public String getDescription();
	abstract public String getDescription(Player p);
	public boolean okToLearn(Player p) {
		int lv = this.getLevel(p);
		String req = "$" + this.needMoney(lv) + " s:经验值:" + this.needExp(lv);
		return UserInfoUtils.haveRequires(p, req) && lv < PlayerUtil.getLevel(p);
	}
	public void onLearn(Player p) {
		UserInfoUtils.add(p, "技能" + name + "等级", 1);
		int lv = this.getLevel(p);
		String req = "$" + this.needMoney(lv) + " s:经验值:" + this.needExp(lv);
		UserInfoUtils.takeItems(p, req);
		p.sendMessage("技能等级提升！");
	}
	public String getLearnDescription(Player p) {
		return "学习下一级需要经验值 " + this.needExp(this.getLevel(p)) +
			   "  金钱 " + this.needMoney(this.getLevel(p));
	}
	public String unableToLearnMessage() {
		return "你没有足够的等级/金钱/经验来学习这项技能";
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
