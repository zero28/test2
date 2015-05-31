package com.gmail.zhou1992228.rpgsuit.arena;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class Arena implements Runnable {
	static public Random random = new Random();

	static private EntityType GetRandomEntity() {
		double r = random.nextDouble();
		if (r < 0.1) {
			return EntityType.CREEPER;
		}
		if (r < 0.3) {
			return EntityType.SPIDER;
		}
		if (r < 0.6) {
			return EntityType.SKELETON;
		}
		return EntityType.ZOMBIE;
	}

	public Arena(String n, Location p1, Location p2, Location sp) {
		name = n;
		RPGSuit.ins.getServer().getScheduler()
				.scheduleSyncRepeatingTask(RPGSuit.ins, this, 100, 80);
		l1 = p1;
		l2 = p2;
		spawn = sp;
	}

	public String getName() {
		return name;
	}

	public boolean okToJoin() {
		return !running;
	}
	
	public boolean shouldkillmob;
	
	public boolean joinAtLevel(Player p, int l) {
		if (running) {
			return false;
		} else {
			p.teleport(Util.RandomLocationBetween(l1, l2, 5));
			p.setMaximumNoDamageTicks(100);
			starttime = Util.Now();
			player = p;
			running = true;
			startLevel = l;
			shouldApplyLevel = -1;
			shouldkillmob = true;
			round = 0;
			p.sendMessage("准备好就开始了！");
			return true;
		}
	}

	public boolean join(Player p) {
		return joinAtLevel(p, PlayerUtil.getLevel(p));
	}

	@Override
	public void run() {
		if (running) {
			CheckNextRound();
			CheckShouldApplyLevel();
			CheckPlayer();
			CheckFinish();
		} else {
			KickPlayers();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void KickPlayers() {
		for (Player p : RPGSuit.ins.getServer().getOnlinePlayers()) {
			if (PlayerUtil.InArea(p, l1, l2, 5) && !p.isOp()) {
				p.teleport(spawn);
			}
		}
	}

	private void CheckShouldApplyLevel() {
		if (shouldApplyLevel != -1) {
			for (LivingEntity e : player.getWorld().getLivingEntities()) {
				if (e instanceof Monster) {
					if (PlayerUtil.InArea(e.getLocation(), l1, l2)) {
						if (!shouldApplyBoss) {
							RPGSuit.mobGenerator.apply(e, shouldApplyLevel);
						} else {
							RPGSuit.mobGenerator.applyBoss(e, shouldApplyLevel);
							shouldApplyBoss = false;
						}
						
					}
				}
			}
			shouldApplyLevel = -1;
		}
	}

	public void end() {
		if (running) {
			running = false;
			if (player.isOnline()) {
				player.teleport(spawn);
				SendReward();
			}
		}
		killAllMob();
		player = null;
	}

	private void CheckNextRound() {
		if (shouldkillmob) {
			killAllMob();
			shouldkillmob = false;
		}
		if (IsAllMobDead()) {
			++round;
			SpawnNextRound();
		}
	}

	private void SendReward() {
		int exp = 0, money = 0, point = 0;
		for (int i = 1; i < round; ++i) {
			int lv = startLevel - 5 + i;
			if (lv < 0) { lv = 0; }
			exp += MobUtil.exp[lv] * 10;
			money += lv * 6 + 50;
			point += lv;
		}
		player.sendMessage("你得到了奖励 " + "  经验值:" + exp + "  $" + money + "  竞技场积分:" + point);
		UserInfoUtils.giveItems(player, "s:经验值:" + exp + " $" + money + " s:竞技场积分:" + point);
	}

	private void SpawnNextRound() {
		int count = 5 + round / 2;
		shouldApplyLevel = startLevel - 3 + round;
		shouldApplyBoss = (round == 4) || (round == 9);
		if (shouldApplyLevel < 0) {
			shouldApplyLevel = 0;
		}
		for (int i = 0; i < count; ++i) {
			player.getWorld().spawnEntity(Util.RandomLocationBetween(l1, l2, 5),
					GetRandomEntity());
		}
	}

	public boolean isRunning() {
		return running;
	}

	@SuppressWarnings("deprecation")
	void CheckPlayer() {
		if (!player.isOnline()) {
			end();
			return;
		}
		if (!PlayerUtil.InArea(player, l1, l2, 5)) {
			player.sendMessage("你离开了场地");
			end();
		}
		for (Player p : RPGSuit.ins.getServer().getOnlinePlayers()) {
			if (!p.equals(player) && PlayerUtil.InArea(p, l1, l2, 5) && !p.isOp()) {
				p.teleport(spawn);
				p.sendMessage("请勿打扰别人");
			}
		}
	}

	void CheckFinish() {
		if (round == 11) {
			if (player != null) {
				player.sendMessage("恭喜你！");
				end();
				return;
			}
		}
		if (Util.Now() - starttime > 15 * 60 * 1000) {
			if (player != null) {
				player.sendMessage("时间到！");
				end();
				return;
			}
		}
	}
	
	public boolean playerInArena(Player p) {
		return p.equals(player);
	}

	private boolean IsAllMobDead() {
		if (!running) { return true; }
		for (LivingEntity e : player.getWorld().getLivingEntities()) {
			if (e instanceof Monster) {
				if (PlayerUtil.InAreaWithMoreRange(e.getLocation(), l1, l2, 5)) {
					return false;
				}
			}
		}
		return true;
	}

	private void killAllMob() {
		for (LivingEntity e : l1.getWorld().getLivingEntities()) {
			if (e instanceof Monster) {
				if (PlayerUtil.InAreaWithMoreRange(e.getLocation(), l1, l2, 5)) {
					e.remove();
				}
			}
		}
	}

	public Location l1, l2, spawn;
	private boolean running;
	private Player player;
	private String name;
	private int startLevel;
	private int round;
	private long starttime;
	private int shouldApplyLevel;
	private boolean shouldApplyBoss;
}
