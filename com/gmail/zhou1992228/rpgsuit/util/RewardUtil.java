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
		itemList.put("Ì«ÑôÊ¯LV1", new RewardItem(22050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯")), Type.STONE, "Ì«ÑôÊ¯LV1"));
		itemList.put("ÔÂÁÁÊ¯LV1", new RewardItem(22050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯")), Type.STONE, "ÔÂÁÁÊ¯LV1"));
		itemList.put("¹âÃ¢Ê¯LV1", new RewardItem(22050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯")), Type.STONE, "¹âÃ¢Ê¯LV1"));
		
		itemList.put("ÔÂÁÁÊ¯Ëé¿éLV1", new RewardItem(1050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯Ëé¿é").Clone().setStoneLevel(1)), Type.STONE, "ÔÂÁÁÊ¯Ëé¿éLV1"));
		itemList.put("ÔÂÁÁÊ¯Ëé¿éLV2", new RewardItem(1050 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯Ëé¿é").Clone().setStoneLevel(2)), Type.STONE, "ÔÂÁÁÊ¯Ëé¿éLV2"));
		itemList.put("ÔÂÁÁÊ¯Ëé¿éLV3", new RewardItem(1050 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯Ëé¿é").Clone().setStoneLevel(3)), Type.STONE, "ÔÂÁÁÊ¯Ëé¿éLV3"));
		itemList.put("ÔÂÁÁÊ¯Ëé¿éLV4", new RewardItem(1050 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯Ëé¿é").Clone().setStoneLevel(4)), Type.STONE, "ÔÂÁÁÊ¯Ëé¿éLV4"));
		itemList.put("ÔÂÁÁÊ¯Ëé¿éLV5", new RewardItem(1050 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯Ëé¿é").Clone().setStoneLevel(5)), Type.STONE, "ÔÂÁÁÊ¯Ëé¿éLV5"));
		itemList.put("¹âÃ¢Ê¯Ëé¿éLV1", new RewardItem(1050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯Ëé¿é").Clone().setStoneLevel(1)), Type.STONE, "¹âÃ¢Ê¯Ëé¿éLV1"));
		itemList.put("¹âÃ¢Ê¯Ëé¿éLV2", new RewardItem(1050 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯Ëé¿é").Clone().setStoneLevel(2)), Type.STONE, "¹âÃ¢Ê¯Ëé¿éLV2"));
		itemList.put("¹âÃ¢Ê¯Ëé¿éLV3", new RewardItem(1050 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯Ëé¿é").Clone().setStoneLevel(3)), Type.STONE, "¹âÃ¢Ê¯Ëé¿éLV3"));
		itemList.put("¹âÃ¢Ê¯Ëé¿éLV4", new RewardItem(1050 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯Ëé¿é").Clone().setStoneLevel(4)), Type.STONE, "¹âÃ¢Ê¯Ëé¿éLV4"));
		itemList.put("¹âÃ¢Ê¯Ëé¿éLV5", new RewardItem(1050 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯Ëé¿é").Clone().setStoneLevel(5)), Type.STONE, "¹âÃ¢Ê¯Ëé¿éLV5"));
		itemList.put("Ì«ÑôÊ¯Ëé¿éLV1", new RewardItem(1050,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯Ëé¿é").Clone().setStoneLevel(1)), Type.STONE, "Ì«ÑôÊ¯Ëé¿éLV1"));
		itemList.put("Ì«ÑôÊ¯Ëé¿éLV2", new RewardItem(1050 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯Ëé¿é").Clone().setStoneLevel(2)), Type.STONE, "Ì«ÑôÊ¯Ëé¿éLV2"));
		itemList.put("Ì«ÑôÊ¯Ëé¿éLV3", new RewardItem(1050 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯Ëé¿é").Clone().setStoneLevel(3)), Type.STONE, "Ì«ÑôÊ¯Ëé¿éLV3"));
		itemList.put("Ì«ÑôÊ¯Ëé¿éLV4", new RewardItem(1050 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯Ëé¿é").Clone().setStoneLevel(4)), Type.STONE, "Ì«ÑôÊ¯Ëé¿éLV4"));
		itemList.put("Ì«ÑôÊ¯Ëé¿éLV5", new RewardItem(1050 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯Ëé¿é").Clone().setStoneLevel(5)), Type.STONE, "Ì«ÑôÊ¯Ëé¿éLV5"));
		
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV1", new RewardItem(50,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(1)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV1"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV2", new RewardItem(50 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(2)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV2"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV3", new RewardItem(50 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(3)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV3"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV4", new RewardItem(50 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(4)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV4"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV5", new RewardItem(50 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(5)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV5"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV6", new RewardItem(50 * 32,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(6)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV6"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV7", new RewardItem(50 * 64,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(7)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV7"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV8", new RewardItem(50 * 128,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(8)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV8"));
		itemList.put("Ì«ÑôÊ¯ËéÆ¬LV9", new RewardItem(50 * 256,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("Ì«ÑôÊ¯ËéÆ¬").Clone().setStoneLevel(9)), Type.STONE, "Ì«ÑôÊ¯ËéÆ¬LV9"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV1", new RewardItem(50,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(1)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV1"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV2", new RewardItem(50 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(2)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV2"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV3", new RewardItem(50 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(3)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV3"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV4", new RewardItem(50 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(4)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV4"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV5", new RewardItem(50 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(5)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV5"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV6", new RewardItem(50 * 32,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(6)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV6"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV7", new RewardItem(50 * 64,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(7)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV7"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV8", new RewardItem(50 * 128,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(8)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV8"));
		itemList.put("ÔÂÁÁÊ¯ËéÆ¬LV9", new RewardItem(50 * 256,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("ÔÂÁÁÊ¯ËéÆ¬").Clone().setStoneLevel(9)), Type.STONE, "ÔÂÁÁÊ¯ËéÆ¬LV9"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV1", new RewardItem(50,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(1)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV1"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV2", new RewardItem(50 * 2,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(2)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV2"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV3", new RewardItem(50 * 4,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(3)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV3"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV4", new RewardItem(50 * 8,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(4)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV4"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV5", new RewardItem(50 * 16,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(5)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV5"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV6", new RewardItem(50 * 32,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(6)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV6"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV7", new RewardItem(50 * 64,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(7)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV7"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV8", new RewardItem(50 * 128,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(8)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV8"));
		itemList.put("¹âÃ¢Ê¯ËéÆ¬LV9", new RewardItem(50 * 256,
				StoneProperty.makeItem(CommandStoneBuy.ins.stoneInfos.get("¹âÃ¢Ê¯ËéÆ¬").Clone().setStoneLevel(9)), Type.STONE, "¹âÃ¢Ê¯ËéÆ¬LV9"));
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
