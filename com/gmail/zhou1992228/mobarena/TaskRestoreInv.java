package com.gmail.zhou1992228.mobarena;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.garbagemule.MobArena.framework.Arena;

public class TaskRestoreInv implements Runnable {
	private Arena arena;
	public TaskRestoreInv(Arena a) {
		arena = a;
	}
	public void run() {
		for (Player p : arena.getAllPlayers()) {
			try {
				arena.getInventoryManager().restoreInv(p);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
