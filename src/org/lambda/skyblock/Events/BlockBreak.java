package org.lambda.skyblock.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType() != Material.DIAMOND_ORE) {
            System.out.println("Not diamond");
            return;
        }
        e.setCancelled(true);
        ItemStack diamondOre = new ItemStack(b.getType(), 1);
        e.getPlayer().getInventory().addItem(diamondOre);

        e.getBlock().setType(Material.STONE);
        System.out.println("Diamond Broke");
    }

}
