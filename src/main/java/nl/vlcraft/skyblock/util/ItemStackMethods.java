package nl.vlcraft.skyblock.util;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackMethods {

    public static ItemStack genItem(Material material, short durability) {
        return new ItemStack(material, 1, durability);
    }

    public static ItemStack genItem(Material material, short durability, String displayName) {
        ItemStack is = new ItemStack(material, 1, durability);;
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(displayName);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack getPlayerHead(OfflinePlayer player) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setOwner(player.getName());
        is.setItemMeta(im);
        return is;
    }
}
