package com.gmail.zhou1992228.rpgsuit.mob;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.metadata.FixedMetadataValue;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntityManager;

public class MobGenerator {
	private RPGSuit plugin;
	private FileConfiguration config;
	public static final String RPGMOB_KEY = "RPGMOB_KEY";
	public static final String RPGMOB_BOSS_KEY = "RPGMOB_BOSS";
	public static final int RPGMOB_INT_KEY = 23562342;
	private final String[] MobName = {
			"ZOMBIE",
			"PIG_ZOMBIE",
			"SPIDER",
			"CAVE_SPIDER",
			"BLAZE",
			"CREEPER",
			"ENDERMAN",
			"SKELETON",
			"WITCH",
			"WITHER_SKULL",
			"WITHER",
	};
	private HashMap<String, Aptitude> mobAptitude = new HashMap<String, Aptitude>();

	public MobGenerator(RPGSuit p) {
		plugin = p;
		config = RPGSuit.getConfigWithName("monster.yml");
		for (String name : MobName) {
			mobAptitude.put(name, new Aptitude(config, name));
		}
	}
	
	public void applyBoss(Entity entity, int level) {
		if (entity instanceof Monster) {
			Monster m = (Monster) entity;
			double hp = mobAptitude.get(entity.getType().name()).getHp(level) * (3 + level / 15.0);
			double str = mobAptitude.get(entity.getType().name()).getStr(level) * 1.05;
			double def = mobAptitude.get(entity.getType().name()).getDef(level) * 1.05;
			MobUtil.setLevel(m, level);
			MobUtil.setStr(m, (int) str);
			MobUtil.setDef(m, (int) def);
			MobUtil.setHp(m, (int) hp);
			MobUtil.setupSkill(m);
			m.setMetadata(RPGMOB_KEY, new FixedMetadataValue(plugin, RPGMOB_INT_KEY));
			m.setMetadata(RPGMOB_BOSS_KEY, new FixedMetadataValue(plugin, RPGMOB_INT_KEY));
			m.setCustomName(m.getType().name() + " BOSS LEVEL " + level);
			m.setCustomNameVisible(true);
			RPGEntityManager.AddEntity((LivingEntity) entity);
		}
	}
	
	public void apply(Entity entity, int level) {
		if (entity instanceof Monster) {
			Monster m = (Monster) entity;
			double hp = mobAptitude.get(entity.getType().name()).getHp(level);
			double str = mobAptitude.get(entity.getType().name()).getStr(level);
			double def = mobAptitude.get(entity.getType().name()).getDef(level);
			MobUtil.setLevel(m, level);
			MobUtil.setStr(m, (int) str);
			MobUtil.setDef(m, (int) def);
			MobUtil.setHp(m, (int) hp);
			MobUtil.setupSkill(m);
			m.setMetadata(RPGMOB_KEY, new FixedMetadataValue(plugin, RPGMOB_INT_KEY));
			m.setCustomName(m.getType().name() + " LEVEL " + level);
			m.setCustomNameVisible(true);
			RPGEntityManager.AddEntity((LivingEntity) entity);
		}
	}

	private class Aptitude {
		public int ax1, ax2, bx1, bx2;
		public double hp, str, def;
		public int gap;
		public Aptitude(FileConfiguration c, String name) {
			ax1 = c.getInt(name + ".aptitude.ax1", 8);
			c.set(name + ".aptitude.ax1", ax1);
			ax2 = c.getInt(name + ".aptitude.ax2", 9);
			c.set(name + ".aptitude.ax2", ax2);
			bx1 = c.getInt(name + ".aptitude.bx1", 16);
			c.set(name + ".aptitude.bx1", bx1);
			bx2 = c.getInt(name + ".aptitude.bx2", 17);
			c.set(name + ".aptitude.bx2", bx2);
			hp = c.getDouble(name + ".aptitude.hp", 1.0);
			c.set(name + ".aptitude.hp", hp);
			str = c.getDouble(name + ".aptitude.str", 1.0);
			c.set(name + ".aptitude.str", str);
			def = c.getDouble(name + ".aptitude.def", 1.0);
			c.set(name + ".aptitude.def", def);
			gap = c.getInt(name + ".aptitude.gap", 3);
			c.set(name + ".aptitude.gap", gap);
		}
		public double getHp(int level) {
			return (level + level / gap + ax1) * (level + level / gap + ax2) * hp;
		}
		public double getStr(int level) {
			return (level + level / gap + bx1) * (level + level / gap + bx2) * str * 0.05 + 20;
		}
		public double getDef(int level) {
			return (level + level / gap + bx1) * (level + level / gap + bx2) * def * 0.04;
		}
	}
}
