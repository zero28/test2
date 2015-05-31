package com.gmail.zhou1992228.mobarena;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.events.ArenaCompleteEvent;
import com.garbagemule.MobArena.events.ArenaEndEvent;
import com.garbagemule.MobArena.events.ArenaKillEvent;
import com.garbagemule.MobArena.events.ArenaPlayerDeathEvent;
import com.garbagemule.MobArena.events.ArenaPlayerJoinEvent;
import com.garbagemule.MobArena.events.ArenaPlayerLeaveEvent;
import com.garbagemule.MobArena.events.ArenaStartEvent;
import com.garbagemule.MobArena.events.NewWaveEvent;
import com.garbagemule.MobArena.framework.Arena;
import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;

public class MobArenaIntegrator implements Listener {
	FileConfiguration config;
	static public MobArena mobArena;
	public MobArenaIntegrator() {
		config = RPGSuit.getConfigWithName("mobarena.yml");
		mobArena = (MobArena) Bukkit.getPluginManager().getPlugin("MobArena");
	}
	@EventHandler
	public void onPlayerDeathArena(ArenaPlayerDeathEvent e) {
		Bukkit.getLogger().info("player death");
		try {
			e.getArena().getInventoryManager().storeInv(e.getPlayer());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public int getArenaMinLevel(Arena a) {
		return config.getInt("arena." +  a.configName() + ".minlv", 0);
	}
	public int getArenaMaxLevel(Arena a) {
		return config.getInt("arena." +  a.configName() + ".maxlv", 150);
	}
	
	@EventHandler
	public void onPlayerJoinArena(ArenaPlayerJoinEvent e) {
		Bukkit.getLogger().info("player join");
		int plv = PlayerUtil.getLevel(e.getPlayer());
		int maxlv = this.getArenaMaxLevel(e.getArena());
		int minlv = this.getArenaMinLevel(e.getArena());
		//Bukkit.getLogger().info("player join, plv: " + plv + " maxlv: " + maxlv + " minlv:" + minlv + " arenaname:" + e.getArena().arenaName() + " configname:" + e.getArena().configName());
		if (minlv <= plv && plv <= maxlv) {
			return;
		} else {
			e.getPlayer().sendMessage("你的等级不符合此竞技场要求！" + minlv + "-" + maxlv + "级");
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onArenaEnd(ArenaEndEvent e) {
		Bukkit.getLogger().info("arena end");
	}
	@EventHandler
	public void onArenaKill(ArenaKillEvent e) {
		Bukkit.getLogger().info("arena kill");
	}
	@EventHandler
	public void onArenaStart(ArenaStartEvent e) {
		Bukkit.getLogger().info("arena start");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
				RPGSuit.ins, new TaskRestoreInv(e.getArena()), 3);
	}
	@EventHandler
	public void onPlayerLeaveArena(ArenaPlayerLeaveEvent e) {
		Bukkit.getLogger().info("player leave");
		try {
			e.getArena().getInventoryManager().storeInv(e.getPlayer());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
				RPGSuit.ins, new TaskUpdateInv(e.getPlayer()), 3);
	}
	@EventHandler
	public void onArenaComplete(ArenaCompleteEvent e) {
		Bukkit.getLogger().info("arena comp");
	}
	
	@EventHandler
	public void onNewWave(NewWaveEvent e) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
				RPGSuit.ins,
				new TaskApplyLevelAndBoss(
						e.getArena(),
						this.getArenaMinLevel(e.getArena()),
						this.getArenaMaxLevel(e.getArena())),
				3);
	}
}
