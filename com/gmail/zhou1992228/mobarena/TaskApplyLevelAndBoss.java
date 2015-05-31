package com.gmail.zhou1992228.mobarena;

import java.util.Random;
import java.util.Set;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

import com.garbagemule.MobArena.framework.Arena;
import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class TaskApplyLevelAndBoss implements Runnable {
	public Random random = new Random();
	public Arena a;
	public int min, max;
	public TaskApplyLevelAndBoss(Arena aa, int mi, int ma) {
		a = aa;
		min = mi;
		max = ma;
	}
	public void run() {
		int wave = a.getWaveManager().getWaveNumber();
		int tot = a.getWaveManager().getFinalWave();
		double lv = (min + (max - min) * (wave * 1.0 / tot));
		int baselv = (int) lv;
		double ulvp = lv - baselv;
		Set<LivingEntity> ml = a.getMonsterManager().getMonsters();
		for (LivingEntity e : ml) {
			if (e instanceof Monster) {
				if (random.nextDouble() > ulvp) {
					RPGSuit.mobGenerator.apply(e, baselv);
				} else {
					RPGSuit.mobGenerator.apply(e, baselv + 1);
				}
			}
		}
	}
}
