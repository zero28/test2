package com.gmail.zhou1992228.rpgsuit.quest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.mob.MonsterSkillBase;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class PetCatchListener implements Listener {
	//public static String KEY_PET_CACHER = "π÷ŒÔ«Ú";

	public Map<EntityType, String> name_map = new HashMap<EntityType, String>();
	public PetCatchListener() {
		name_map.put(EntityType.SKELETON, "–°∞◊");
		name_map.put(EntityType.CREEPER, "ø‡¡¶≈¬");
		name_map.put(EntityType.SPIDER, "÷©÷Î");
		name_map.put(EntityType.ZOMBIE, "Ω© ¨");
	}
	public Random random = new Random();
	@EventHandler
	public void onEggHit(ProjectileHitEvent e) {
		if (!Util.InMobWorld(e.getEntity())) return;
		if (e.getEntity().getType() == EntityType.EGG) {
			List<Entity> l = e.getEntity().getNearbyEntities(1, 1, 1);
			for (Entity m : l) {
				if (m instanceof Monster) {
					tryCatchMonster((Monster) m);
					return;
				}
			}
		}
	}
	private void tryCatchMonster(Monster m) {
		double hp_rate = 1 - ((Damageable)m).getHealth() / ((Damageable)m).getMaxHealth();
		List<Entity> ll = m.getNearbyEntities(10, 10, 10);
		Player p = null;
		for (Entity e : ll) {
			if (e instanceof Player) {
				p = (Player) e;
				break;
			}
		}
		double dis_rate;
		if (p == null) {
			dis_rate = 0;
		} else {
			double dis = p.getLocation().distance(m.getLocation());
			dis_rate = 1 - dis / 20.0;
		}
		double suc_rate = (dis_rate * hp_rate / 1.5);
		if (random.nextDouble() < suc_rate) {
			ItemStack it = new ItemStack(Material.EGG);
			if (name_map.containsKey(m.getType())) {
				ItemUtil.addLore(it, name_map.get(m.getType()));
			} else {
				ItemUtil.addLore(it, m.getType().name());
			}
			if (MobUtil.isBoss(m)) {
				ItemUtil.addLore(it, "BOSS");
			}
			ItemUtil.addLore(it, "LV" + MobUtil.getLevel(m));
			for (String skillName : MonsterSkillBase.getSkillList(m)) {
				ItemUtil.addLore(it, skillName);
			}
			m.getWorld().dropItemNaturally(m.getLocation(), it);
			m.remove();
		} else {
			m.setHealth(((Damageable)m).getMaxHealth() - 1);
		}
	}
}
