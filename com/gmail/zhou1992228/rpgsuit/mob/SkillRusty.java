package com.gmail.zhou1992228.rpgsuit.mob;

import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class SkillRusty extends MonsterSkillBase implements Listener {
	public String key = "腐蚀";
	public Random random = new Random();
	public SkillRusty() {
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
	}
	@EventHandler
	public void onShot(EntityDamageByEntityEvent e) {
		if (!Util.InMobWorld(e.getEntity())) { return; }
		if (e.getEntity() instanceof Player) {
			Entity ps = e.getDamager();
			int lv = -1;
			if (ps instanceof Monster && MobUtil.hasSkill((Monster) ps, key)) {
				lv = MobUtil.getLevel((Monster) ps);
			}
			if (lv == -1) return;
			int damage = 1 + lv / 15;
			Player p = (Player)e.getEntity();
			p.setItemInHand(ItemUtil.RPGDamage(p, p.getItemInHand(), damage));
			p.getInventory().setHelmet(
				ItemUtil.RPGDamage(p, p.getInventory().getHelmet(), damage));
			p.getInventory().setChestplate(
				ItemUtil.RPGDamage(p, p.getInventory().getChestplate(), damage));
			p.getInventory().setLeggings(
				ItemUtil.RPGDamage(p, p.getInventory().getLeggings(), damage));
			p.getInventory().setBoots(
				ItemUtil.RPGDamage(p, p.getInventory().getBoots(), damage));
			p.sendMessage("你的装备被腐蚀了！耐久度减少");
		}
	}
	@Override
	public boolean shouldHasSkill(Monster m) {
		if (m.getType() != EntityType.ZOMBIE) return false;
		int lv = MobUtil.getLevel(m);
		double p = 0.2 + (lv - 20) / 150.0 * 0.5;
		if (random.nextDouble() < p) {
			return true;
		}
		return false;
	}
	@Override
	public String getKey() {
		return key;
	}
}
