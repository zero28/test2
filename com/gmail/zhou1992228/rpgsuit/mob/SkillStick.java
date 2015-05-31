package com.gmail.zhou1992228.rpgsuit.mob;

import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class SkillStick extends MonsterSkillBase implements Listener {
	public String key = "蛛丝";
	public Random random = new Random();
	public SkillStick() {
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
			int time = lv / 50 + 3;
			Player p = (Player)e.getEntity();
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
					50, time * 10));
			p.sendMessage("你被吐出来的蜘蛛丝黏住了！");
		}
	}
	@Override
	public boolean shouldHasSkill(Monster m) {
		if (m.getType() != EntityType.SPIDER) return false;
		int lv = MobUtil.getLevel(m);
		double p = 0.2 + (lv - 10) / 150.0 * 0.4;
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
