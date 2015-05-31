package com.gmail.zhou1992228.rpgsuit.mob;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class SkillShotShock extends MonsterSkillBase implements Listener {
	public String key = "击晕";
	public Random random = new Random();
	public SkillShotShock() {
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onShot(EntityDamageByEntityEvent e) {
		if (!Util.InMobWorld(e.getEntity())) { return; }
		if (e.getDamager() instanceof Projectile &&
			e.getEntity() instanceof Player) {
			ProjectileSource ps = ((Projectile) e.getDamager()).getShooter();
			int lv = -1;
			if (ps instanceof Monster && MobUtil.hasSkill((Monster) ps, key)) {
				lv = MobUtil.getLevel((Monster) ps);
			}
			if (lv == -1) return;
			if (lv < random.nextInt(155)) {
				return;
			}
			Player p = (Player)e.getEntity();
			Location loc = p.getLocation();
			loc.setPitch(random.nextFloat() * 180 - 90);
			loc.setYaw(random.nextFloat() * 360 - 180);
			p.teleport(loc);
			p.sendMessage("你被一支箭射的晕头转向");
		}
	}
	@Override
	public boolean shouldHasSkill(Monster m) {
		if (m.getType() != EntityType.SKELETON) return false;
		int lv = MobUtil.getLevel(m);
		double p = 0.1 + (lv - 15) / 150.0 * 0.3;
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
