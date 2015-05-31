package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.task.TaskMinuteUpdater;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class SkillMakeMed extends SkillBase implements CommandExecutor {
	public static SkillMakeMed ins;
	public FileConfiguration config;
	public Random random = new Random();
	
	public Map<Material, Integer> value_list = new HashMap<Material, Integer>();
	public Map<String, ItemStack> item_list = new HashMap<String, ItemStack>();
	public List<String> names = new ArrayList<String>();

	public SkillMakeMed(String n) {
		super(n);
		ins = this;
		value_list.put(Material.COOKIE, 1);
		value_list.put(Material.NETHER_WARTS, 1);
		value_list.put(Material.SEEDS, 1);
		value_list.put(Material.INK_SACK, 1);
		value_list.put(Material.SUGAR, 2);
		value_list.put(Material.SUGAR_CANE, 2);
		value_list.put(Material.MELON, 2);
		value_list.put(Material.CARROT_ITEM, 2);
		value_list.put(Material.POTATO_ITEM, 2);
		value_list.put(Material.BAKED_POTATO, 3);
		value_list.put(Material.EGG, 3);
		value_list.put(Material.GOLD_NUGGET, 3);
		value_list.put(Material.PUMPKIN, 5);
		value_list.put(Material.WHEAT, 5);
		value_list.put(Material.SPECKLED_MELON, 6);
		value_list.put(Material.BLAZE_POWDER, 6);
		value_list.put(Material.APPLE, 8);
		value_list.put(Material.PORK, 10);
		value_list.put(Material.RAW_BEEF, 10);
		value_list.put(Material.GRILLED_PORK, 11);
		value_list.put(Material.COOKED_BEEF, 11);
		value_list.put(Material.BREAD, 15);
		value_list.put(Material.PUMPKIN_PIE, 15);
		value_list.put(Material.RAW_FISH, 15);
		value_list.put(Material.RAW_CHICKEN, 15);
		value_list.put(Material.COOKED_CHICKEN, 16);
		value_list.put(Material.COOKED_FISH, 16);
		value_list.put(Material.GOLDEN_CARROT, 20);
		value_list.put(Material.CAKE, 20);
		value_list.put(Material.GOLDEN_APPLE, 25);
		value_list.put(Material.POISONOUS_POTATO, 25);
		value_list.put(Material.GHAST_TEAR, 50);
		names.add("С����");
		names.add("�󻹵�");
		names.add("ǧ�걣�ĵ�");
		names.add("��ת�ػ굤");
		names.add("���������");
		names.add("����");
		names.add("ʮ�㷵����");
		names.add("������");
		names.add("��ѩɢ");
		names.add("�����컯��");
		names.add("������");
		RPGSuit.ins.getCommand("lianyao").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			tryMakeMed((Player)arg0);
		}
		return true;
	}

	@Override
	public String getDescription() {
		return "С���ӣ��ҿ���������棬�������ѧ��ҩ�ɣ���Ȼ��Ҫ��ѧ�ѵģ����㿴��ѧ��֮�󣬽�4�����Ϸ���ǰ4��Ȼ������/lianyao�Ϳ��Ի�����Ϊ���棬��������ҩ����";
	}

	@Override
	public String getDescription(Player p) {
		int level = this.getLevel(p);
		return "��ǰ�ȼ�Ϊ: " + this.getLevel(p) +
				",   ��ҩ�������� " + (level / 10 + 10) + " �㣬" +
				" Ʒ�ʱ���Ϊ: " + (level + 10);
	}
	
	public void tryMakeMed(Player p) {
		int lv = this.getLevel(p);
		if (lv <= 0) {
			p.sendMessage("��ҩ�ɲ���˵����ģ���ѧϰѧϰ�ɣ�");
			return;
		}
		if (UserInfoUtils.getInt(p, TaskMinuteUpdater.KEY_STRENGTH) < 10 + lv / 10) {
			p.sendMessage("��û���㹻����������ҩ�ˣ�");
			return;
		}
		int value_count = 0;
		for (int i = 0; i < 4; ++i) {
			if (p.getInventory().getItem(i) == null) {
				p.sendMessage("�뽫��ҩ����׼����");
				return;
			}
			if (!value_list.containsKey(p.getInventory().getItem(i).getType())) {
				p.sendMessage("����ʲô������������ҩ�ģ���������԰���");
				return;
			}
			value_count += value_list.get(p.getInventory().getItem(i).getType());
		}
		for (int i = 0; i < 4; ++i) {
			if (p.getInventory().getItem(i).getAmount() == 1) {
				p.getInventory().setItem(i, null);
			} else {
				p.getInventory().getItem(i).setAmount(
						p.getInventory().getItem(i).getAmount() - 1);
			}
		}
		UserInfoUtils.minus(p, TaskMinuteUpdater.KEY_STRENGTH, 10 + lv / 10);
		double pp;
		if (value_count >= 35) {
			pp = lv / 300.0 + 0.25 + (value_count - 35) / 100.0;
		} else {
			pp = lv / 300.0 - ((15 - value_count) / 300.0);
		}
		if (random.nextDouble() < pp) {
			p.sendMessage("��ҩ�ɹ���");
			p.getInventory().addItem(makeRandomMed(lv));
		} else {
			p.sendMessage("��ҩʧ�ܣ�");
			p.getInventory().addItem(makeNormalMed());
		}
	}
	
	public ItemStack makeNormalMed() {
		if (random.nextBoolean()) {
			return makeMed("��Ƥҩ��", 100);
		} else {
			return makeMed("��ҩ", 300);
		}
	}
	
	public ItemStack makeRandomMed(int skillLevel) {
		int r = random.nextInt(names.size());
		int multiply = 15 + r;
		int quality = skillLevel + 10;
		return makeMed(names.get(r), multiply * quality);
	}
	
	public static ItemStack makeMed(String name, int recover) {
		ItemStack it = new ItemStack(Material.INK_SACK, 1, (short) 15);
		ItemUtil.setName(it, name);
		ItemUtil.addLore(it, name);
		ItemUtil.addLore(it, "�ظ�����ֵ:" + recover);
		return it;
	}

}
