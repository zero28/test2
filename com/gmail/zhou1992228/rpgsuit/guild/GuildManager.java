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
			p.sendMessage("�㻹û��������أ�����");
		}
	}
	
	public void Create(Player p, String name) {
		if (guilds.containsKey(name)) {
			p.sendMessage("�ð����Ѿ����ڣ�����ȡ����");
			return;
		}
		if (hasGuild(p)) {
			p.sendMessage("���Ѿ���������ˡ��������Ż������˳���");
			return;
		}
		if (UserInfoUtils.haveRequire(p, "$150000")) {
			config.set("guild." + name + ".name", name);
			Guild g = new Guild(config.getConfigurationSection("guild." + name));
			g.SetLeader(p);
			guilds.put(name, g);
			UserInfoUtils.takeItems(p, "$150000");
			p.sendMessage("�������ɳɹ���");
		} else {
			p.sendMessage("��û���㹻�Ľ�Ǯ/��Ʒ����������($150000)");
		}	
	}

	public void SetType(String playerName, Player by, MemberType tp) {
		if (GetGuild(by) != null) {
			GetGuild(by).SetType(playerName, by, tp);
		} else {
			by.sendMessage("�㻹û��������أ�����");
		}
	}

	@SuppressWarnings("deprecation")
	public boolean hasGuild(String playerName) {
		OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(playerName);
		String guildname = UserInfoUtils.getString(p, "����", "");
		return !guildname.isEmpty();
	}
	public boolean hasGuild(Player p) {
		String guildname = UserInfoUtils.getString(p, "����", "");
		return !guildname.isEmpty();
	}

	public Guild GetGuild(Player p) {
		return guilds.get(UserInfoUtils.getString(p, "����", ""));
	}

	public void Join(Player p, String guildName) {
		if (!guilds.containsKey(guildName)) {
			p.sendMessage("û���������");
			return;
		}
		if (hasGuild(p)) {
			p.sendMessage("���Ѿ���������ˡ����˳���");
			return;
		}
		guilds.get(guildName).Apply(p);
	}
	
	public void Leave(Player p, String guildName) {
		if (!guilds.containsKey(guildName)) {
			p.sendMessage("û���������");
			return;
		}
		if (!guilds.get(guildName).isLeader(p)) {
			guilds.get(guildName).removePlayer(p);
			p.sendMessage("���Ѿ��뿪�˰���");
		} else {
			p.sendMessage("��ʱ���ṩ�����뿪���ɹ���");
		}
	}
	
	public void SendGuildInfo(Player p, String guildName) {
		if (!guilds.containsKey(guildName)) {
			p.sendMessage("û���������");
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
			UserInfoUtils.set(p, "����", "");
		}
	}
}
