package com.gmail.zhou1992228.rpgsuit.mob;

import org.bukkit.entity.Monster;
import org.bukkit.metadata.FixedMetadataValue;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class MobUtil {
	
	public static double expA, expB, expC;
	public static int exp[];
	public static void init() {
		exp = new int[156];
		for (int i = 0; i <= 155; ++i) {
			exp[i] = (int) ((i / 5 + i) * (i / 5 + i) * expA + i * expB + expC);
		}
	}
	
	public static final String STR_KEY = "STR_KEY";
	public static final String DEF_KEY = "DEF_KEY";
	public static final String AST_KEY = "AST_KEY";
	public static final String ADF_KEY = "ADF_KEY";
	public static final String DEX_KEY = "DEX_KEY";
	public static final String AGL_KEY = "AGL_KEY";
	public static final String LEVEL_KEY = "LEVEL_KEY";
	public static final String SKILL_KEY = "SKILL_KEY";
	public static RPGSuit plugin;

	public static void setupSkill(Monster m) {
		for (MonsterSkillBase sb : MonsterSkillBase.mobSkillList) {
			if (sb.shouldHasSkill(m)) {
				addSkill(m, sb.getKey());
			}
		}
	}

	public static void addSkill(Monster m, String skillKey) {
		m.setMetadata(skillKey, new FixedMetadataValue(plugin, true));
	}
	public static void setLevel(Monster m, int v) {
		m.setMetadata(LEVEL_KEY, new FixedMetadataValue(plugin, v));
	}
	public static void setStr(Monster m, int v) {
		m.setMetadata(STR_KEY, new FixedMetadataValue(plugin, v));
	}
	public static void setDef(Monster m, int v) {
		m.setMetadata(DEF_KEY, new FixedMetadataValue(plugin, v));
	}
	public static void setAst(Monster m, int v) {
		m.setMetadata(AST_KEY, new FixedMetadataValue(plugin, v));
	}
	public static void setAdf(Monster m, int v) {
		m.setMetadata(ADF_KEY, new FixedMetadataValue(plugin, v));
	}
	public static void setDex(Monster m, int v) {
		m.setMetadata(DEX_KEY, new FixedMetadataValue(plugin, v));
	}
	public static void setAgl(Monster m, int v) {
		m.setMetadata(AGL_KEY, new FixedMetadataValue(plugin, v));
	}
	public static void setHp(Monster m, int v) {
		m.setMaxHealth((double)v);
		m.setHealth(v - 0.01);
	}

	public static int getLevel(Monster m) {
		try {
			return m.getMetadata(LEVEL_KEY).get(0).asInt();
		} catch (Exception e) {
			return 0;
		}
	}
	public static int getStr(Monster m) {
		try {
			return m.getMetadata(STR_KEY).get(0).asInt();
		} catch (Exception e) {
			return -1;
		}
	}
	public static int getDef(Monster m) {
		try {
			return m.getMetadata(DEF_KEY).get(0).asInt();
		} catch (Exception e) {
			return -1;
		}
	}
	public static int getAst(Monster m) {
		try {
			return m.getMetadata(AST_KEY).get(0).asInt();
		} catch (Exception e) {
			return -1;
		}
	}
	public static int getAdf(Monster m) {
		try {
			return m.getMetadata(ADF_KEY).get(0).asInt();
		} catch (Exception e) {
			return -1;
		}
	}
	public static int getDex(Monster m) {
		try {
			return m.getMetadata(DEX_KEY).get(0).asInt();
		} catch (Exception e) {
			return -1;
		}
	}
	public static int getAgl(Monster m) {
		try {
			return m.getMetadata(AGL_KEY).get(0).asInt();
		} catch (Exception e) {
			return -1;
		}
	}
	public static boolean hasSkill(Monster m, String skillKey) {
		try {
			return m.getMetadata(skillKey).get(0).asBoolean();
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isBoss(Monster m) {
		try {
			return m.getMetadata(MobGenerator.RPGMOB_BOSS_KEY).get(0).asInt() == MobGenerator.RPGMOB_INT_KEY;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isRPGMob(Monster m) {
		try {
			return m.getMetadata(MobGenerator.RPGMOB_KEY).get(0).asInt() == MobGenerator.RPGMOB_INT_KEY;
		} catch (Exception e) {
			return false;
		}
	}
	
	public enum DamageType {
		ARROW,
		STICK,
		SWORD,
	}
	
	public static double damageWithStr(Monster m, int str, DamageType type) {
		return 0;
	}
}
