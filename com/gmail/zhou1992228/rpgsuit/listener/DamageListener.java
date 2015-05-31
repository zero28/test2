package com.gmail.zhou1992228.rpgsuit.listener;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGDamageType;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntityManager;
import com.gmail.zhou1992228.rpgsuit.skill.SkillShotArrow;
import com.gmail.zhou1992228.rpgsuit.util.DamageHandler;
import com.gmail.zhou1992228.rpgsuit.util.StatusUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class DamageListener implements Listener{
	
	public static double damageTransfer(double damage, Player p) {
		return damage / PlayerUtil.getHp(p) * 20.0;
	}

	@EventHandler (priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!Util.InMobWorld(e.getEntity())) { return; }
		DamageInternal(e);
	}
	
	@SuppressWarnings("deprecation")
	private void DamageInternal(EntityDamageByEntityEvent e) {
		if (e.getDamage() <= 0) {
			e.setDamage(-e.getDamage());
			return;
		}
		if (e.getDamager() instanceof LivingEntity &&
			e.getEntity() instanceof LivingEntity) {
			if (RPGEntityManager.Damage((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), RPGDamageType.NORMAL)) {
				e.setDamage(0.0);
				return;
			}
		}
		boolean is_arrow_damage = false;
		if (e.getDamager() instanceof Arrow) {
			ProjectileSource ps = ((Projectile) e.getDamager()).getShooter();
			if (ps instanceof LivingEntity) {
				boolean could_shot = true;
				if (ps instanceof Player) {
					could_shot = false;
					if (StatusUtil.HasStatus((Player) ps, StatusUtil.Status.CAN_SHOT_ARROW) ||
						SkillShotArrow.ins.tryActive((Player) ps)) {
						could_shot = true;
					}
				}
				if (could_shot) {
					if (RPGEntityManager.Damage((LivingEntity) ps, (LivingEntity)e.getEntity(), RPGDamageType.ARROW_NORMAL)) {
						e.setDamage(0.0);
						return;
					}
				}
			}
		}
		long str;
		LivingEntity attacker;
		LivingEntity defender;
		defender = (LivingEntity) e.getEntity();
		if (e.getDamager() instanceof Arrow) {
			ProjectileSource ps = ((Projectile) e.getDamager()).getShooter();
			if (ps instanceof Monster) {
				str = getStr((Entity) ps);
				attacker = (LivingEntity) ps;
			} else if (ps instanceof Player) {
				if (StatusUtil.HasStatus((Player) ps, StatusUtil.Status.CAN_SHOT_ARROW) ||
					SkillShotArrow.ins.tryActive((Player) ps)) {
					is_arrow_damage = true;
					str = PlayerUtil.getStr((OfflinePlayer) ps);
					attacker = (LivingEntity) ps;
				} else {
					return;
				}
			} else {
				return;
			}
		} else {
			if (e.getDamager() instanceof Player) {
				if (((Player)e.getDamager()).getItemInHand().getType() == Material.BOW) {
					return;
				} else {
					attacker = (LivingEntity) e.getDamager();
					str = getStr(e.getDamager());
				}
			} else if (e.getDamager() instanceof LivingEntity) {
				attacker = (LivingEntity) e.getDamager();
				str = getStr(e.getDamager());
			} else {
				return;
			}
		}
		if (str == -1) return;
		long def = getDef(e.getEntity());
		if (def == -1) return;
		double damage = (str - def) * 4;
		if (damage < 0) { damage = 0; }
		if (e.getEntity() instanceof Player) {
			damage = damageTransfer(damage, (Player) e.getEntity());
		}
		if (holyDamage(e.getEntity(), damage)) {
			e.setDamage(0.0);
		} else {
			e.setDamage(damage);
		}
		if (defender instanceof Player) {
			DamageHandler.GetCPWithDamaged((Player) defender, damage);
		}
		if (attacker instanceof Player && !is_arrow_damage) {
			DamageHandler.GetCPWithDamage((Player) attacker, defender, damage);
		}
		if (damage > 0 && is_arrow_damage && RPGSuit.flyingManager.isFlying(defender)) {
			RPGSuit.flyingManager.removeEntity(defender);
		}
	}
	
	private boolean holyDamage(Entity e, double damage) {
		if (e instanceof Player) {
			Player p = (Player) e;
			if (((Damageable) p).getHealth() < damage) {
				p.setHealth(0.0);
			} else {
				p.setHealth(((Damageable) p).getHealth() - damage);
			}
			return true;
		}
		return false;
	}

	public static long getStr(Entity e) {
		if (e instanceof Monster) {
			return MobUtil.getStr((Monster) e);
		} else if (e instanceof Player) {
			return PlayerUtil.getStr((Player) e);
		}
		return -1;
	}
	public static long getDef(Entity e) {
		if (e instanceof Monster) {
			return MobUtil.getDef((Monster) e);
		} else if (e instanceof Player) {
			return PlayerUtil.getDef((Player) e);
		}
		return -1;
	}
}
