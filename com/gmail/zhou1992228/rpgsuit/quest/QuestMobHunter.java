package com.gmail.zhou1992228.rpgsuit.quest;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.treasuremap.TreasureMapUtils;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class QuestMobHunter extends Quest implements Listener {
	public String npcname;
	public int maxx, minx, maxz, minz, require_level;
	public String worldName;
	public double base_drop_pob, map_pob;
	public Map<String, Integer> key_list = new HashMap<String, Integer>();
	public Map<String, Location> loc_list = new HashMap<String, Location>();

	public QuestMobHunter(FileConfiguration config, String name) {
		super(name);
		npcname = config.getString("quest." + name + ".npcname");
		worldName = config.getString("quest." + name + ".worldname");
		maxx = config.getInt("quest." + name + ".maxx");
		minx = config.getInt("quest." + name + ".minx");
		maxz = config.getInt("quest." + name + ".maxx");
		minz = config.getInt("quest." + name + ".minz");
		require_level = config.getInt("quest." + name + ".require_level", 15);
		base_drop_pob = config.getDouble("quest." + name + ".base_drop_pob", 0.2);
		map_pob = config.getDouble("quest." + name + ".map_pob");
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
		Load();
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		if (e.getEntity().getKiller() != null && e.getEntity() instanceof Monster) {
			if (Quest.haveQuest(e.getEntity().getKiller(), name)) {
				if (e.getEntity().getWorld().getName().equals(worldName)) {
					if (loc_list.get(e.getEntity().getKiller().getName()) != null &&
						e.getEntity().getLocation().distanceSquared(
						loc_list.get(e.getEntity().getKiller().getName())) < 40000) {
						int mob_lev = MobUtil.getLevel((Monster) e.getEntity());
						int p_lev = PlayerUtil.getLevel(e.getEntity().getKiller()); 
						double pob_modifier = 
								(Math.min(mob_lev, p_lev) - require_level) /
								(180.5 - require_level)
								* 0.4;
						if (random.nextDouble() < base_drop_pob + pob_modifier) {
							dropMessage(e);
						}
					}
				}
			}
		}
	}
	
	private void dropMessage(EntityDeathEvent e) {
		ItemStack it = new ItemStack(Material.PAPER);
		ItemUtil.addLore(it, "����Ϯ����ͨ������鱨#" + key_list.get(e.getEntity().getKiller().getName()));
		e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), it);
		e.getEntity().getKiller().sendMessage("��ӹ��������ҵ������ǽ�������������鱨��");
	}

	@Override
	public boolean tryComplete(String npcName, Player p) {
		Quest.removeQuest(p, name);
		if (random.nextDouble() < map_pob) {
			p.sendMessage(npcname + ":��������ǰ�ҵ��Ĳر�ͼ���������Ѿ��Ҳ����ˣ����͸����");
			p.sendMessage("������һ�Ųر�ͼ��");
			int x = minx + random.nextInt(maxx - minx);
			int z = minz + random.nextInt(maxz - minz);
			Location loc = new Location(RPGSuit.ins.getServer().getWorld(worldName), x, 64, z);
			p.getInventory().addItem(TreasureMapUtils.mapMaker("�ر�ͼ", loc, PlayerUtil.getLevel(p)));
		} else {
			p.sendMessage(npcname + ":лл�����Ǹ���ı���");
			int money = 100 + random.nextInt(200);
			p.sendMessage("������$" + money);
			UserInfoUtils.giveItem(p, "$" + money);
		}
		return true;
	}

	@Override
	public void OnQuestComplete(Player p) {
	}

	@Override
	public void OnTakingQuest(Player p) {
		if (PlayerUtil.getLevel(p) < require_level) {
			p.sendMessage(npcname + ":��������Ҫ " + require_level + " ������������������");
			return;
		}
		if (Quest.haveQuest(p, name)) {
			if (loc_list.containsKey(p.getName())) {
				Location loc = loc_list.get(p.getName());
				p.sendMessage("������ȥ " + loc.getBlockX() + "," + loc.getBlockZ() + " �ռ��鱨����");
				return;
			}
		} else {
			Quest.addQuest(p, name);
		}
		Location loc = null;
		while (true) {
			int x = minx + random.nextInt(maxx - minx);
			int z = minz + random.nextInt(maxz - minz);
			loc = new Location(RPGSuit.ins.getServer().getWorld(worldName), x, 62, z);
			loc.getChunk().load();
			if (loc.getBlock().getType() == Material.STATIONARY_WATER ||
				loc.getBlock().getType() == Material.WATER) {
				loc = null;
			}
			if (loc != null) break;
		}
		loc_list.put(p.getName(), loc);
		key_list.put(p.getName(), random.nextInt(Integer.MAX_VALUE));
		p.sendMessage("�ڹ�������� " + loc.getBlockX() + ", " + loc.getBlockZ()
						+ " ������Щ����׼��Ϯ��������磬��ȥ�������ǻ�ȡһЩ�鱨������������׼����");
	}

	@Override
	public boolean OnInteractWith(String npcName, Player p) {
		if (npcName.equals(npcName)) {
			ItemStack it = p.getItemInHand();
			if (it != null && it.getItemMeta() != null && it.getItemMeta().getLore() != null) {
				if (it.getItemMeta().getLore().get(0).equals("����Ϯ����ͨ������鱨#" + key_list.get(p.getName()))) {
					p.setItemInHand(null);
					tryComplete(npcName, p);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getDescription(Player p) {
		Location loc = loc_list.get(p.getName());
		if (loc == null) return null;
		return "ȥ " + loc.getBlockX() + "," + loc.getBlockZ() + " �����ռ��鱨";
	}

	@Override
	public void Save() {/*
		FileUtil.saveObjectTo(key_list, RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_key_list");
		FileUtil.saveObjectTo(loc_list, RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_loc_list");*/
	}

	@Override
	public void Load() {/*
		key_list = (Map<String, Integer>) FileUtil.readObjectFrom(RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_key_list");
		loc_list = (Map<String, Location>) FileUtil.readObjectFrom(RPGSuit.ins.getDataFolder().getPath()
				+ "/questinfo/" + this.name + "_" + this.npcname + "_loc_list");
		if (key_list == null) { key_list = new HashMap<String, Integer>(); }
		if (loc_list == null) { loc_list = new HashMap<String, Location>(); }*/
	}

	@Override
	public void onCancel(Player p) {
		Quest.removeQuest(p, name);
	}
}
