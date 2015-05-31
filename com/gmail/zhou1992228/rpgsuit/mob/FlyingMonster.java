package com.gmail.zhou1992228.rpgsuit.mob;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public class FlyingMonster {
	private Entity entity = null;
	private EntityType Type;
	private Boolean Landing = Boolean.valueOf(false);
	private static Random rand = new Random();
	private Integer flightLevel = Integer.valueOf(64);
	private Integer flightHeight = Integer.valueOf(5 + rand.nextInt(5));

	public FlyingMonster(Entity e) {
		this.entity = e;
		this.Type = e.getType();
		if (rand.nextInt(3) == 0) {
			this.Landing = Boolean.valueOf(true);
		}
	}

	public EntityType getType() {
		return this.Type;
	}

	public Boolean isDead() {
		return Boolean.valueOf(this.entity.isDead());
	}

	public void Fly() {
		Vector v = this.entity.getVelocity();
		Location loc = this.entity.getLocation();
		if (!this.Landing.booleanValue()) {
			if (rand.nextInt(400) == 0) {
				this.Landing = Boolean.valueOf(true);
			} else {
				updateFlightLevel();
				if ((loc.getY() > 120.0D) && (v.getY() > 0.0D)) {
					v.setY(0);
				}
				if ((rand.nextInt(20) == 0) && (v.getY() < 0.0D)) {
					v.setY(rand.nextDouble() / 3.0D);
				}
				if (loc.getY() - this.flightLevel.intValue() < this.flightHeight
						.intValue() * rand.nextDouble() + 5.0D) {
					v.setY(rand.nextDouble() / 3.0D);
				}
				/*
				if (rand.nextInt(5) == 0) {
					v.setX(loc.getDirection().getX() / 3.0D);
				}
				if (rand.nextInt(5) == 0) {
					v.setZ(loc.getDirection().getZ() / 3.0D);
				}
				*/
				this.entity.setVelocity(v);
				this.entity.setFallDistance(0.0F);
			}
		} else if (rand.nextInt(200) == 0) {
			this.Landing = Boolean.valueOf(false);
			TakeOff();
		}
	}

	public void TakeOff() {
		this.entity
				.setVelocity(new Vector(0.0D, rand.nextDouble() / 3.0D, 0.0D));
	}

	private void updateFlightLevel() {
		Location loc = this.entity.getLocation();
		Integer f = Integer.valueOf(loc.getWorld().getHighestBlockYAt(loc));
		if (f.intValue() > 0) {
			if (this.flightLevel.intValue() - f.intValue() > 12) {
				this.flightLevel = Integer
						.valueOf(this.flightLevel.intValue() - 1);
			} else {
				this.flightLevel = f;
			}
		}
	}
}
