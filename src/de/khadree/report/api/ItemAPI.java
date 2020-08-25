package de.khadree.report.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ItemAPI {

    public static ItemStack createItemWithMaterial(Material material, int subid, int amount, String DisplayName) {

        ItemStack itemStack = new ItemStack(material, amount, (short) subid);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(DisplayName);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    public static ItemStack createSkull(int amount, String owner, String displayName) {

        ItemStack is = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setOwner(owner);
        im.setDisplayName(displayName);
        is.setItemMeta(im);

        return is;
    }

    public static ItemStack createItemWithLore(Material material, int subid, int amount, String DisplayName, ArrayList<String> list) {

        ItemStack itemStack = new ItemStack(material, amount, (short) subid);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(list);
        itemMeta.setDisplayName(DisplayName);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    public static ItemStack createUnbreakableItem(Material material, int subid, int amount, String DisplayName) {

        ItemStack itemStack = new ItemStack(material, amount, (short) subid);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(DisplayName);
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    public static ItemStack createItemWithEnchant(Material material, int amount, String displayname, Enchantment enchantment, int level) {

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayname);
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    public static ItemStack createUnbreakableItemWithEnchant(Material material, int subid, int amount, String DisplayName, Enchantment enchantment, int level) {

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(DisplayName);
        itemMeta.addEnchant(enchantment, level, true);
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }


}
