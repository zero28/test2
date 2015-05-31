package com.gmail.zhou1992228.rpgsuit.listener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.task.TaskArmorValidator;

public class ItemDamageListener implements Listener {
	private Random random = new Random();

	public Map<String, Long> cooldown = new HashMap<String, Long>();
	public static int breakChance = 20;
	@EventHandler
	public void onUseMed(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand() != null &&
			e.getPlayer().getItemInHand().getItemMeta() != null &&
			e.getPlayer().getItemInHand().getItemMeta().getLore() != null) {
			List<String> lore = e.getPlayer().getItemInHand().getItemMeta().getLore();
			for (String l : lore) {
			if (l.startsWith("回复生命值")) {
				if (cooldown.containsKey(e.getPlayer().getName()) &&
					cooldown.get(e.getPlayer().getName()) > Calendar.getInstance().getTimeInMillis()/1000 - 3) {
					e.getPlayer().sendMessage("不要当吃药是吃饭啊，一把一把的吃啊");
					return;
				}
				int r = Integer.parseInt(l.split(":")[1]);
				e.getPlayer().setHealth(Math.min(20.0, 
						((Damageable)e.getPlayer()).getHealth() + DamageListener.damageTransfer(r, e.getPlayer())));
				if (e.getPlayer().getItemInHand().getAmount() == 1) {
					e.getPlayer().setItemInHand(null);
				} else {
					e.getPlayer().getItemInHand().setAmount(
						e.getPlayer().getItemInHand().getAmount() - 1);
				}
				cooldown.put(e.getPlayer().getName(), Calendar.getInstance().getTimeInMillis()/1000);
			}
			}
		}
	}
	@EventHandler
	public void onHandItemBreak(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand() != null &&
			e.getPlayer().getItemInHand().getType() != Material.BOW && 
			TaskArmorValidator.isAuthorizedArmor(e.getPlayer().getItemInHand())) {
			if (random.nextInt((int) (breakChance * 1.5)) == 0) {
				e.getPlayer().setItemInHand(ItemUtil.RPGDamage(e.getPlayer(), e.getPlayer().getItemInHand()));
			}
		}
	}
	
	@EventHandler
	public void onArmorDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (random.nextInt(breakChance) == 0) {
				((Player)e.getEntity()).getInventory().setHelmet(
						ItemUtil.RPGDamage((Player)e.getEntity(), ((Player)e.getEntity()).getInventory().getHelmet()));
			}
			if (random.nextInt(breakChance) == 0) {
				((Player)e.getEntity()).getInventory().setChestplate(
						ItemUtil.RPGDamage((Player)e.getEntity(), ((Player)e.getEntity()).getInventory().getChestplate()));
			}
			if (random.nextInt(breakChance) == 0) {
				((Player)e.getEntity()).getInventory().setLeggings(
						ItemUtil.RPGDamage((Player)e.getEntity(), ((Player)e.getEntity()).getInventory().getLeggings()));
			}
			if (random.nextInt(breakChance) == 0) {
				((Player)e.getEntity()).getInventory().setBoots(
						ItemUtil.RPGDamage((Player)e.getEntity(), ((Player)e.getEntity()).getInventory().getBoots()));
			}
		}
	}
	
	@EventHandler
	public void onBowDamage(EntityShootBowEvent e) {
		if (e.getEntity() instanceof Player) {
			if (random.nextInt(breakChance) == 0) {
					((Player)e.getEntity()).setItemInHand(
							ItemUtil.RPGDamage((Player)e.getEntity(), ((Player)e.getEntity()).getItemInHand()));
			}
		}
	}
}
