package org.lambda.skyblock.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lambda.skyblock.GUI.ShopFarmer;
import org.lambda.skyblock.NPC.RightClickNPC;

public class ClickNPC implements Listener {

    @EventHandler
    public void onClick(RightClickNPC e) {
        Player player = e.getPlayer();
        player.sendMessage("Hello " + player.getName());
        player.openInventory(ShopFarmer.getInventory());
    }

}
