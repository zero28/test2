package com.gmail.zhou1992228.rpgsuit.command;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemProperty;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandRpgBuy implements CommandExecutor{

	private FileConfiguration config;
	static public CommandRpgBuy ins;
	public HashMap<String, ItemProperty> itemInfos;
	public CommandRpgBuy(RPGSuit plugin) {
		ins = this;
		config = RPGSuit.getConfigWithName("rpgitems.yml");
		Set<String> rpgs = config.getConfigurationSection("rpgs").getKeys(false);
		itemInfos = new HashMap<String, ItemProperty>();
		for (String name : rpgs) {
			itemInfos.put(name, 
					ItemProperty.getItemProperty(
							config.getString("rpgs." + name + ".name", "δ������Ʒ"),
							config.getString("rpgs." + name + ".material", "STICK"),
							config.getInt("rpgs." + name + ".hp", 0),
							config.getInt("rpgs." + name + ".str", 0),
							config.getInt("rpgs." + name + ".def", 0),
							config.getInt("rpgs." + name + ".maxdura", 0),
							config.getInt("rpgs." + name + ".level", 150),
							config.getString("rpgs." + name + ".special", "��"),
							config.getInt("rpgs." + name + ".cost", 50000),
							config.getInt("rpgs." + name + ".doublehit", 0),
							config.getInt("rpgs." + name + ".repairtime", 0),
							0
							));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (!sender.hasPermission("rpgshop.buy")) {
				sender.sendMessage("��û�й���RPG��Ʒ��Ȩ��");
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage("����������Ҫ���RPG��Ʒ");
				return true;
			}
			if (!itemInfos.containsKey(args[0])) {
				sender.sendMessage("û�����RPG��Ʒ/��RPG��Ʒ�����ۣ�");
				return true;
			}
			int cost = itemInfos.get(args[0]).cost;
			if (UserInfoUtils.haveRequire((Player) sender, "$" + cost)) {
				UserInfoUtils.takeItem((Player) sender, "$" + cost);
				PlayerUtil.giveRPGItem((Player) sender, itemInfos.get(args[0]));
			} else {
				sender.sendMessage("��û���㹻����Ʒ/��Ǯ���������Ʒ");
			}
			return true;
		} else {
			sender.sendMessage("only player can do this");
			return true;
		}
	}
}
