package com.gmail.zhou1992228.rpgsuit.mob;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyingManager
{
	JavaPlugin plugin;
  private HashMap<Entity, FlyingMonster> Entities = new HashMap<Entity, FlyingMonster>();
  
  public FlyingManager(JavaPlugin p)
  {
    this.plugin = p;
    
    this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable()
    {
      public void run()
      {
    	  FlyingManager.this.runLoop();
      }
    }, 1L, 3L);
  }
  
  public void runLoop()
  {
    Iterator<FlyingMonster> itr = this.Entities.values().iterator();
    while (itr.hasNext())
    {
    	FlyingMonster entity = (FlyingMonster)itr.next();
      if (entity.isDead().booleanValue()) {
        itr.remove();
      } else {
        entity.Fly();
      }
    }
  }
  
  public void addEntity(Entity entity)
  {
    if (!isFlying(entity).booleanValue())
    {
    	FlyingMonster flyer = new FlyingMonster(entity);
      this.Entities.put(entity, flyer);
      

      flyer.TakeOff();
    }
  }
  
  public void removeEntity(Entity entity)
  {
    if (isFlying(entity).booleanValue()) {
      this.Entities.remove(entity);
    }
  }
  
  public Boolean isFlying(Entity entity)
  {
    if (this.Entities.containsKey(entity)) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
}
