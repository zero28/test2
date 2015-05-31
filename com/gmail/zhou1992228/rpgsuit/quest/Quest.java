package com.gmail.zhou1992228.rpgsuit.quest;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public abstract class Quest {
	public static Random random = new Random();
	public static HashMap<String, Quest> list = new HashMap<String, Quest>();
	public static HashMap<String, List<String>> npcQuest = new HashMap<String, List<String>>();
	public static void SaveAll() {
		for (Quest q : list.values()) {
			q.Save();
		}
	}
	public static void tryRandomRequest(Player p, String npc) {
		List<String> questList = npcQuest.get(npc);
		if (questList == null) return;
		String questName = questList.get(random.nextInt(questList.size()));
		Quest q = list.get(questName);
		if (q == null) return;
		q.OnTakingQuest(p);
	}

	public static boolean haveQuest(Player p, String questName) {
		String[] list = UserInfoUtils.getString(p, "任务列表", "").split(",");
		for (String n : list) {
			if (n.equals(questName)) {
				return true;
			}
		}
		return false;
	}

	public static void addQuest(Player p, String questName) {
		String s = UserInfoUtils.getString(p, "任务列表", "");
		if (s.isEmpty()) {
			s = questName;
		} else {
			s += "," + questName;
		}
		UserInfoUtils.set(p, "任务列表", s);
	}
	public static void removeQuest(Player p, String questName) {
		String[] list = UserInfoUtils.getString(p, "任务列表", "").split(",");
		String result = "";
		for (String name : list) {
			if (name.equals(questName)) continue;
			if (result.isEmpty()) {
				result = name;
			} else {
				result += "," + name;
			}
		}
		UserInfoUtils.set(p, "任务列表", result);
	}
	
	public static void InitNpcQuest() {
		FileConfiguration config = RPGSuit.getConfigWithName("npcquest.yml");
		for (String npcName : config.getConfigurationSection("npc").getKeys(false)) {
			npcQuest.put(npcName, config.getStringList("npc." + npcName + ".questlist"));
			Bukkit.getLogger().info("adding npc " + npcName + " with list: " + config.getStringList("npc." + npcName + ".questlist"));
		}
		for (String questName : config.getConfigurationSection("quest").getKeys(false)) {
			if (config.getString("quest." + questName + ".type", "").equalsIgnoreCase("sendletter")) {
				list.put(questName, new QuestSendLetter(config, questName));
				Bukkit.getLogger().info("load sendletter" + questName);
			}
			if (config.getString("quest." + questName + ".type", "").equalsIgnoreCase("treasuremap")) {
				list.put(questName, new QuestMobHunter(config, questName));
				Bukkit.getLogger().info("load treasuremap" + questName);
			}
			if (config.getString("quest." + questName + ".type", "").equalsIgnoreCase("daily")) {
				list.put(questName, new QuestDailyTask(config, questName));
				Bukkit.getLogger().info("load daily" + questName);
			}
			if (config.getString("quest." + questName + ".type", "").equalsIgnoreCase("normal")) {
				list.put(questName, new QuestNormal(config, questName));
				Bukkit.getLogger().info("load normal" + questName);
			}
		}
	}
	public String name;
	abstract public void onCancel(Player p);
	abstract public boolean tryComplete(String npcName, Player p);
	abstract public void OnQuestComplete(Player p);
	abstract public void OnTakingQuest(Player p);
	abstract public boolean OnInteractWith(String npcName, Player p);
	abstract public String getDescription(Player p);
	abstract public void Save();
	abstract public void Load();
	public Quest(String name) {
		this.name = name;
		list.put(name, this);
	}
}
