package com.gmail.zhou1992228.rpgsuit.task;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class TaskGetTarget implements Runnable {
	
	@Override
	public void run() {
		for (String worldName : RPGSuit.mobEnableWorld) {
			World w = RPGSuit.ins.getServer().getWorld(worldName);
			for (Monster m : w.getEntitiesByClass(Monster.class)) {
				if (m.getTarget() == null) {
					m.setTarget(getNearPlayer(m));
				}
				if (m.getTarget() == null) {
					m.remove();
				}
			}
		}
	}
	
	private LivingEntity getNearPlayer(Monster m) {
		for (Entity e : m.getNearbyEntities(45, 30, 45)) {
			if (e instanceof Player) {
				return (LivingEntity) e;
			}
		}
		return null;
	}

}
