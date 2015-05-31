package com.gmail.zhou1992228.rpgsuit.mob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Monster;

abstract public class MonsterSkillBase {
	public static ArrayList<MonsterSkillBase> mobSkillList = new ArrayList<MonsterSkillBase>();
	public static void InitMonsterSkill() {
		mobSkillList.add(new SkillShotShock());
		mobSkillList.add(new SkillStick());
		mobSkillList.add(new SkillRusty());
	}
	public static List<String> getSkillList(Monster m) {
		ArrayList<String> list = new ArrayList<String>();
		for (MonsterSkillBase msb : mobSkillList) {
			if (MobUtil.hasSkill(m, msb.getKey())) {
				list.add(msb.getKey());
			}
		}
		return list;
	}
	abstract public boolean shouldHasSkill(Monster m);
	abstract public String getKey();
}
