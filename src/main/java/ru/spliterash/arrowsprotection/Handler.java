package ru.spliterash.arrowsprotection;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class Handler implements Listener {
    private final ArrowsProtection plugin;

    public Handler(ArrowsProtection plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSmash(PlayerInteractEvent e) {
        Action action = e.getAction();
        switch (action) {
            case LEFT_CLICK_BLOCK:
            case LEFT_CLICK_AIR:
                break;
            default:
                return;
        }
        ItemStack item = e.getItem();
        if (item == null)
            return;
        if (!plugin.getItems().contains(item.getType()))
            return;
        Player p = e.getPlayer();
        Location loc = p.getEyeLocation();
        double distance = plugin.getDistance();
        for (Entity entity : loc.getWorld().getNearbyEntities(loc, distance, distance, distance)) {
            if (entity instanceof Arrow) {
                Arrow a = (Arrow) entity;
                if (a.isOnGround())
                    continue;
                if (a.isDead())
                    continue;
                if (a.isInBlock())
                    continue;
                Vector v = a.getVelocity();
                Vector shootVelocity = v.multiply(-1);
                ProjectileSource shooter = a.getShooter();
                Location aLoc = a.getLocation();
                a.setVelocity(shootVelocity);
                a.setShooter(p);
                loc.getWorld().playSound(aLoc, Sound.BLOCK_GLASS_BREAK, 1F, 2F);
                item.setDurability((short) (1 + item.getDurability()));
            }
        }
    }

}
