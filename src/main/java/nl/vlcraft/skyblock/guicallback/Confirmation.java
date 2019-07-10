package nl.vlcraft.skyblock.guicallback;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Confirmation implements Listener {

    private Player player;
    private ConfirmCallback callback;
    private JavaPlugin plugin;
    private Inventory inventory;
    private List<SlotItem> items;
    private boolean callbackSent;

    public Confirmation(Player player, ConfirmCallback callback, JavaPlugin plugin, Inventory inventory, List<SlotItem> items) {
        this.player = player;
        this.callback = callback;
        this.plugin = plugin;
        this.inventory = inventory;
        this.items = items;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().equals(inventory)) {
            e.setCancelled(true);
            for(SlotItem item : items) {
                if(item.getSlot() == e.getSlot()) {
                    callback.callback((Player) e.getWhoClicked(), item.callback);
                    callbackSent = true;
                }
            }
            player.updateInventory();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getInventory().equals(inventory)) {
            HandlerList.unregisterAll(this);

            if(!callbackSent) callback.callback((Player) e.getPlayer(), "CLOSED");
        }
    }
}
