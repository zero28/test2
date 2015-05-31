package com.gmail.zhou1992228.rpgsuit.listener;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class ListenerForTest implements Listener {
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		if (e.getEntity().getType() == EntityType.CREEPER) {
			if (Util.InMobWorld(e.getEntity())) {
				double health = ((Damageable)e.getEntity()).getHealth();
				int lv = MobUtil.getLevel((Monster) e.getEntity());
				Monster c = (Monster) e.getEntity().getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
				RPGSuit.mobGenerator.apply(c, lv);
				c.setHealth(health);
			}
		}
	}
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMaxPlayers(123456789);
		e.setMotd("≤‚ ‘");
	}
}
