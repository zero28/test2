package com.gmail.zhou1992228.rpgsuit.guild;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Guild {
	enum MemberType {
		LEADER,
		MANAGER,
		MEMBER_V2,
		MEMBER,
	}
	
	private String name;
	private ConfigurationSection config;
	private Map<MemberType, List<String>> members = new HashMap<MemberType, List<String>>();
	private List<String> apply_member;
	
	public Guild(ConfigurationSection c) {
		config = c;
		name = config.getString("name", "UNKNOWN");
		config.set("name", name);
		members.put(MemberType.LEADER, config.getStringList("leader"));
		members.put(MemberType.MANAGER, config.getStringList("manager"));
		members.put(MemberType.MEMBER_V2, config.getStringList("memberv2"));
		members.put(MemberType.MEMBER, config.getStringList("member"));
		apply_member = config.getStringList("apply_member");
	}
	
	public void Save() {
		config.set("leader", members.get(MemberType.LEADER));
		config.set("manager", members.get(MemberType.MANAGER));
		config.set("memberv2", members.get(MemberType.MEMBER_V2));
		config.set("member", members.get(MemberType.MEMBER));
		config.set("apply_member", apply_member);
	}
	
	public void update() {
		
	}
	
	public String[] GetInfo() {
		return null;
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
		if (!havePlayer(p)) {
			by.sendMessage("��İ���û��������");
			return;
		}
		removePlayer(p);
		by.sendMessage("���Ѿ��� " + p + " �߳��˰���");
	}
	
	public boolean havePlayer(Player p) {
		return havePlayer(p.getName());
	}
	public boolean havePlayer(String playerName) {
		return members.get(MemberType.MEMBER).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.MEMBER_V2).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.MANAGER).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.LEADER).contains(playerName.toLowerCase());
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
	public void AcceptBy(Player p, Player by) {}
	public void SetType(Player p, Player by, MemberType type) {}
	

	public boolean isLeader(Player p) {
		return isLeader(p.getName());
	}
	private boolean isLeader(String playerName) {
		return members.get(MemberType.LEADER).contains(playerName.toLowerCase());
	}
	private boolean isManager(Player p) {
		return isManager(p.getName());
	}
	private boolean isManager(String playerName) {
		return members.get(MemberType.MANAGER).contains(playerName.toLowerCase()) ||
			   members.get(MemberType.LEADER).contains(playerName.toLowerCase());
	}
}