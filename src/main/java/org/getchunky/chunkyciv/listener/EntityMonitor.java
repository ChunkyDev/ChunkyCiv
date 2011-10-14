package org.getchunky.chunkyciv.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.getchunky.chunkyciv.task.ActionTracker;

/**
 * @author dumptruckman
 */
public class EntityMonitor extends EntityListener {

    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent)event;
            if (edbeEvent.getDamager() instanceof Projectile) {
                LivingEntity shooter = ((Projectile)edbeEvent.getDamager()).getShooter();
                if (shooter instanceof Player) {
                    if (event.getEntity() instanceof Player)
                        playerAttackingPlayer((Player) shooter);
                    else
                        playerAttackingMonster((Player) shooter);
                }
            } else if (edbeEvent.getDamager() instanceof Player) {
                if (event.getEntity() instanceof Player)
                    playerAttackingPlayer((Player) edbeEvent.getDamager());
                else
                    playerAttackingMonster((Player) edbeEvent.getDamager());
            }
        }
    }

    public void playerAttackingPlayer(Player player) {
        ActionTracker.trackPlayerAttack(player);
    }

    public void playerAttackingMonster(Player player) {
        ActionTracker.trackMonsterAttack(player);
    }
}
