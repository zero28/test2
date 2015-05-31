package com.gmail.zhou1992228.rpgsuit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;

public class ExternalPluginListener implements Listener {
	@EventHandler
	public void onResCreate(ResidenceCreationEvent e) {
		if (e.getPhysicalArea().getSize() < 5000 && !e.getPlayer().isOp()) {
			e.getPlayer().sendMessage("��С����ز��ܴ���(С��5000��)");
			e.setCancelled(true);
		}
	}
}
