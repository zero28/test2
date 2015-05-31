package com.gmail.zhou1992228.rpgsuit.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class StatusUtil {
	public enum Status {
		CAN_SHOT_ARROW,  // If a player's arrow has rpg damage.
		STR_UP1,
		DEF_UP1,
		HP_UP1,
		STR_UP2,
		DEF_UP2,
		HP_UP2,
	};
	public static Map<String, HashMap<Status, Long>> info = new HashMap<String, HashMap<Status, Long>>();
	public static void AddStatus(Player p, Status s, long time_s) {
		long new_time = time_s * 1000 + Util.Now();
		if (info.containsKey(p.getName())) {
			info.get(p.getName()).put(s, new_time);
			return;
		}
		HashMap<Status, Long> list = new HashMap<Status, Long>();
		list.put(s, new_time);
		info.put(p.getName(), list);
	}
	public static boolean HasStatus(Player p, Status s) {
		long now = Util.Now();
		if (info.containsKey(p.getName())) {
			if (info.get(p.getName()).containsKey(s)) {
				if (info.get(p.getName()).get(s) > now) {
					return true;
				}
			}
		}
		return false;
	}
}
