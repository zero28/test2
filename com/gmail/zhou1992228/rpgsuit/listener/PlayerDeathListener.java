package com.gmail.zhou1992228.rpgsuit.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.arena.ArenaManager;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.task.TaskMinuteUpdater;
import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class PlayerDeathListener implements Listener {

	public double expDrop;
	public double expGain;
	public int dropExpLevelDiff;
	public PlayerDeathListener(RPGSuit plugin) {
		expDrop = plugin.getConfig().getDouble("expDrop", 0.015);
		expGain = plugin.getConfig().getDouble("expGain", 0.7);
		dropExpLevelDiff = plugin.getConfig().getInt("dropExpLevelDiff", 10);
		plugin.getConfig().set("expDrop", expDrop);
		plugin.getConfig().set("expGain", expGain);
		plugin.getConfig().set("dropExpLevelDiff", dropExpLevelDiff);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (e.getPlayer().isOp()) return;
		if (!Util.InMobWorld(e.getPlayer())) return;
		if (ArenaManager.ins.inArena(e.getPlayer().getLocation()) &&
			!ArenaManager.ins.playerInArena(e.getPlayer())) {
			return;
		}
		if (e.getFrom().getWorld().equals(e.getTo().getWorld()) &&
			e.getFrom().distanceSquared(e.getTo()) < 1000) {
			return;
		}
		for (Entity ee : e.getPlayer().getNearbyEntities(10, 10, 10)) {
			if (ee instanceof Monster) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("你的身边有怪物，不能传送！");
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (ArenaManager.ins.playerInArena(e.getEntity())) {
			return;
		}
		if (UserInfoUtils.getInt(e.getEntity(), PlayerJoinListener.KEY_DEATH_PROTECT) > 0) {
			UserInfoUtils.minus(e.getEntity(), PlayerJoinListener.KEY_DEATH_PROTECT, 1);
			return;
		}
		Player killer = null;
		if (e.getEntity().getKiller() instanceof Player) {
			killer = e.getEntity().getKiller();
		}
		long oldExp = UserInfoUtils.getInt(e.getEntity(), "经验值");
		long newExp = (long) (oldExp * (1 - expDrop));
		UserInfoUtils.set(e.getEntity(), "经验值", newExp);
		UserInfoUtils.set(e.getEntity(), TaskMinuteUpdater.KEY_STRENGTH,
				(int)(UserInfoUtils.getInt(e.getEntity(), TaskMinuteUpdater.KEY_STRENGTH) * 0.5));
		if (killer != null) {
			int deathLevel = PlayerUtil.getLevel(e.getEntity());
			int killerLevel = PlayerUtil.getLevel(killer);
			if (killerLevel - dropExpLevelDiff > deathLevel) {
				return;
			}
			UserInfoUtils.add(killer, "经验值", (long)(oldExp * expDrop * expGain));
		}
	}
}
