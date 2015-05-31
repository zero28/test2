package com.gmail.zhou1992228.rpgsuit.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;

public class ExplodeArrowListener implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArrowHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Arrow) {
			if (!(((Arrow)e.getEntity()).getShooter() instanceof Player)) return;
			Player p = (Player) ((Arrow)e.getEntity()).getShooter();
			if (p != null) {
				if (ItemUtil.isExplodeBow(p.getItemInHand())) {
					p.getWorld().createExplosion(
							e.getEntity().getLocation().getX(),
							e.getEntity().getLocation().getY(),
							e.getEntity().getLocation().getZ(),
							4.0f, false, false);
					//Bukkit.getPluginManager().callEvent(new EntityExplodeEvent(p, e.getEntity().getLocation(), null, 4));
				}
			}
		}
	}
}
