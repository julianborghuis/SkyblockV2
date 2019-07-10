package nl.vlcraft.skyblock.guicallback;

import org.bukkit.inventory.ItemStack;

public class SlotItem {

    int slot;
    ItemStack item;
    String callback;

    public SlotItem(int slot, ItemStack item, String callback) {
        this.slot = slot;
        this.item = item;
        this.callback = callback;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
