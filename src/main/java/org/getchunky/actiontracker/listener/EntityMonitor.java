package org.getchunky.actiontracker.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.getchunky.actiontracker.task.ActionTracker;

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

                    if (event.getEntity() instanceof Player)
                        playerAttackedByPlayer((Player)event.getEntity());
                }
                if (shooter instanceof Monster) {
                    if (event.getEntity() instanceof Player)
                        playerAttackedByMonster((Player)event.getEntity());
                }
            } else if (edbeEvent.getDamager() instanceof Player) {
                if (event.getEntity() instanceof Player)
                    playerAttackingPlayer((Player) edbeEvent.getDamager());
                else
                    playerAttackingMonster((Player) edbeEvent.getDamager());

                if (event.getEntity() instanceof Player)
                    playerAttackedByPlayer((Player)event.getEntity());
            } else if (edbeEvent.getDamager() instanceof Monster) {
                if (event.getEntity() instanceof Player)
                    playerAttackedByMonster((Player)event.getEntity());
            }
        }
    }

    private void playerAttackingPlayer(Player player) {
        ActionTracker.trackPlayerAttack(player);
    }

    private void playerAttackingMonster(Player player) {
        ActionTracker.trackMonsterAttack(player);
    }

    private void playerAttackedByMonster(Player player) {
        //ActionTracker.trackAttackedByMonster(player);
    }

    private void playerAttackedByPlayer(Player player) {
        ActionTracker.trackAttackedByPlayer(player);
    }
}
