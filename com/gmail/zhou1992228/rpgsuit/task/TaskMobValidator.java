package com.gmail.zhou1992228.rpgsuit.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;

public class TaskMobValidator implements Runnable {
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for (String worldName : RPGSuit.mobEnableWorld) {
			World w = Bukkit.getServer().getWorld(worldName);
			if (w != null) {
				for (Entity e : w.getEntities()) {
					if (e instanceof Monster) {
						if (MobUtil.getDef((Monster) e) == -1 || e.getLocation().getBlockY() < 40) {
							e.remove();
						}
					}
				}
			}
		}
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getWorld().getEnvironment() == Environment.NETHER) {
				if (p.getLocation().getY() >= 128.0) {
					p.damage(1);
				}
			}
		}
	}
}
