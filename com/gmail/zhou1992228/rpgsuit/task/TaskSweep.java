package com.gmail.zhou1992228.rpgsuit.task;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TaskSweep implements Runnable{
	
	public Player player;
	public int times;
	public int intv;
	public JavaPlugin plugin;
	public double ang;
	public TaskSweep(JavaPlugin plugin, Player pp, int int_v, int times, double angle) {
		this.plugin = plugin;
		this.player = pp;
		this.intv = int_v;
		this.times = times;
		this.ang = angle;
	}

	@Override
	public void run() {
		Location loc = player.getLocation();
		double a = loc.getYaw() + ang;
		if (a > 360) a -= 360;
		loc.setYaw((float) a);
		player.teleport(loc);
		if (times > 0) {
			--times;
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, intv);
		}
	}

}
