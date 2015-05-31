package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandRepair implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			int cost = ItemUtil.getRepairCost(((Player) arg0).getItemInHand());
			if (arg3.length > 0) {
				if (arg3[0].equalsIgnoreCase("cost")) {
					arg0.sendMessage("�������Ʒ�軨�� $" + cost);
				}
				return true;
			}
			if (UserInfoUtils.haveRequire((Player) arg0, "$" + cost)) {
				UserInfoUtils.takeItem((Player) arg0, "$" + cost);
				((Player) arg0).setItemInHand(ItemUtil.RepairRPGItem(((Player) arg0).getItemInHand()));
				arg0.sendMessage("װ��������ϣ��������+1");
			} else {
				arg0.sendMessage("��û���㹻�Ľ�Ǯ���������Ʒ��");
			}
		}
		return true;
	}
}
