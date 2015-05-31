package com.gmail.zhou1992228.rpgsuit.quest;

import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class QuestThread extends Quest {
	
	public String npcname;
	public int require_level, max_level;
	public Set<String> stages;
	
	public QuestThread(FileConfiguration config, String name) {
		super(name);
		npcname = config.getString("quest." + name + ".npcname");
		require_level = config.getInt("quest." + name + ".require_level", 0);
		max_level = config.getInt("quest." + name + ".max_level", 30);
		//Set<String> stages = config.getConfigurationSection("quest." + name + ".stages").getKeys(false);
		Load();
	}

	@Override
	public void onCancel(Player p) { }

	@Override
	public boolean tryComplete(String npcName, Player p) {
		return true;
	}

	@Override
	public void OnQuestComplete(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnTakingQuest(Player p) {
	}

	@Override
	public boolean OnInteractWith(String npcName, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Load() {
		// TODO Auto-generated method stub
		
	}

}
