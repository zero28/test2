package com.gmail.zhou1992228.treasuremap;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.command.CommandRpgBuy;
import com.gmail.zhou1992228.rpgsuit.item.ItemProperty;
import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.util.RewardUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class TreasureMapUtils {

	public static Random random = new Random();
	public static boolean inited = false;
	
	public static ItemStack mapMaker(String name, Location loc, int lv) {
		ItemStack it = new ItemStack(Material.PAPER);
		ItemUtil.setName(it, name);
		ItemUtil.addLore(it, "这是一张藏宝图");
		ItemUtil.addLore(it, "藏宝地点: 怪物世界");
		ItemUtil.addLore(it, "坐标:");
		ItemUtil.addLore(it, loc.getBlockX() + " " + loc.getBlockZ());
		ItemUtil.addLore(it, "等级:" + lv / 10 * 10);
		return it;
	}

	public static boolean isTreasureMap(ItemStack it) {
		try {
			return it.getItemMeta().getLore().get(0).equals("这是一张藏宝图");
		} catch (Exception e) {
			return false;
		}
	}
	
	public static Location getTargetLocation(ItemStack it) {
		Location lc = null;
		if (isTreasureMap(it)) {
			String pos = it.getItemMeta().getLore().get(3);
			String[] s = pos.split(" ");
			int x = Integer.parseInt(s[0]);
			int z = Integer.parseInt(s[1]);
			World w = RPGSuit.ins.getServer().getWorld("mobworld");
			lc = new Location(w, x, 64, z);
		}
		return lc;
	}
	
	public static void tryDigTreasureMap(Player p) {
		if (isTreasureMap(p.getItemInHand())) {
			int lv = 0;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				if (s.startsWith("等级:")) {
					lv = Integer.parseInt(s.split(":")[1]);
					break;
				}
			}
			Location loc = getTargetLocation(p.getItemInHand());
			if (p.getLocation().getWorld().getName().equals(loc.getWorld().getName())) {
				if (Math.abs(p.getLocation().getBlockX() - loc.getBlockX()) < 5 &&
					Math.abs(p.getLocation().getBlockZ() - loc.getBlockZ()) < 5 ) {
					p.setItemInHand(null);
					giveTreasureMapReward(p, lv);
				} else {
					p.sendMessage("请在正确的位置挖掘藏宝图");
				}
			} else {
				p.sendMessage("请在正确的世界挖掘藏宝图");
			}
		}
	}

	public static void giveTreasureMapReward(Player p, int lv) {
		double damage_chance = RPGSuit.ins.getConfig().getDouble("treasuremap.damage", 0.1);
		double money_chance = RPGSuit.ins.getConfig().getDouble("treasuremap.money", 0.1);
		double monster_chance = RPGSuit.ins.getConfig().getDouble("treasuremap.monster", 0.1);
		double pb = random.nextDouble();
		if (pb < damage_chance) {
			p.sendMessage("你一铲子挖下去，地就爆炸了，你觉得这是一个陷阱");
			p.setHealth(((Damageable)p).getHealth() * 0.3);
			return;
		}
		pb -= damage_chance;
		if (pb < money_chance) {
			int ct = 200 + random.nextInt(lv * 50);
			p.sendMessage("你挖了一个箱子出来，从里面发现了$" + ct);
			UserInfoUtils.giveItem(p, "$" + ct);
			return;
		}
		pb -= money_chance;
		if (pb < monster_chance) {
			p.sendMessage("在你准备挖宝藏的时候，周围冲出了一群怪物，你知道你中计了！");
			Location loc = p.getLocation();
			Location l1 = loc.clone();
			Location l2 = loc.clone();
			Location l3 = loc.clone();
			Location l4 = loc.clone();
			l1.add(7, 10, 0);
			l2.add(-7, 10, 0);
			l3.add(0, 10, 7);
			l4.add(0, 10, -7);
			l1.getWorld().spawnEntity(l1, EntityType.SKELETON);
			l1.getWorld().spawnEntity(l1, EntityType.SKELETON);
			l2.getWorld().spawnEntity(l2, EntityType.ZOMBIE);
			l2.getWorld().spawnEntity(l2, EntityType.ZOMBIE);
			l3.getWorld().spawnEntity(l3, EntityType.CREEPER);
			l3.getWorld().spawnEntity(l3, EntityType.CREEPER);
			l4.getWorld().spawnEntity(l4, EntityType.SPIDER);
			l4.getWorld().spawnEntity(l4, EntityType.SPIDER);
			return;
		}
		p.getInventory().addItem(getRandomTreasureItem(p, lv));
	}

	public static ItemStack getRandomTreasureItem(Player p, int level) {
		if (random.nextDouble() < 0.6) {
			return RewardUtil.getRandomStone(level * 120).it.clone();
		} else {
			return RewardUtil.getRandomArmorWithLevel(level).it.clone();
		}
	}

	public static ItemStack getRandomTreasureItemStone(Player p) {
		return null;
	}

	public static ItemStack getRandomTreasureItemArmorV2(Player p) {
		int count = 0;
		for (ItemProperty ip : CommandRpgBuy.ins.itemInfos.values()) {
			if (ip.level < 20 || ip.level > 70) continue;
			++count;
		}
		int cc = random.nextInt(count);
		ItemProperty ipp = null;
		for (ItemProperty ip : CommandRpgBuy.ins.itemInfos.values()) {
			if (ip.level < 20 || ip.level > 70) continue;
			if (cc == 0) {
				ipp = ip.Clone();
				break;
			}
			--cc;
		}
		if (ipp == null) {
			return null;
		}
		double r1 = 0.85 + random.nextDouble() * 0.3;
		double r2 = 0.85 + random.nextDouble() * 0.3;
		double r3 = 0.85 + random.nextDouble() * 0.3;
		double r4 = 0.6 + random.nextDouble() * 0.8;
		ipp.def *= r1;
		ipp.str *= r2;
		ipp.hp *= r3;
		ipp.maxdura *= r4;
		p.sendMessage("你获得了  " + ipp.name);
		return ItemProperty.makeItem(ipp);
	}

	public static ItemStack getRandomTreasureItemArmor(Player p) {
		double tot_pro = 0;
		for (ItemProperty ip : CommandRpgBuy.ins.itemInfos.values()) {
			if (ip.level < 20 || ip.level > 70) continue;
			tot_pro += 1.0 / ip.cost;
		}
		double pp = random.nextDouble() * tot_pro;
		ItemProperty ipp = null;
		for (ItemProperty ip : CommandRpgBuy.ins.itemInfos.values()) {
			if (ip.level < 20 || ip.level > 70) continue;
			if (pp < 1.0 / ip.cost) {
				ipp = ip.Clone();
				break;
			}
			pp -= 1.0 / ip.cost;
		}
		if (ipp == null) {
			return null;
		}
		ipp.def *= 1.15;
		ipp.str *= 1.15;
		ipp.hp *= 1.15;
		ipp.maxdura *= 1.4;
		p.sendMessage("你获得了  " + ipp.name);
		return ItemProperty.makeItem(ipp);
	}
}
