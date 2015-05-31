package com.gmail.zhou1992228.rpgsuit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.zhou1992228.rpgsuit.util.InventoryInfoViewer;

public class InventoryInfoViewerListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent event) {
		if ((event.getInventory().getHolder() instanceof InventoryInfoViewer)) {
			event.getWhoClicked().closeInventory();
			event.setCancelled(true);
		}
	}
}
