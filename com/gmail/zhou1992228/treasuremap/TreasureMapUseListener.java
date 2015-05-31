package com.gmail.zhou1992228.treasuremap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TreasureMapUseListener implements Listener {
	@EventHandler
	public void onPlayerUseTreasureMap(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR ||
			e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			TreasureMapUtils.tryDigTreasureMap(e.getPlayer());
		}
	}
}
