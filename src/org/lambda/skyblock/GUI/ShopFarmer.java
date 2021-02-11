package org.lambda.skyblock.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopFarmer implements Listener {

    private static Inventory inventory;

    public static Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().contains("Farmer"))
            return;
        if (e.getCurrentItem() == null)
            return;
        if (e.getCurrentItem().getItemMeta() == null)
            return;

        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);

        if (e.getClickedInventory().getType() == InventoryType.PLAYER)
            return;

        if (e.getSlot() == 4) {
            ItemStack item = new ItemStack(Material.WHEAT, 5);
            player.getInventory().addItem(item);
            player.closeInventory();
            player.updateInventory();
        }
    }

    public static void createStore() {
        GUI shopGUI = new GUI(9, "Farmer", "RED");
        Inventory guiInv = shopGUI.getInventory();
        inventory = guiInv;
    }

}

