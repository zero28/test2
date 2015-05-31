package com.gmail.zhou1992228.rpgsuit.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.FileUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class QuestNormal extends Quest {
	
	public Map<String, Long> cancel_list = new HashMap<String, Long>();

	public String npcname;
	public int require_level, max_level;
	public int money_a, money_b;
	public int exp_a;
	public Map<String, String> req_list = new HashMap<String, String>();
	public Map<String, String> display_list = new HashMap<String, String>();
	public ArrayList<String> display, req;
	
	public QuestNormal(FileConfiguration config, String name) {
		super(name);		
		npcname = config.getString("quest." + name + ".npcname");
		require_level = config.getInt("quest." + name + ".require_level", 0);
		max_level = config.getInt("quest." + name + ".max_level", 30);
		money_a = config.getInt("quest." + name + ".money_a", 500);
		money_b = config.getInt("quest." + name + ".money_b", 10);
		exp_a = config.getInt("quest." + name + ".exp_a", 0);
		Set<String> keys = config.getConfigurationSection("quest." + name + ".reqs").getKeys(false);
		display = new ArrayList<String>();
		req = new ArrayList<String>();
		for (String key : keys) {
			String v = config.getString("quest." + name + ".reqs." + key);
			display.add(key);
			req.add(v);
		}
		Load();
	}

	@Override
	public void onCancel(Player p) {
		if (haveQuest(p, name)) {
			Quest.removeQuest(p, name);
			cancel_list.put(p.getName(), Util.Now());
			Bukkit.getLogger().info("" + cancel_list.get(p.getName()));
			p.sendMessage("你取消了你的任务");
		} else {
			p.sendMessage("你没有任务，取消什么");
		}
	}

	@Override
	public boolean tryComplete(String npcName, Player p) {
		return true;
	}

	@Override
	public void OnQuestComplete(Player p) {
		Quest.removeQuest(p, name);
	}

	@Override
	public void OnTakingQuest(Player p) {
		if (PlayerUtil.getLevel(p) < require_level) {
			p.sendMessage(npcname + ":接任务需要 " + require_level + " 级，先练练级再来吧");
			return;
		}
		if (PlayerUtil.getLevel(p) > max_level) {
			p.sendMessage(npcname + ":接任务需要 小于" + max_level + " 级，找点难点的任务吧");
			return;
		}
		if (Quest.haveQuest(p, name)) {
			if (req_list.containsKey(p.getName())) {
				p.sendMessage("还不快去将我要的东西找回来？" + display_list.get(p.getName()));
				return;
			}
		}
		if (cancel_list.containsKey(p.getName())) {
			Bukkit.getLogger().info("" + cancel_list.get(p.getName()) + " now " + Util.Now());
			if (cancel_list.get(p.getName()) + 10 * 60 * 1000 > Util.Now()) {
				p.sendMessage("现在没什么事情了，等一下再来吧");
				return;
			}
		} 
		Quest.addQuest(p, name);
		int index = random.nextInt(req.size());
		String require = req.get(index);
		String ori_display = display.get(index);
		String final_require = require;
		String final_display = ori_display;
		if (require.startsWith("t")) {
			if (require.contains(":LV")) {
				int lv = PlayerUtil.getLevel(p) + random.nextInt(7) - 3;
				final_require = require.replace(":LV", ":LV" + lv);
				final_display = ori_display.replace("%LV%", "LV" + lv);
			}
		}
		req_list.put(p.getName(), final_require);
		display_list.put(p.getName(), final_display);
		p.sendMessage("你去帮我找 " + final_display + " 回来吧");
		cancel_list.put(p.getName(), Util.Now());
	}

	@Override
	public boolean OnInteractWith(String npcName, Player p) {
		if (npcName.equals(npcname)) {
			if (req_list.containsKey(p.getName()) &&
				UserInfoUtils.haveRequires(p, req_list.get(p.getName()))) {
				UserInfoUtils.takeItems(p, req_list.get(p.getName()));
				OnQuestComplete(p);
				int plv = PlayerUtil.getLevel(p);
				int getexp = MobUtil.exp[plv] * exp_a;
				int money = plv * money_a + money_b;
				UserInfoUtils.add(p, "经验值", getexp);
				UserInfoUtils.giveItem(p, "$" + money);
				p.sendMessage(npcName + ":辛苦了，这是给你的奖励");
				p.sendMessage("你获得了 " + getexp + " 经验值" + " 和 $" + money);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription(Player p) {
		return "收集" + display_list.get(p.getName()) + "给" + npcname;
	}

	@Override
	public void Save() {
		FileUtil.saveObjectTo(req_list, RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_req_list");
		FileUtil.saveObjectTo(display_list, RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_display_list");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void Load() {
		req_list = (Map<String, String>) FileUtil.readObjectFrom(RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_req_list");
		display_list = (Map<String, String>) FileUtil.readObjectFrom(RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_display_list");
		if (req_list == null) { req_list = new HashMap<String, String>(); }
		if (display_list == null) { display_list = new HashMap<String, String>(); }
	}

}
