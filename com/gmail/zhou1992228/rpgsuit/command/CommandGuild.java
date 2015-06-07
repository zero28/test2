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
					arg0.sendMessage("你确定要离开帮派？输入/bp leave <帮派名称>  确认退出");
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
						arg0.sendMessage("你没有帮派，别闹");
					} else {
						GuildManager.ins.GetGuild((Player) arg0).AcceptBy(arg3[1], (Player) arg0);
					}
				}
			} else if (arg3[0].equals("deny")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					if (GuildManager.ins.GetGuild((Player) arg0) == null) {
						arg0.sendMessage("你没有帮派，别闹");
					} else {
						GuildManager.ins.GetGuild((Player) arg0).DenyBy(arg3[1], (Player) arg0);
					}
				}
			} else if (arg3[0].equals("kick")) {
				if (arg3.length < 2) {
					arg0.sendMessage(usage());
				} else {
					if (GuildManager.ins.GetGuild((Player) arg0) == null) {
						arg0.sendMessage("你没有帮派，别闹");
					} else {
						GuildManager.ins.GetGuild((Player) arg0).KickBy(arg3[1], (Player) arg0);
					}
				}
			} else if (arg3[0].equals("applylist")) {
				if (GuildManager.ins.GetGuild((Player) arg0) == null) {
					arg0.sendMessage("你没有帮派，别闹");
				} else {
					arg0.sendMessage(GuildManager.ins.GetGuild((Player) arg0).applyList());
				}
			} else if (arg3[0].equals("upgrade")) {
				if (GuildManager.ins.GetGuild((Player) arg0) == null) {
					arg0.sendMessage("你没有帮派，别闹");
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
					if (arg3[2].equals("长老")) {
						tp = MemberType.MANAGER;
					}
					if (arg3[2].equals("帮众")) {
						tp = MemberType.MEMBER;
					}
					if (arg3[2].equals("精英")) {
						tp = MemberType.MEMBER_V2;
					}
					if (tp == null) {
						arg0.sendMessage("职位可以设置为长老/精英/帮众");
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
		usage.add("/bp list <页数> 查看帮派列表");
		usage.add("/bp join <帮派名称> 加入帮派");
		usage.add("/bp leave 退出帮派");
		usage.add("/bp info <帮派名称> 查看帮派情报");
		usage.add("/bp create <帮派名称> 创建帮派");

		usage.add("/bp accept <id> 接受玩家加入申请(长老以上权限)");
		usage.add("/bp applylist 查看申请加入的玩家");
		usage.add("/bp deny <id> 拒绝玩家加入申请(长老以上权限)");
		usage.add("/bp kick <id> 将玩家踢出帮派(长老以上权限)");
		usage.add("/bp set <id> <职位> 设置玩家职位(长老以上权限)");
		usage.add("/bp upgrade <设施名称> 升级设施，获得额外的效果");
		
		return (String[]) usage.toArray(new String[usage.size()]);
	}
}
