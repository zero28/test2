package com.gmail.zhou1992228.mobarena;

import org.bukkit.entity.Player;

public class TaskUpdateInv implements Runnable {
	private Player p;
	public TaskUpdateInv(Player pa) {
		p = pa;
	}
	public void run() {
		p.updateInventory();
	}
}
