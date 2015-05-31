package com.gmail.zhou1992228.rpgsuit.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.FileUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.ItemUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class QuestDailyTask extends Quest implements Listener {
	public String npcname;
	public int require_level;
	public int max_level;
	public int money_a, money_b;
	public int exp_a;
	public ConfigurationSection config;
	public Random random = new Random();
	public Map<String, String> req_list = new HashMap<String, String>();
	public Map<String, String> display_list = new HashMap<String, String>();
	public ArrayList<String> display, req;
	
	@EventHandler
	public void onMonsterDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Monster) {
			Player p = e.getEntity().getKiller();
			if (p != null && Util.InMobWorld(p)) {
				String req = req_list.get(p.getName());
				if (req != null) {
					if (req.startsWith("m")) {
						if (req.contains(e.getEntityType().name())) {
							if (random.nextInt(3) == 0) {
								if (PlayerUtil.getLevel(p) -
									MobUtil.getLevel((Monster) e.getEntity()) < 5) {
									ItemStack it = ItemUtil.createFromString(req);
									it.setAmount(1);
									p.getWorld().dropItemNaturally(e.getEntity().getLocation(), it);
								}
							}
						}
					}
				}
			}
		}
	}

	public QuestDailyTask(FileConfiguration config, String name) {
		super(name);
		npcname = config.getString("quest." + name + ".npcname");
		require_level = config.getInt("quest." + name + ".require_level", 0);
		max_level = config.getInt("quest." + name + ".max_level", 30);
		money_a = config.getInt("quest." + name + ".money_a", 8);
		money_b = config.getInt("quest." + name + ".money_b", 60);
		exp_a = config.getInt("quest." + name + ".exp_a", 12);
		Set<String> keys = config.getConfigurationSection("quest." + name + ".reqs").getKeys(false);
		display = new ArrayList<String>();
		req = new ArrayList<String>();
		for (String key : keys) {
			String v = config.getString("quest." + name + ".reqs." + key);
			display.add(key);
			req.add(v);
		}
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
		Load();
	}
	
	@Override
	public boolean tryComplete(String npcName, Player p) {
		return true;
	}

	@Override
	public void OnQuestComplete(Player p) {
		Quest.removeQuest(p, name);
		UserInfoUtils.minus(p, RPGSuit.DAILY_TASK_LEFT_COUNT, 1);
	}

	@Override
	public void OnTakingQuest(Player p) {
		if (PlayerUtil.getLevel(p) < require_level) {
			p.sendMessage(npcname + ":接任务需要 " + require_level + " 级，先练练级再来吧");
			return;
		}
		if (PlayerUtil.getLevel(p) > max_level) {
			p.sendMessage(npcname + ":接任务需要 小于" + max_level + " 级，找点难点的任务吧");
			return;
		}
		if (UserInfoUtils.getInt(p, RPGSuit.DAILY_TASK_LEFT_COUNT) <= 0) {
			p.sendMessage("今天没啥事了，明天再来吧");
			return;
		}
		if (Quest.haveQuest(p, name)) {
			if (req_list.containsKey(p.getName())) {
				p.sendMessage("还不快去将我要的东西找回来？" + display_list.get(p.getName()));
				return;
			}
		} else {
			Quest.addQuest(p, name);
		}
		int index = random.nextInt(req.size());
		String require = req.get(index);
		String ori_display = display.get(index);
		String final_require = require;
		String final_display = ori_display;
		if (require.startsWith("m")) {
			if (require.contains(":ID")) {
				final_require = require.replace(":ID", "" + random.nextInt());
			}
		}
		if (require.startsWith("t")) {
			if (require.contains(":LV")) {
				int lv = PlayerUtil.getLevel(p) + random.nextInt(7) - 3;
				final_require = require.replace(":LV", ":LV" + lv);
				final_display = ori_display.replace("%LV%", "LV" + lv);
			}
		}
		req_list.put(p.getName(), final_require);
		display_list.put(p.getName(), final_display);
		p.sendMessage("你去帮我找 " + final_display + " 回来吧");
	}

	@Override
	public boolean OnInteractWith(String npcName, Player p) {
		if (npcName.equals(npcname)) {
			if (req_list.containsKey(p.getName()) &&
				UserInfoUtils.haveRequires(p, req_list.get(p.getName()))) {
				UserInfoUtils.takeItems(p, req_list.get(p.getName()));
				OnQuestComplete(p);
				UserInfoUtils.add(p, "每日任务积分", 1);
				int plv = PlayerUtil.getLevel(p);
				//int getexp = MobUtil.exp[plv] * 12;
				//int money = plv * 8 + 60;
				int getexp = MobUtil.exp[plv] * exp_a;
				int money = plv * money_a + money_b;
				UserInfoUtils.add(p, "经验值", getexp);
				UserInfoUtils.giveItem(p, "$" + money);
				p.sendMessage(npcName + ":辛苦了，这是给你的奖励");
				p.sendMessage("你获得了 " + getexp + " 经验值" + " 和 $" + money);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription(Player p) {
		return "收集" + display_list.get(p.getName()) + "给" + npcname;
	}

	@Override
	public void Save() {
		FileUtil.saveObjectTo(req_list, RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_req_list");
		FileUtil.saveObjectTo(display_list, RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_display_list");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void Load() {
		req_list = (Map<String, String>) FileUtil.readObjectFrom(RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_req_list");
		display_list = (Map<String, String>) FileUtil.readObjectFrom(RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_display_list");
		if (req_list == null) { req_list = new HashMap<String, String>(); }
		if (display_list == null) { display_list = new HashMap<String, String>(); }
	}

	@Override
	public void onCancel(Player p) {
		Quest.removeQuest(p, name);
		UserInfoUtils.minus(p, RPGSuit.DAILY_TASK_LEFT_COUNT, 1);
		p.sendMessage("你取消了你的任务");
	}
}
