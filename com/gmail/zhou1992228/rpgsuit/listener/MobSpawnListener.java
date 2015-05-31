package com.gmail.zhou1992228.rpgsuit.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.arena.ArenaManager;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class MobSpawnListener implements Listener {
	private Random random = new Random();
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		if (e.getEntity() instanceof Monster) {
			if (e.getSpawnReason() == SpawnReason.SPAWNER) {
				e.setCancelled(true);
				return;
			}
			if (Util.InMobWorld(e.getEntity())) {
				if (ArenaManager.ins.inArena(e.getLocation())) return;
				int lv = 150;
				int ct = 0;
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getWorld().equals(e.getEntity().getWorld())) {
						if (Math.abs(p.getLocation().getBlockX() - e.getEntity().getLocation().getBlockX()) < 60 &&
							Math.abs(p.getLocation().getBlockZ() - e.getEntity().getLocation().getBlockZ()) < 60) {
							++ct;
							lv = Math.min(PlayerUtil.getLevel(p), lv);
						}
					}
				}
				if (ct == 0) {
					e.setCancelled(true);
					return;
				}
				lv = (int) (lv + random.nextGaussian() * 3);
				if (lv > 150) lv = 150;
				if (lv <= 0) lv = 1;
				if (random.nextInt(25) == 0) {
					RPGSuit.mobGenerator.applyBoss(e.getEntity(), lv);
				} else {
					RPGSuit.mobGenerator.apply(e.getEntity(), lv);
				}
			}
		}
	}
}
