package com.gmail.zhou1992228.rpgsuit.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.listener.PlayerJoinListener;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.quiz.QuizMaster;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class InventoryInfoViewer implements InventoryHolder {
	Player p;
	public InventoryInfoViewer(Player pl) {
		p = pl;
	}

	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(null, 54);
	}

	public static void addInfosForPlayer(Inventory iv, Player p) {
		setInfo(iv, 0, new ItemStack(Material.SKULL_ITEM, 1, (short)3), "等级", String.format("%d", PlayerUtil.getLevel(p)));
		setInfo(iv, 1, new ItemStack(Material.WOOD_SWORD), "攻击", String.format("%d:装备武器/学习技能以提高攻击力", UserInfoUtils.getInt(p, "攻击力")));
		setInfo(iv, 2, new ItemStack(Material.LEATHER_CHESTPLATE), "防御", String.format("%d:装备衣服/学习技能以提高防御力", UserInfoUtils.getInt(p, "防御力")));
		setInfo(iv, 3, new ItemStack(Material.LEATHER_HELMET), "生命值", String.format("%d:装备头盔/学习技能以提高生命值", UserInfoUtils.getInt(p, "生命值")));
		setInfo(iv, 4, new ItemStack(Material.EXP_BOTTLE), "经验值", String.format("%d:击杀怪物/完成任务/通过竞技场以获得经验值", UserInfoUtils.getInt(p, "经验值")));

		setInfo(iv, 6, new ItemStack(Material.BLAZE_POWDER), "怒气值", String.format("%d:攻击怪物/被怪物攻击以获得怒气:释放技能时使用", UserInfoUtils.getInt(p, "怒气值")));
		setInfo(iv, 7, new ItemStack(Material.WOOD_PICKAXE), "体力值", String.format("%d:自动恢复:炼药、打造等消耗体力", UserInfoUtils.getInt(p, "体力")));
		
		setInfo(iv, 9, new ItemStack(Material.PAPER), "每日任务剩余次数", String.format("%d", UserInfoUtils.getInt(p, "每日任务剩余次数")));
		setInfo(iv, 10, new ItemStack(Material.PAPER), "剩余竞技场次数", String.format("%d", UserInfoUtils.getInt(p, "剩余竞技场次数")));
		setInfo(iv, 11, new ItemStack(Material.PAPER), "本周剩余双倍经验时间", String.format("%d", UserInfoUtils.getInt(p, "剩余双倍时间")));
		setInfo(iv, 12, new ItemStack(Material.PAPER), "死亡保护次数", String.format("%d", UserInfoUtils.getInt(p, PlayerJoinListener.KEY_DEATH_PROTECT)));
		setInfo(iv, 13, new ItemStack(Material.PAPER), QuizMaster.QUIZ_COUNT_KEY, String.format("%d", UserInfoUtils.getInt(p, QuizMaster.QUIZ_COUNT_KEY)));
		
		setInfo(iv, 45, new ItemStack(Material.INK_SACK, 1, (short) 15), "炼药", String.format("%d级:使用材料/体力炼药，恢复生命值", UserInfoUtils.getInt(p, "技能炼药等级")));
		setInfo(iv, 46, new ItemStack(Material.LEATHER_BOOTS), "多段跳", String.format("%d级:使用羽毛:跳跃多次", UserInfoUtils.getInt(p, "技能多段跳等级")));
		setInfo(iv, 47, new ItemStack(Material.BOW), "射箭", String.format("%d级:允许使用RPG弓", UserInfoUtils.getInt(p, "技能射箭等级")));
		setInfo(iv, 48, new ItemStack(Material.LEATHER_LEGGINGS), "轻功", String.format("%d级:减少掉落伤害", UserInfoUtils.getInt(p, "技能轻功等级")));
		setInfo(iv, 49, new ItemStack(Material.IRON_SWORD), "剑气", String.format("%d级:使用怒气，攻击前方敌人:释放方式：右键，左键", UserInfoUtils.getInt(p, "技能剑气等级")));
		setInfo(iv, 50, new ItemStack(Material.BLAZE_ROD), "旋风轮", String.format("%d级:使用怒气，攻击周围敌人并击退:释放方式：右键，左键", UserInfoUtils.getInt(p, "技能旋风轮等级")));
		setInfo(iv, 51, new ItemStack(Material.REDSTONE_TORCH_ON), "打雷", String.format("%d级:大喊天雷滚滚以召唤雷击:然而并没有什么用", UserInfoUtils.getInt(p, "技能打雷等级")));
	}
	
	public static void setInfo(Inventory iv, int slot, ItemStack it, String name, String lore) {
		ItemUtil.applyName(it, name);
		List<String> l = Arrays.asList(lore.split(":"));
		ItemUtil.setLore(it, l);
		iv.setItem(slot, it);
	}
}
