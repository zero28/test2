package com.gmail.zhou1992228.rpgsuit.player;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemProperty;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.task.TaskArmorValidator;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class PlayerUtil {
	
	public static long[] levelExp, maxExpGet;
	public static int expA, expB, expC, expD;
	public static double av1, av2, bv1, bv2;
	public static double strv1, strv2, defv1, defv2, hpv1, hpv2;
	public static Ranking rank = new Ranking();
	public static void init() {
		levelExp = new long[155];
		maxExpGet = new long[155];
		for (int i = 1; i < 153; ++i) {
			levelExp[i] = levelExp[i - 1] + MobUtil.exp[i] * mobCountToLevelUp(i);
			maxExpGet[i] = levelExp[i] / mobMinCountToLevelUp(i);
		}
	}	
	
	public static int getAbility(OfflinePlayer p) {
		int lv = PlayerUtil.getLevel(p);
		int hp = (int) PlayerUtil.getHp(p);
		int atk = (int) PlayerUtil.getStr(p);
		int def = (int) PlayerUtil.getDef(p);
		return getAbility(lv, hp, atk, def);
	}
	
	public static int getAbility(int lv, long hp, long str, long def) {
		int ret = 0;
		ret += lv * lv;
		ret += str * 10;
		ret += def * 12;
		ret += hp * 3;
		ret = (int) Math.pow(ret, 0.88);
		return ret;
	}
	
	public static int mobCountToLevelUp(int level) {
		return level / 40 * 400 + level / 20 * 200 + level / 10 * 50 + level / 5 * 10 + level * 5 + 5;
	}
	
	public static int mobMinCountToLevelUp(int level) {
		return mobCountToLevelUp(level) / 3 + 7;
	}
	
	public static void giveRPGItem(Player p, ItemProperty ip) {
		p.getInventory().addItem(ItemProperty.makeItem(ip));
	}
	
	public static void addMobExp(Player p, int exp) {
		exp = (int) Math.min(exp, maxExpGet[getLevel(p)]);
		if (UserInfoUtils.getInt(p, RPGSuit.DOUBLE_EXP_END_TIME) > RPGSuit.now()) {
			exp = exp * 2;
		}
		addExp(p, exp);
	}
	
	public static void addExp(OfflinePlayer p, int exp) {
		UserInfoUtils.add(p, "经验值", exp);
	}

	public static void updateUserInfo(Player p) {
		// Basic Infos
		long exp = UserInfoUtils.getInt(p, "经验值");
		int level = getExpLevel(exp);
		long hp = getLevelHp(level) + UserInfoUtils.getInt(p, "额外生命值");
		long str = getLevelStr(level) + UserInfoUtils.getInt(p, "额外攻击力");
		long def = getLevelDef(level) + UserInfoUtils.getInt(p, "额外防御力");
		String special = "";
		for (ItemStack is : p.getEquipment().getArmorContents()) {
			ItemProperty ip = ItemProperty.getProperty(is);
			if (ip != null && level >= ip.level) {
				hp += ip.hp;
				str += ip.str;
				def += ip.def;
				special += ip.special;
			}
		}
		ItemProperty ip = ItemProperty.getProperty(p.getItemInHand());
		if (ip == null ||
			ip.str <= 0 ||
			level < ip.level ||
			p.getItemInHand().getType().name().contains("HELMET") ||
			p.getItemInHand().getType().name().contains("CHESTPLATE") ||
			p.getItemInHand().getType().name().contains("LEGGINGS") ||
			p.getItemInHand().getType().name().contains("BOOTS")) {
			str = 0;
		} else {
			if (ip != null) {
				hp += ip.hp;
				str += ip.str;
				def += ip.def;
				special += ip.special;
			}
		}
		
		// Skill Modifiers
		
		
		
		UserInfoUtils.set(p, "等级", level);
		UserInfoUtils.set(p, "生命值", hp);
		UserInfoUtils.set(p, "攻击力", str);
		UserInfoUtils.set(p, "防御力", def);
		UserInfoUtils.set(p, "特效", special);
		UserInfoUtils.set(p, "战斗力", Math.max(getAbility(level, hp, str,def),
									 UserInfoUtils.getInt(p, "战斗力")));
		rank.Update(p.getName(), (Long) UserInfoUtils.get(p, "战斗力"));
		tryRepairRPGItems(p);
	}
	static private void tryRepairRPGItems(Player p) {
		if (TaskArmorValidator.isAuthorizedArmor(p.getItemInHand())) {
			if (p.getItemInHand().getDurability() > 16) {
				p.getItemInHand().setDurability((short)16);
			}
		}
		for (ItemStack it : p.getInventory().getArmorContents()) {
			if (TaskArmorValidator.isAuthorizedArmor(it)) {
				if (it.getDurability() > 16) {
					it.setDurability((short)16);
				}
			}
		}
		return;
	}

	public static int getExpLevel(long exp) {
		int l = 0, r = 150, m;
		while (l + 1 < r) {
			m = (l + r) / 2;
			if (levelExp[m] >= exp) {
				r = m;
			} else {
				l = m;
			}
		}
		return r;
	}

	public static int getLevelHp(int level) {
		int a = (int) ((level + av1) * (level + av2));
		return (int) (hpv1 * a + hpv2);
	}

	public static int getLevelStr(int level) {
		int b = (int) ((level + bv1) * (level + bv2));
		return (int) (strv1 * b + strv2);
	}
	
	public static int getLevelDef(int level) {
		int b = (int) ((level + bv1) * (level + bv2));
		return (int) (defv1 * b + defv2);
	}
	
	public static int getLevel(OfflinePlayer p) {
		return (int) UserInfoUtils.getInt(p, "等级");
	}

	public static long getStr(OfflinePlayer p) {
		return UserInfoUtils.getInt(p, "攻击力");
	}

	public static long getDef(OfflinePlayer p) {
		return UserInfoUtils.getInt(p, "防御力");
	}

	public static long getHp(OfflinePlayer p) {
		return UserInfoUtils.getInt(p, "生命值");
	}
	
	public static boolean InArea(Location p, Location l1, Location l2) {
		return ((l1.getBlockX() <= p.getBlockX() && p.getBlockX() <= l2.getBlockX()) ||
			    (l2.getBlockX() <= p.getBlockX() && p.getBlockX() <= l1.getBlockX())) &&
			   ((l1.getBlockY() <= p.getBlockY() && p.getBlockY() <= l2.getBlockY()) ||
				(l2.getBlockY() <= p.getBlockY() && p.getBlockY() <= l1.getBlockY())) &&
			   ((l1.getBlockZ() <= p.getBlockZ() && p.getBlockZ() <= l2.getBlockZ()) ||
				(l2.getBlockZ() <= p.getBlockZ() && p.getBlockZ() <= l1.getBlockZ()));
	}
	public static boolean InAreaWithMoreRange(Location p, Location l1, Location l2, int extraRange) {
		return ((l1.getBlockX() - extraRange <= p.getBlockX() && p.getBlockX() <= l2.getBlockX() + extraRange) ||
			    (l2.getBlockX() - extraRange <= p.getBlockX() && p.getBlockX() <= l1.getBlockX() + extraRange)) &&
			   ((l1.getBlockY() - extraRange <= p.getBlockY() && p.getBlockY() <= l2.getBlockY() + extraRange) ||
				(l2.getBlockY() - extraRange <= p.getBlockY() && p.getBlockY() <= l1.getBlockY() + extraRange)) &&
			   ((l1.getBlockZ() - extraRange <= p.getBlockZ() && p.getBlockZ() <= l2.getBlockZ() + extraRange) ||
				(l2.getBlockZ() - extraRange <= p.getBlockZ() && p.getBlockZ() <= l1.getBlockZ() + extraRange));
	}

	public static boolean InArea(Player p, Location l1, Location l2, int extraRange) {
		return InAreaWithMoreRange(p.getLocation(), l1, l2, extraRange);
	}
	
	public static boolean InArea(Player p, Location l1, Location l2) {
		return InArea(p.getLocation(), l1, l2);
	}
}
