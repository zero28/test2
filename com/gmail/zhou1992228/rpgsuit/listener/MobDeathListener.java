package com.gmail.zhou1992228.rpgsuit.listener;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.garbagemule.MobArena.framework.Arena;
import com.gmail.zhou1992228.mobarena.MobArenaIntegrator;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.RewardUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class MobDeathListener implements Listener {
	public Random random = new Random();
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Monster) {
			Monster m = (Monster) e.getEntity();
			if (m.getKiller() != null) {
				if (MobUtil.isRPGMob(m)) {
					int level = MobUtil.getLevel(m);
					int totExp = MobUtil.exp[level];
					ArrayList<Player> ps = new ArrayList<Player>();
					//Bukkit.getLogger().info("mob arena null?" + (MobArenaIntegrator.mobArena == null));
					if (MobArenaIntegrator.mobArena != null &&
						MobArenaIntegrator.mobArena.getArenaMaster()
							.getArenaAtLocation(e.getEntity().getLocation()) != null) {
						Arena a = MobArenaIntegrator.mobArena.getArenaMaster()
								.getArenaAtLocation(e.getEntity().getLocation());
						for (Player p : a.getAllPlayers()) {
							if (!a.getSpectators().contains(p)) {
								ps.add(p);
							}
						}
					} else {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (m.getWorld().equals(p.getWorld()) &&
								m.getLocation().distanceSquared(p.getLocation()) <= 2500) {
								ps.add(p);
							}
						}
					}
					if (!ps.isEmpty()) {
						if (MobUtil.isBoss(m)) {
							int perExp = (int) (totExp * 10 * (1.1 - ps.size() / 5.0));
							if (perExp <= 0) perExp = 1;
							double prob = random.nextDouble(); 
							if (prob < 0.4) {
								m.getWorld().dropItemNaturally(m.getLocation(), 
										RewardUtil.getRandomStone(MobUtil.getLevel(m) * 100).it.clone());
							} else if (prob < 0.5) {
								m.getWorld().dropItemNaturally(m.getLocation(), 
										RewardUtil.getRandomArmorWithLevel(MobUtil.getLevel(m) - 5).it.clone());
							} else {
								for (Player p : ps) {
									tryAddMoney((int) (level * 20), p, ps.size());
								}
							}
							for (Player p : ps) {
								PlayerUtil.addMobExp(p, perExp);
							}
						} else {
							int perExp = (int) (totExp * (1.1 - ps.size() / 10.0));
							if (perExp <= 0) perExp = 1;
							for (Player p : ps) {
								PlayerUtil.addMobExp(p, perExp);
								tryAddMoney((int) (level * 1.5), p, ps.size());
							}
						}
					}
				}
			}
		}
	}
	
	private void tryAddMoney(int mobLevel, Player p, int playerCount) {
		int money = (int) (mobLevel * (1.2 - 0.2 * playerCount) + 1);
		if (money <= 0) money = 1;
		AddMoneyIfOk(p, money);
	}
	
	private void AddMoneyIfOk(Player p, int money) {
		int num = (int) UserInfoUtils.getInt(p, "击杀怪物数");
		while (num > 200) {
			money = money * 2 / 3 + 1;
			num -= 200;
		}
		p.sendMessage("你从怪物的身上得到了 " + money + " 米拉");
		UserInfoUtils.add(p, "击杀怪物数", 1);
		UserInfoUtils.giveItems(p, "$" + money);
	}
}
