package org.lambda.skyblock.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUI  implements Listener {

    private Inventory inventory;

    private int invSize;
    private String invTitle;
    private String invTitleColour;

    public Inventory getInventory() {
        return inventory;
    }

    public GUI(int size, String title, String colour) {
        inventory = Bukkit.createInventory(null, size, ChatColor.valueOf(colour) + title);
        String[][] lore = {{"BLACK", "I am the law!"}};
        String[] enchants = {};
        ItemGUI itemGUI = new ItemGUI("WHEAT", "Special Wheat", "BLUE", lore, enchants, true);
        ItemStack wheat = itemGUI.getItemStack();
        inventory.setItem(4, wheat);
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

}
