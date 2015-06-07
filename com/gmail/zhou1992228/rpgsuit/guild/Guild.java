package com.gmail.zhou1992228.rpgsuit.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class Guild {
	
	public enum MemberType {
		LEADER,
		MANAGER,
		MEMBER_V2,
		MEMBER,
	}
	
	private String name;
	private ConfigurationSection config;
	private Map<MemberType, List<String>> members = new HashMap<MemberType, List<String>>();
	private List<String> apply_member;
	private Map<String, Integer> skill_list = new HashMap<String, Integer>();
	private int money;
	private int build;
	
	public Guild(ConfigurationSection c) {
		config = c;
		name = config.getString("name", "UNKNOWN");
		config.set("name", name);
		members.put(MemberType.LEADER, config.getStringList("leader"));
		members.put(MemberType.MANAGER, config.getStringList("manager"));
		members.put(MemberType.MEMBER_V2, config.getStringList("memberv2"));
		members.put(MemberType.MEMBER, config.getStringList("member"));
		apply_member = config.getStringList("apply_member");
		for (GuildSkill gs : GuildSkill.list.values()) {
			skill_list.put(gs.name(), config.getInt(gs.name(), 0));
		}
		money = config.getInt("money", 150000);
		build = config.getInt("build", 0);
	}
	
	public void Save() {
		config.set("leader", members.get(MemberType.LEADER));
		config.set("manager", members.get(MemberType.MANAGER));
		config.set("memberv2", members.get(MemberType.MEMBER_V2));
		config.set("member", members.get(MemberType.MEMBER));
		config.set("apply_member", apply_member);
		for (GuildSkill gs : GuildSkill.list.values()) {
			config.set(gs.name(), skill_list.get(gs.name()));
		}
		config.set("skill_list", skill_list);
		config.set("money", money);
		config.set("build", build);
	}
	
	public void withdrawMaintainCost() {
		
	}
	
	public void SetLeader(Player p) {
		members.get(MemberType.LEADER).add(p.getName());
	}
	
	public void update() {
		for (List<String> l : members.values()) {
			for (String playerName : l) {
				Player p = Bukkit.getServer().getPlayer(playerName);
				if (p != null) {
					UserInfoUtils.set(p, "����", name);
				}
			}
		}
		applyGuildSkills();
	}
	public void applyGuildSkills() {
		for (GuildSkill gs : GuildSkill.list.values()) {
			for (Player p : getOnlinePlayer()) {
				gs.applyToPlayer(p, this);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Player> getOnlinePlayer() {
		ArrayList<Player> result = new ArrayList<Player>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (isMember(p)) {
				result.add(p);
			}
		}
		return result;
	}
	public String applyList() {
		return StringUtils.join(apply_member, ",");
	}
	public String GetName() {
		return name;
	}
	public int getMemberCount() {
		int sum = 0;
		for (List<String> l : members.values()) {
			sum += l.size();
		}
		return sum;
	}
	public String GetShortInfo() {
		return "��������: " + name + " ��������:" + getMemberCount() + " ����: " + members.get(MemberType.LEADER).get(0);
	}
	
	public String[] GetInfo() {
		ArrayList<String> info = new ArrayList<String>();
		info.add("����: " +  members.get(MemberType.LEADER).get(0));
		info.add("��Ǯ: " + money);
		info.add("�����: " + build);
		info.add("���ɽ���:");
		for (String s : skill_list.keySet()) {
			info.add(s + ": " + skill_list.get(s) + " �� " + GuildSkill.list.get(s).getDescription());
		}
		info.add("���ɳ�Ա: ");
		info.add("����: " + StringUtils.join(members.get(MemberType.MANAGER), ","));
		info.add("��Ӣ: " + StringUtils.join(members.get(MemberType.MEMBER_V2), ","));
		info.add("����: " + StringUtils.join(members.get(MemberType.MEMBER), ","));
		return info.toArray(new String[info.size()]);
	}
	
	public void Apply(Player p) {
		if (apply_member.contains(p.getName().toLowerCase())) {
			p.sendMessage("���Ѿ����������������ˣ���Ⱥ���Ϣ��");
		} else {
			apply_member.add(p.getName().toLowerCase());
			p.sendMessage("������������������");
		}
	}
	
	public void DenyBy(String playerName, Player by) {
		if (!isManager(by)) {
			by.sendMessage("��û��Ȩ�޾ܾ����˵�����");
			return;
		}
		if (!apply_member.contains(playerName.toLowerCase())) {
			by.sendMessage("�����û�����������İ���");
			return;
		}
		apply_member.remove(playerName.toLowerCase());
		by.sendMessage("��ܾ��� " + playerName + " �ļ�������");
		Util.SendMessageIfOnline(playerName, 
			by.getName() + " �ܾ�������� " + name + "������");
	}
	
	public void TryUpgrade(String skillName, Player by) {
		if (this.isLeader(by)) {
			if (!GuildSkill.list.containsKey(skillName)) {
				by.sendMessage("û�������ʩ");
				return;
			}
			int lv = this.getSkillLevel(skillName);
			if (this.hasCost(GuildSkill.list.get(skillName).upgradeCost(lv + 1))) {
				this.takeCost(GuildSkill.list.get(skillName).upgradeCost(lv + 1));
			} else {
				by.sendMessage("û���㹻����Ʒ/��Ǯ/���Ͻ�������");
				by.sendMessage("��Ҫ " + GuildSkill.list.get(skillName).upgradeCost(lv + 1));
			}
		} else {
			by.sendMessage("�㲻�ǰ�����");
		}
	}
	
	public void takeCost(String cost) {
		for (String c : cost.split(" ")) {
			String name = c.split(":")[0];
			int count = Integer.parseInt(c.split(":")[1]);
			if (name.equals("��Ǯ")) {
				money -= count;
			} else if (name.equals("�����")) {
				build -= count;
			}
		}
	}

	public boolean hasCost(String cost) {
		for (String c : cost.split(" ")) {
			String name = c.split(":")[0];
			int count = Integer.parseInt(c.split(":")[1]);
			if (name.equals("��Ǯ")) {
				if (count > money) {
					return false;
				}
			} else if (name.equals("�����")) {
				if (count > build) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	public void KickBy(String p, Player by) {
		if (!isManager(by)) {
			by.sendMessage("��û��Ȩ�޽�����߳�����");
			return;
		}
		if (isLeader(p)) {
			by.sendMessage("�㲻�ܽ������߳�����");
			return;
		}
		if (!isLeader(by) && !isLeader(p) && isManager(p) && isManager(by)) {
			by.sendMessage("�㲻�ܽ���������Ա�߳�����");
			return;
		}
		if (!isMember(p)) {
			by.sendMessage("��İ���û��������");
			return;
		}
		removePlayer(p);
		by.sendMessage("���Ѿ��� " + p + " �߳��˰���");
	}
	
	public void removePlayer(Player p) {
		removePlayer(p.getName());
	}
	
	public void removePlayer(String playerName) {
		members.get(MemberType.MEMBER).remove(playerName);
		members.get(MemberType.MEMBER_V2).remove(playerName);
		members.get(MemberType.MANAGER).remove(playerName);
		members.get(MemberType.LEADER).remove(playerName);
	}
	
	public void AcceptBy(String playerName, Player by) {
		if (this.isManager(by)) {
			if (apply_member.contains(playerName)) {
				if (GuildManager.ins.hasGuild(playerName)) {
					by.sendMessage("������Ѿ�������һ��������");
					apply_member.remove(playerName);
				} else {
					apply_member.remove(playerName);
					members.get(MemberType.MEMBER).add(playerName);
					by.sendMessage("���Ѿ�ͬ���� " + playerName + " ���������");
					Util.SendMessageIfOnline(playerName, by.getName() + "�Ѿ�ͬ��������������");
				}
			} else {
				by.sendMessage("�����û�����������İ���");
			}
		} else {
			by.sendMessage("��û��Ȩ�޽��д˲���");
		}
	}
	
	public MemberType GetType(String playerName) {
		if (members.get(MemberType.MEMBER).contains(playerName)) return MemberType.MEMBER;
		if (members.get(MemberType.MEMBER_V2).contains(playerName)) return MemberType.MEMBER_V2;
		if (members.get(MemberType.MANAGER).contains(playerName)) return MemberType.MANAGER;
		if (members.get(MemberType.LEADER).contains(playerName)) return MemberType.LEADER;
		return null;
	}
	
	public void SetType(String playerName, Player by, MemberType type) {
		if (!this.isMember(playerName)) {
			by.sendMessage("����Ҳ�������İ���");
			return;
		}
		MemberType command_type = GetType(by.getName());
		switch (command_type) {
			case MEMBER:
			case MEMBER_V2:
				by.sendMessage("��û��Ȩ�޽��д˲���");
				return;
			case MANAGER:
				if (type == MemberType.LEADER || type == MemberType.MANAGER) {
					by.sendMessage("��û��Ȩ�޽��д˲���");
					return;
				}
			case LEADER:
				if (type == MemberType.LEADER) {
					by.sendMessage("��û��Ȩ�޽��д˲���");
					return;
				}
				if (by.getName().equalsIgnoreCase(playerName)) {
					by.sendMessage("���ڿ���Ц�ɣ�");
					return;
				}
				break;
		}
		this.removePlayer(playerName);
		members.get(type).add(playerName);
	}
	
	public boolean isLeader(Player p) {
		return isLeader(p.getName());
	}
	public boolean isLeader(String playerName) {
		return members.get(MemberType.LEADER).contains(playerName.toLowerCase());
	}
	public boolean isManager(Player p) {
		return isManager(p.getName());
	}
	public boolean isManager(String playerName) {
		return members.get(MemberType.MANAGER).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.LEADER).contains(playerName.toLowerCase());
	}
	public boolean isMember(Player p) {
		return isMember(p.getName());
	}
	public boolean isMember(String playerName) {
		return members.get(MemberType.MEMBER).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.MEMBER_V2).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.MANAGER).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.LEADER).contains(playerName.toLowerCase());
	}
	
	public void upgradeSkill(String skillName) {
		skill_list.put(skillName, skill_list.get(skillName) + 1);
	}
	public int getSkillLevel(String skillName) {
		return skill_list.get(skillName);
	}
}