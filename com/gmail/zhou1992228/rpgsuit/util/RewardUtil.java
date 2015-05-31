package com.gmail.zhou1992228.rpgsuit.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.command.CommandRpgBuy;
import com.gmail.zhou1992228.rpgsuit.command.CommandStoneBuy;
import com.gmail.zhou1992228.rpgsuit.item.ItemProperty;
import com.gmail.zhou1992228.rpgsuit.item.StoneProperty;
import com.gmail.zhou1992228.rpgsuit.util.RewardUtil.RewardItem.Type;

public class RewardUtil {
	public static class RewardItem {
		enum Type {
			STONE,
			ARMOR,
			VALUE,
		}
		public int value;
		public ItemStack it;
		public String val;
		public Type tp;
		public String name;
		public RewardItem(int v, ItemStack i, Type type, String name) {
			value = v; it = i; tp = type; this.name = name;
		}
		public RewardItem(String v) {
			val = v;
			tp = Type.VALUE;
		}
	}
	public static Random random = new Random();
	public static Map<String, RewardItem> itemList = new HashMap<String, RewardItem>();
	
	public static void Init() {
		try {
		itemList.put("̫��ʯLV1", new RewardItem(22050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ")), Type.STONE, "̫��ʯLV1"));
		itemList.put("����ʯLV1", new RewardItem(22050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ")), Type.STONE, "����ʯLV1"));
		itemList.put("��âʯLV1", new RewardItem(22050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ")), Type.STONE, "��âʯLV1"));
		
		itemList.put("����ʯ���LV1", new RewardItem(1050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ���").Clone().setStoneLevel(1)), Type.STONE, "����ʯ���LV1"));
		itemList.put("����ʯ���LV2", new RewardItem(1050 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ���").Clone().setStoneLevel(2)), Type.STONE, "����ʯ���LV2"));
		itemList.put("����ʯ���LV3", new RewardItem(1050 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ���").Clone().setStoneLevel(3)), Type.STONE, "����ʯ���LV3"));
		itemList.put("����ʯ���LV4", new RewardItem(1050 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ���").Clone().setStoneLevel(4)), Type.STONE, "����ʯ���LV4"));
		itemList.put("����ʯ���LV5", new RewardItem(1050 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ���").Clone().setStoneLevel(5)), Type.STONE, "����ʯ���LV5"));
		itemList.put("��âʯ���LV1", new RewardItem(1050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ���").Clone().setStoneLevel(1)), Type.STONE, "��âʯ���LV1"));
		itemList.put("��âʯ���LV2", new RewardItem(1050 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ���").Clone().setStoneLevel(2)), Type.STONE, "��âʯ���LV2"));
		itemList.put("��âʯ���LV3", new RewardItem(1050 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ���").Clone().setStoneLevel(3)), Type.STONE, "��âʯ���LV3"));
		itemList.put("��âʯ���LV4", new RewardItem(1050 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ���").Clone().setStoneLevel(4)), Type.STONE, "��âʯ���LV4"));
		itemList.put("��âʯ���LV5", new RewardItem(1050 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ���").Clone().setStoneLevel(5)), Type.STONE, "��âʯ���LV5"));
		itemList.put("̫��ʯ���LV1", new RewardItem(1050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ���").Clone().setStoneLevel(1)), Type.STONE, "̫��ʯ���LV1"));
		itemList.put("̫��ʯ���LV2", new RewardItem(1050 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ���").Clone().setStoneLevel(2)), Type.STONE, "̫��ʯ���LV2"));
		itemList.put("̫��ʯ���LV3", new RewardItem(1050 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ���").Clone().setStoneLevel(3)), Type.STONE, "̫��ʯ���LV3"));
		itemList.put("̫��ʯ���LV4", new RewardItem(1050 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ���").Clone().setStoneLevel(4)), Type.STONE, "̫��ʯ���LV4"));
		itemList.put("̫��ʯ���LV5", new RewardItem(1050 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ���").Clone().setStoneLevel(5)), Type.STONE, "̫��ʯ���LV5"));
		
		itemList.put("̫��ʯ��ƬLV1", new RewardItem(50,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(1)), Type.STONE, "̫��ʯ��ƬLV1"));
		itemList.put("̫��ʯ��ƬLV2", new RewardItem(50 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(2)), Type.STONE, "̫��ʯ��ƬLV2"));
		itemList.put("̫��ʯ��ƬLV3", new RewardItem(50 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(3)), Type.STONE, "̫��ʯ��ƬLV3"));
		itemList.put("̫��ʯ��ƬLV4", new RewardItem(50 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(4)), Type.STONE, "̫��ʯ��ƬLV4"));
		itemList.put("̫��ʯ��ƬLV5", new RewardItem(50 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(5)), Type.STONE, "̫��ʯ��ƬLV5"));
		itemList.put("̫��ʯ��ƬLV6", new RewardItem(50 * 32,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(6)), Type.STONE, "̫��ʯ��ƬLV6"));
		itemList.put("̫��ʯ��ƬLV7", new RewardItem(50 * 64,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(7)), Type.STONE, "̫��ʯ��ƬLV7"));
		itemList.put("̫��ʯ��ƬLV8", new RewardItem(50 * 128,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(8)), Type.STONE, "̫��ʯ��ƬLV8"));
		itemList.put("̫��ʯ��ƬLV9", new RewardItem(50 * 256,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("̫��ʯ��Ƭ").Clone().setStoneLevel(9)), Type.STONE, "̫��ʯ��ƬLV9"));
		itemList.put("����ʯ��ƬLV1", new RewardItem(50,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(1)), Type.STONE, "����ʯ��ƬLV1"));
		itemList.put("����ʯ��ƬLV2", new RewardItem(50 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(2)), Type.STONE, "����ʯ��ƬLV2"));
		itemList.put("����ʯ��ƬLV3", new RewardItem(50 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(3)), Type.STONE, "����ʯ��ƬLV3"));
		itemList.put("����ʯ��ƬLV4", new RewardItem(50 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(4)), Type.STONE, "����ʯ��ƬLV4"));
		itemList.put("����ʯ��ƬLV5", new RewardItem(50 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(5)), Type.STONE, "����ʯ��ƬLV5"));
		itemList.put("����ʯ��ƬLV6", new RewardItem(50 * 32,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(6)), Type.STONE, "����ʯ��ƬLV6"));
		itemList.put("����ʯ��ƬLV7", new RewardItem(50 * 64,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(7)), Type.STONE, "����ʯ��ƬLV7"));
		itemList.put("����ʯ��ƬLV8", new RewardItem(50 * 128,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(8)), Type.STONE, "����ʯ��ƬLV8"));
		itemList.put("����ʯ��ƬLV9", new RewardItem(50 * 256,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("����ʯ��Ƭ").Clone().setStoneLevel(9)), Type.STONE, "����ʯ��ƬLV9"));
		itemList.put("��âʯ��ƬLV1", new RewardItem(50,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(1)), Type.STONE, "��âʯ��ƬLV1"));
		itemList.put("��âʯ��ƬLV2", new RewardItem(50 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(2)), Type.STONE, "��âʯ��ƬLV2"));
		itemList.put("��âʯ��ƬLV3", new RewardItem(50 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(3)), Type.STONE, "��âʯ��ƬLV3"));
		itemList.put("��âʯ��ƬLV4", new RewardItem(50 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(4)), Type.STONE, "��âʯ��ƬLV4"));
		itemList.put("��âʯ��ƬLV5", new RewardItem(50 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(5)), Type.STONE, "��âʯ��ƬLV5"));
		itemList.put("��âʯ��ƬLV6", new RewardItem(50 * 32,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(6)), Type.STONE, "��âʯ��ƬLV6"));
		itemList.put("��âʯ��ƬLV7", new RewardItem(50 * 64,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(7)), Type.STONE, "��âʯ��ƬLV7"));
		itemList.put("��âʯ��ƬLV8", new RewardItem(50 * 128,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(8)), Type.STONE, "��âʯ��ƬLV8"));
		itemList.put("��âʯ��ƬLV9", new RewardItem(50 * 256,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("��âʯ��Ƭ").Clone().setStoneLevel(9)), Type.STONE, "��âʯ��ƬLV9"));
		for (ItemProperty ip : CommandRpgBuy.ins.itemInfos.values()) {
			if (ip.level > 110) continue;
			ItemProperty i = ip.Clone();
			i.setDef((int) (i.getDef() * 1.2));
			i.setHp((int) (i.getHp() * 1.3));
			i.setStr((int) (i.getStr() * 1.25));
			i.setMaxdura((int) (i.getDura() * 1.15));
			itemList.put(i.name, new RewardItem((int) (i.cost * i.level / 10), ItemProperty.makeItem(i), Type.ARMOR, i.name));
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static RewardItem getRandomStone(int value) {
		double total_p = 0;
		for (RewardItem ri : itemList.values()) {
			if (ri.tp != Type.STONE) continue; 
			double pp = GetProbWithValue(value, ri.value);
			total_p += pp;
		}
		double r = random.nextDouble() * total_p;
		for (RewardItem ri : itemList.values()) {
			if (ri.tp != Type.STONE) continue;
			double p = GetProbWithValue(value, ri.value);
			if (p > r) {
				return ri;
			}
			r -= p;
		}
		return null;
	}
	
	public static RewardItem getRandomArmorWithLevel(int lv) {
		double total_p = 0;
		for (RewardItem ri : itemList.values()) {
			if (ri.tp != Type.ARMOR) continue;
			int diff = Math.abs(ItemProperty.getProperty(ri.it).level - lv);
			double pp = GuessFunc(diff / 10.0);
			total_p += pp;
		}
		double r = random.nextDouble() * total_p;
		for (RewardItem ri : itemList.values()) {
			if (ri.tp != Type.ARMOR) continue;
			int diff = Math.abs(ItemProperty.getProperty(ri.it).level - lv);
			double pp = GuessFunc(diff / 10.0);
			if (pp > r) {
				return ri;
			}
			r -= pp;
		}
		return null;
	}
	
	public static RewardItem getRandomArmorWithValue(int value) {
		double total_p = 0;
		for (RewardItem ri : itemList.values()) {
			if (ri.tp != Type.ARMOR) continue; 
			double pp = GetProbWithValue(value, ri.value);
			total_p += pp;
		}
		double r = random.nextDouble() * total_p;
		for (RewardItem ri : itemList.values()) {
			if (ri.tp != Type.ARMOR) continue;
			double p = GetProbWithValue(value, ri.value);
			if (p > r) {
				return ri;
			}
			r -= p;
		}
		return null;
	}
	/*
	public static RewardItem getRandomItemRewardWithValue(int value) {
		double total_p = 0;
		double vv = 0;
		for (RewardItem ri : itemList.values()) {
			double pp = GetProbWithValue(value, ri.value);
			total_p += pp;
			vv += ri.value * pp;
		}
		Bukkit.getLogger().info("E: " + vv / total_p);
		double r = random.nextDouble() * total_p;
		for (RewardItem ri : itemList.values()) {
			double p = GetProbWithValue(value, ri.value);
			if (p > r) {
				return ri;
			}
			r -= p;
		}
		return null;
	}
	*/
	public static double GetProbWithValue(int target_v, int v) {
		if (v > target_v * 2) return 0;
		return GuessFunc((Math.abs(target_v - v) * 2.0) / v);
	}
	
	public static double GuessFunc(double x) {
		return Math.exp(-(x * x) / 2) / (Math.sqrt(2 * Math.PI));
	}
	/*
	public static ItemStack getRewardItem(String name) {
		ItemStack it = null;
		if (CommandStoneBuy.ins.stoneInfos.containsKey(name)) {
			it = StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get(name));
		} else if (CommandRpgBuy.ins.itemInfos.containsKey(name)) {
			it = ItemProperty.makeItem(CommandRpgBuy.ins.itemInfos.get(name));
		}
		return it;
	}
	*/
}
