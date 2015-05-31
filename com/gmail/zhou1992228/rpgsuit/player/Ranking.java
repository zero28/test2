package com.gmail.zhou1992228.rpgsuit.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.util.FileUtil;

public class Ranking {
	public HashMap<String, Long> map = new HashMap<String, Long>();
	public Info[] res = new Info[100];
	int ressize;

	public void Save() {
		FileUtil.saveObjectTo(map, RPGSuit.ins.getDataFolder().getPath() + "/ranking.obj");
	}

	@SuppressWarnings("unchecked")
	public void Load() {
		map = (HashMap<String, Long>) FileUtil.readObjectFrom(RPGSuit.ins.getDataFolder().getPath() + "/ranking.obj");
		if (map == null) { map = new HashMap<String, Long>(); }
	}
	
	public void Update(String playerName, long long1) {
		if (map.containsKey(playerName)) {
			map.put(playerName, Math.max(long1, map.get(playerName)));
		} else {
			map.put(playerName, long1);
		}
	}
	public void Reset() {
		map.clear();
	}
	public void RefreshResult() {
		PriorityQueue<Info> queue = new PriorityQueue<Info>(100,  
                new Comparator<Info>() {  
                  public int compare(Info s1, Info s2) {  
                    long r = s2.v - s1.v;
                    if (r > 0) return 1;
                    if (r == 0) return 0;
                    return -1;
                  }
                });
		for (String key : map.keySet()) {
			queue.add(new Info(key, map.get(key)));
		}
		ressize = -1;
		for (int i = 0; i < 100 && !queue.isEmpty(); ++i) {
			res[i] = queue.poll();
			ressize = i;
		}
		++ressize;
	}

	public ArrayList<String> getPage(int i) {
		--i;
		ArrayList<String> ret = new ArrayList<String>();
		for (int x = 0; x < 10 && i * 10 + x < ressize; ++x) {
			ret.add(res[i*10+x].name + " : Õ½Á¦ " + res[i*10+x].v);
		}
		return ret;
	}
}

class Info {
	String name;
	long v;
	public Info(String n, Long long1) {
		name = n;
		v = long1;
	}
}