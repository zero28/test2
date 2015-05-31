package com.gmail.zhou1992228.rpgsuit.util;

import java.util.Calendar;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class Util {
	
	static public Random random = new Random();
	
	static public long Now() {
		return Calendar.getInstance().getTimeInMillis();
	}
	
	static public Location RandomLocationBetween(Location l1, Location l2) {
		if (l1.getWorld() != l2.getWorld()) { return null; }
		World w = l1.getWorld();
		int x = Math.min(l1.getBlockX(), l2.getBlockX());
		int y = Math.min(l1.getBlockY(), l2.getBlockY());
		int z = Math.min(l1.getBlockZ(), l2.getBlockZ());
		x += random.nextInt(Math.abs(l1.getBlockX() - l2.getBlockX()) + 1);
		y += random.nextInt(Math.abs(l1.getBlockY() - l2.getBlockY()) + 1);
		z += random.nextInt(Math.abs(l1.getBlockZ() - l2.getBlockZ()) + 1);		
		return new Location(w, x, y, z);
	}

	static public Location RandomLocationBetween(Location l1, Location l2, int range) {
		if (l1.getWorld() != l2.getWorld()) { return null; }
		World w = l1.getWorld();
		int x = Math.min(l1.getBlockX(), l2.getBlockX()) + range;
		int y = Math.min(l1.getBlockY(), l2.getBlockY());
		int z = Math.min(l1.getBlockZ(), l2.getBlockZ()) + range;
		//Bukkit.getLogger().info(x + " " + y + " " + z);
		x += random.nextInt(Math.max(1, Math.abs(l1.getBlockX() - l2.getBlockX()) + 1 - range * 2));
		y += random.nextInt(Math.max(1, Math.abs(l1.getBlockY() - l2.getBlockY())));
		z += random.nextInt(Math.max(1, Math.abs(l1.getBlockZ() - l2.getBlockZ()) + 1 - range * 2));
		//Bukkit.getLogger().info("l1: " + l1.toString() + " l2: " + l2.toString());
		//Bukkit.getLogger().info(x + " " + y + " " + z);
		return new Location(w, x, y, z);
	}
	
	static public boolean InMobWorld(Entity e) {
		return IsMobWorld(e.getWorld());
	}
	
	static public boolean InMobWorld(Player p) {
		return IsMobWorld(p.getWorld());
	}
	
	static public boolean IsMobWorld(World w) {
		return RPGSuit.mobEnableWorld.contains(w.getName());
	}
}
