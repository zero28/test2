package com.gmail.zhou1992228.rpgsuit.command;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandRpgBuyMed implements CommandExecutor{

	private FileConfiguration config;
	public HashMap<String, ItemStack> itemInfos;
	public CommandRpgBuyMed(RPGSuit plugin) {
		config = RPGSuit.getConfigWithName("medicines.yml");
		Set<String> rpgs = config.getConfigurationSection("meds").getKeys(false);
		itemInfos = new HashMap<String, ItemStack>();
		for (String name : rpgs) {
			String displayName = config.getString("meds." + name + ".name", "δ����ҩ��");
			int recover = config.getInt("meds." + name + ".hp", 0);
			int count = config.getInt("meds." + name + ".count", 1);
			ItemStack it = new ItemStack(Material.INK_SACK, count, (short) 15);
			ItemUtil.setName(it, displayName);
			ItemUtil.addLore(it, "�ظ�����ֵ:" + recover);
			itemInfos.put(name, it);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (!sender.hasPermission("rpgmed.buy")) {
				sender.sendMessage("��û�й���RPGҩ���Ȩ��");
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
			int cost = config.getInt("meds." + args[0] + ".cost", 100);
			if (UserInfoUtils.haveRequire((Player) sender, "$" + cost)) {
				UserInfoUtils.takeItem((Player) sender, "$" + cost);
				((Player) sender).getInventory().addItem(itemInfos.get(args[0]).clone());
				//PlayerUtil.giveRPGItem((Player) sender, itemInfos.get(args[0]));
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
