package com.gmail.zhou1992228.rpgsuit.listener;

import java.util.Calendar;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.arena.ArenaManager;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntityManager;
import com.gmail.zhou1992228.rpgsuit.util.VIPUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class PlayerJoinListener  implements Listener {
	
	public static String KEY_DEATH_PROTECT = "剩余死亡保护次数";
	
	public Random random = new Random();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		int day = (int) UserInfoUtils.getInt(e.getPlayer(), "更新日");
		Calendar c = Calendar.getInstance();
		int today = c.get(Calendar.DAY_OF_YEAR);
		if (day != today) {
			refreshDailyData(e.getPlayer());
			UserInfoUtils.set(e.getPlayer(), "更新日", today);
		}
		int toweek = (int) UserInfoUtils.getInt(e.getPlayer(), "更新周");
		int thisweek = c.get(Calendar.WEEK_OF_YEAR);
		if (toweek != thisweek) {
			refreshWeeklyData(e.getPlayer());
			UserInfoUtils.set(e.getPlayer(), "更新周", thisweek);
		}
		if (e.getPlayer().isOp()) {
			RPGEntityManager.AddEntity(e.getPlayer());
		}
	}
	
	private void refreshDailyData(Player p) {
		UserInfoUtils.set(p, "击杀怪物数", 0);
		UserInfoUtils.set(p, RPGSuit.DAILY_TASK_LEFT_COUNT, 15);
		UserInfoUtils.set(p, KEY_DEATH_PROTECT, PlayerUtil.getLevel(p) / 20 + 4);
		UserInfoUtils.set(p, ArenaManager.ARENA_COUNT, 3);
		VIPUtil.handleVipDaily(p);
	}
	private void refreshWeeklyData(Player p) {
		UserInfoUtils.set(p, RPGSuit.DOUBLE_EXP_LEFT_HOURE, 10);
		VIPUtil.handleVipWeekly(p);
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		if (e.getState() == State.CAUGHT_FISH ||
			e.getState() == State.CAUGHT_ENTITY || 
			e.getState() == State.FAILED_ATTEMPT) { 
			if (random.nextDouble() < 0.06) {
				Player p = e.getPlayer();
				p.sendMessage("你钓上来了一只怪物！");
				if (random.nextDouble() < 0.5) {
					Location loc = p.getLocation();
					int x = random.nextInt(4);
					loc = loc.add(x == 3 ? 1 : x == 0 ? -1 : 0, 0, x == 1 ? 1 : x == 2 ? -1 : 0);
					p.getWorld().spawnEntity(loc, EntityType.SKELETON);
				} else {
					p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
				}
			} else if (random.nextDouble() < 0.06) {
				Player p = e.getPlayer();
				p.sendMessage("你收杆的时候由于鱼线太高，被电了一下");
				p.getWorld().strikeLightning(p.getLocation());
			}
		}
	}
}
