package com.gmail.zhou1992228.rpgsuit.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TargetUtils {
	  public static List<LivingEntity> getLivingTargets(Player player, double range)
	  {
	    List<Entity> list = player.getNearbyEntities(range, range, range);
	    List<LivingEntity> targets = new ArrayList<LivingEntity>();
	    
	    Vector facing = player.getLocation().getDirection();
	    double fLengthSq = facing.lengthSquared();
	    for (Entity entity : list) {
	      if ((isInFront(player, entity)) && ((entity instanceof LivingEntity)))
	      {
	        Vector relative = entity.getLocation().subtract(player.getLocation()).toVector();
	        double dot = relative.dot(facing);
	        double rLengthSq = relative.lengthSquared();
	        double cosSquared = dot * dot / (rLengthSq * fLengthSq);
	        double sinSquared = 1.0D - cosSquared;
	        double dSquared = rLengthSq * sinSquared;
	        if (dSquared < 4.0D) {
	          targets.add((LivingEntity)entity);
	        }
	      }
	    }
	    return targets;
	  }
	  
	  public static LivingEntity getLivingTarget(Player player, double range)
	  {
	    List<LivingEntity> targets = getLivingTargets(player, range);
	    if (targets.size() == 0) {
	      return null;
	    }
	    LivingEntity target = (LivingEntity)targets.get(0);
	    double minDistance = target.getLocation().distanceSquared(player.getLocation());
	    for (LivingEntity entity : targets)
	    {
	      double distance = entity.getLocation().distanceSquared(player.getLocation());
	      if (distance < minDistance)
	      {
	        minDistance = distance;
	        target = entity;
	      }
	    }
	    return target;
	  }
	  
	  public static List<LivingEntity> getConeTargets(Player player, double arc, double range)
	  {
	    List<LivingEntity> targets = new ArrayList<LivingEntity>();
	    List<Entity> list = player.getNearbyEntities(range, range, range);
	    if (arc <= 0.0D) {
	      return targets;
	    }
	    Vector dir = player.getLocation().getDirection();
	    dir.setY(0);
	    double cos = Math.cos(arc * 3.141592653589793D / 180.0D);
	    double cosSq = cos * cos;
	    double dirSq = dir.lengthSquared();
	    for (Entity entity : list) {
	      if ((entity instanceof LivingEntity)) {
	        if (arc >= 360.0D)
	        {
	          targets.add((LivingEntity)entity);
	        }
	        else
	        {
	          Vector relative = entity.getLocation().subtract(player.getLocation()).toVector();
	          relative.setY(0);
	          double dot = relative.dot(dir);
	          double value = dot * dot / (dirSq * relative.lengthSquared());
	          if ((arc < 180.0D) && (dot > 0.0D) && (value >= cosSq)) {
	            targets.add((LivingEntity)entity);
	          } else if ((arc >= 180.0D) && ((dot > 0.0D) || (dot <= cosSq))) {
	            targets.add((LivingEntity)entity);
	          }
	        }
	      }
	    }
	    return targets;
	  }
	  
	  public static boolean isInFront(LivingEntity entity, Entity target)
	  {
	    Vector facing = entity.getLocation().getDirection();
	    Vector relative = target.getLocation().subtract(entity.getLocation()).toVector();
	    
	    return facing.dot(relative) >= 0.0D;
	  }
}
