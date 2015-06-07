package com.gmail.zhou1992228.rpgsuit.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.guild.Guild.MemberType;
import com.gmail.zhou1992228.rpgsuit.guild.GuildManager;

public class CommandGuild  implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg3.length == 0) {
			arg0.sendMessage(usage());
		} else {
			if (arg3[0].equals("list")) {
				int idx = 1;
				if (arg3.length >= 2) {
					try {
						idx = Integer.parseInt(arg3[1]);
					} catch (Exception e) {}
				}
				arg0.sendMessage(GuildManager.ins.GetGuildShortInfo(idx));
			} else if (arg3[0].equals("join")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					GuildManager.ins.Join((Player) arg0, arg3[1]);
				}
			} else if (arg3[0].equals("leave")) {
				if (arg3.length < 2) {
					arg0.sendMessage("��ȷ��Ҫ�뿪���ɣ�����/bp leave <��������>  ȷ���˳�");
				} else {
					GuildManager.ins.Leave((Player) arg0, arg3[1]);
				}
			} else if (arg3[0].equals("info")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					GuildManager.ins.SendGuildInfo((Player) arg0, arg3[1]);
				}
			} else if (arg3[0].equals("create")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					GuildManager.ins.Create((Player) arg0, arg3[1]);
				}
			} else if (arg3[0].equals("accept")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					if (GuildManager.ins.GetGuild((Player) arg0) == null) {
						arg0.sendMessage("��û�а��ɣ�����");
					} else {
						GuildManager.ins.GetGuild((Player) arg0).AcceptBy(arg3[1], (Player) arg0);
					}
				}
			} else if (arg3[0].equals("deny")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					if (GuildManager.ins.GetGuild((Player) arg0) == null) {
						arg0.sendMessage("��û�а��ɣ�����");
					} else {
						GuildManager.ins.GetGuild((Player) arg0).DenyBy(arg3[1], (Player) arg0);
					}
				}
			} else if (arg3[0].equals("kick")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					if (GuildManager.ins.GetGuild((Player) arg0) == null) {
						arg0.sendMessage("��û�а��ɣ�����");
					} else {
						GuildManager.ins.GetGuild((Player) arg0).KickBy(arg3[1], (Player) arg0);
					}
				}
			} else if (arg3[0].equals("applylist")) {
				if (GuildManager.ins.GetGuild((Player) arg0) == null) {
					arg0.sendMessage("��û�а��ɣ�����");
				} else {
					arg0.sendMessage(GuildManager.ins.GetGuild((Player) arg0).applyList());
				}
			} else if (arg3[0].equals("upgrade")) {
				if (GuildManager.ins.GetGuild((Player) arg0) == null) {
					arg0.sendMessage("��û�а��ɣ�����");
				} else {
					if (arg3.length < 2) {
						arg0.sendMessage(usage());
					} else {
						GuildManager.ins.Upgrade((Player) arg0, arg3[1]);
					}
				}
			} else if (arg3[0].equals("set")) {
				if (arg3.length < 3) {
					arg0.sendMessage(usage());
				} else {
					MemberType tp = null;
					if (arg3[2].equals("����")) {
						tp = MemberType.MANAGER;
					}
					if (arg3[2].equals("����")) {
						tp = MemberType.MEMBER;
					}
					if (arg3[2].equals("��Ӣ")) {
						tp = MemberType.MEMBER_V2;
					}
					if (tp == null) {
						arg0.sendMessage("ְλ��������Ϊ����/��Ӣ/����");
					} else {
						GuildManager.ins.SetType(arg3[1], (Player) arg0, tp);
					}
				}
			} else {
				arg0.sendMessage(usage());
			}
		}
		return true;
	}
	
	public String[] usage() {
		ArrayList<String> usage = new ArrayList<String>();
		usage.add("/bp list <ҳ��> �鿴�����б�");
		usage.add("/bp join <��������> �������");
		usage.add("/bp leave �˳�����");
		usage.add("/bp info <��������> �鿴�����鱨");
		usage.add("/bp create <��������> ��������");

		usage.add("/bp accept <id> ������Ҽ�������(��������Ȩ��)");
		usage.add("/bp applylist �鿴�����������");
		usage.add("/bp deny <id> �ܾ���Ҽ�������(��������Ȩ��)");
		usage.add("/bp kick <id> ������߳�����(��������Ȩ��)");
		usage.add("/bp set <id> <ְλ> �������ְλ(��������Ȩ��)");
		usage.add("/bp upgrade <��ʩ����> ������ʩ����ö����Ч��");
		
		return (String[]) usage.toArray(new String[usage.size()]);
	}
}
