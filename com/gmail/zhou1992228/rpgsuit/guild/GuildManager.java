package com.gmail.zhou1992228.rpgsuit.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.guild.Guild.MemberType;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class GuildManager implements Runnable {
	public static GuildManager ins;
	private RPGSuit plugin;
	private FileConfiguration config;
	private Map<String, Guild> guilds = new HashMap<String, Guild>();
	private boolean first = true;
	public GuildManager(RPGSuit rpgSuit) {
		plugin = rpgSuit;
		Load();
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(rpgSuit, this, 1, 1200);
	}
	
	public void Save() {
		for (Guild g : guilds.values()) {
			g.Save();
		}
		RPGSuit.SaveConfigToName(config, "guild.yml");
	}
	
	public void Upgrade(Player p, String skillName) {
		if (hasGuild(p)) {
			this.GetGuild(p).TryUpgrade(skillName, p);
		} else {
			p.sendMessage("你还没加入帮派呢，别闹");
		}
	}
	
	public void Create(Player p, String name) {
		if (guilds.containsKey(name)) {
			p.sendMessage("该帮派已经存在，请另取名字");
			return;
		}
		if (hasGuild(p)) {
			p.sendMessage("你已经加入帮派了。想自立门户？先退出吧");
			return;
		}
		if (UserInfoUtils.haveRequire(p, "$150000")) {
			config.set("guild." + name + ".name", name);
			Guild g = new Guild(config.getConfigurationSection("guild." + name));
			g.SetLeader(p);
			guilds.put(name, g);
			UserInfoUtils.takeItems(p, "$150000");
			p.sendMessage("创建帮派成功！");
		} else {
			p.sendMessage("你没有足够的金钱/物品来创建帮派($150000)");
		}	
	}

	public void SetType(String playerName, Player by, MemberType tp) {
		if (GetGuild(by) != null) {
			GetGuild(by).SetType(playerName, by, tp);
		} else {
			by.sendMessage("你还没加入帮派呢，别闹");
		}
	}

	@SuppressWarnings("deprecation")
	public boolean hasGuild(String playerName) {
		OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(playerName);
		String guildname = UserInfoUtils.getString(p, "公会", "");
		return !guildname.isEmpty();
	}
	public boolean hasGuild(Player p) {
		String guildname = UserInfoUtils.getString(p, "公会", "");
		return !guildname.isEmpty();
	}

	public Guild GetGuild(Player p) {
		return guilds.get(UserInfoUtils.getString(p, "公会", ""));
	}

	public void Join(Player p, String guildName) {
		if (!guilds.containsKey(guildName)) {
			p.sendMessage("没有这个帮派");
			return;
		}
		if (hasGuild(p)) {
			p.sendMessage("你已经加入帮派了。先退出吧");
			return;
		}
		guilds.get(guildName).Apply(p);
	}
	
	public void Leave(Player p, String guildName) {
		if (!guilds.containsKey(guildName)) {
			p.sendMessage("没有这个帮派");
			return;
		}
		if (!guilds.get(guildName).isLeader(p)) {
			guilds.get(guildName).removePlayer(p);
			p.sendMessage("你已经离开了帮派");
		} else {
			p.sendMessage("暂时不提供帮主离开帮派功能");
		}
	}
	
	public void SendGuildInfo(Player p, String guildName) {
		if (!guilds.containsKey(guildName)) {
			p.sendMessage("没有这个帮派");
			return;
		}
		p.sendMessage(guilds.get(guildName).GetInfo());
	}
	
	public String[] GetGuildShortInfo(int page) {
		int skip = (page - 1) * 15;
		int count = 15;
		ArrayList<String> infos = new ArrayList<String>();
		for (Guild g : guilds.values()) {
			if (skip > 0) {
				--skip;
				continue;
			}
			if (count == 0) {
				break;
			}
			infos.add(g.GetShortInfo());
		}
		return (String[]) infos.toArray(new String[infos.size()]);
	}
	
	private void Load() {
		config = RPGSuit.getConfigWithName("guild.yml");
		for (String guildName : config.getConfigurationSection("guild").getKeys(false)) {
			guilds.put(guildName, new Guild(config.getConfigurationSection("guild." + guildName)));
		}
	}
	@Override
	public void run() {
		Update();
		for (Guild g: guilds.values()) {
			if (first) {
				g.withdrawMaintainCost();
			}
			g.update();
		}
		first = false;
		Save();
	}
	
	@SuppressWarnings("deprecation")
	private void Update() {
		for (GuildSkill gs : GuildSkill.list.values()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				gs.resetPlayer(p);
			}
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			UserInfoUtils.set(p, "公会", "");
		}
	}
}
