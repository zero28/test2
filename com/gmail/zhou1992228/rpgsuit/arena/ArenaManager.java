package com.gmail.zhou1992228.rpgsuit.arena;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class ArenaManager {
	public static ArenaManager ins;
	public static String ARENA_COUNT = "剩余竞技场次数";
	public Map<String, Arena> list = new HashMap<String, Arena>();
	public ArenaManager() {
		FileConfiguration config = RPGSuit.getConfigWithName("arena.yml");
		World w = RPGSuit.ins.getServer().getWorld(config.getString("spawnworld", "world"));
		int x = config.getInt("spawnx", 0);
		int y = config.getInt("spawny", 64);
		int z = config.getInt("spawnz", 0);
		Location spawn = new Location(w, x, y, z);
		for (String name : config.getConfigurationSection("arena").getKeys(false)) {
			World ww = RPGSuit.ins.getServer().getWorld(config.getString("arena." + name + ".world", "mobworld"));
			int x1 = config.getInt("arena." + name + ".l1x", 0);
			int y1 = config.getInt("arena." + name + ".l1y", 64);
			int z1 = config.getInt("arena." + name + ".l1z", 0);
			int x2 = config.getInt("arena." + name + ".l2x", 0);
			int y2 = config.getInt("arena." + name + ".l2y", 64);
			int z2 = config.getInt("arena." + name + ".l2z", 0);
			Bukkit.getLogger().info(
					String.format("Load arena at: world: " + ww.getName() + " x1: %d y1: %d z1 :%d  x2: %d y2: %d z2 :%d",
							x1, y1, z1, x2, y2, z2));
			list.put(name, new Arena(
					name, new Location(ww, x1, y1, z1), new Location(ww, x2, y2, z2), spawn));
		}
	}
	
	public boolean inArena(Location l) {
		for (Arena a : list.values()) {
			if (PlayerUtil.InAreaWithMoreRange(l, a.l1, a.l2, 5)) {
				return true;
			}
		}
		return false;
	}
	
	public void tryJoin(Player p, int lv) {
		if (!UserInfoUtils.haveRequire(p, "s:" + ARENA_COUNT + ":1")) {
			p.sendMessage("你今天参加竞技场的次数已用完");
			return;
		}
		for (Arena a : list.values()) {
			if (a.okToJoin()) {
				p.sendMessage("你加入了？？？");
				UserInfoUtils.takeItem(p, "s:" + ARENA_COUNT + ":1");
				if (lv == -1) {
					a.join(p);
				} else {
					a.joinAtLevel(p, lv);
				}
				return;
			}
		}
		p.sendMessage("人满了，请稍等");
	}
	
	public boolean playerInArena(Player p) {
		for (Arena a : list.values()) {
			if (a.playerInArena(p)) {
				return true;
			}
		}
		return false;
	}
}
