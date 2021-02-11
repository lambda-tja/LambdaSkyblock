package org.lambda.skyblock.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemGUI {

    private String material;
    private String name;
    private String nameColour;
    private String[][] lore;
    private String[] enchantments;
    private boolean hideEnchantmments;

    private ItemStack itemStack;

    public ItemGUI(String material, String name, String nameColour, String[][] lore, String[] enchantments, boolean hideEnchantmments) {
        // TODO NULL CHECKS
        //

        // Set item icon and name
        itemStack = new ItemStack(Material.valueOf(material));
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.valueOf(nameColour) + name);
        // Set Lore
        List<String> loreList = new ArrayList<>();
        for (String[] loreItem : lore) {
            loreList.add(ChatColor.valueOf(loreItem[0]) + loreItem[1]);
        }
        meta.setLore(loreList);
        // Set Enchantments
        for (String enchantment : enchantments) {
            // TODO ENCHANTMENTS LEVELS and NULL HANDLINGs
            meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchantment)), 1, true);
        }
        if (hideEnchantmments) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        // Finish
        itemStack.setItemMeta(meta);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
