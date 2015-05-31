package com.gmail.zhou1992228.rpgsuit.guild;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class GuildManager implements Runnable {
	public static GuildManager ins;
	private RPGSuit plugin;
	private FileConfiguration config;
	private Map<String, Guild> guilds = new HashMap<String, Guild>();
	public GuildManager(RPGSuit rpgSuit) {
		plugin = rpgSuit;
		Load();
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(rpgSuit, this, 1200, 1200);
	}
	
	public void Save() {
		for (Guild g : guilds.values()) {
			g.Save();
		}
		RPGSuit.SaveConfigToName(config, "guild.yml");
	}
	
	private void Load() {
		config = RPGSuit.getConfigWithName("guild.yml");
		for (String guildName : config.getConfigurationSection("guild").getKeys(false)) {
			guilds.put(guildName, new Guild(config.getConfigurationSection("guild." + guildName)));
		}
	}
	@Override
	public void run() {
		Save();
	}
}
