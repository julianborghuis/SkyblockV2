package nl.vlcraft.skyblock.util;

import nl.vlcraft.skyblock.Main;
import nl.vlcraft.skyblock.guicallback.ConfirmCallback;
import nl.vlcraft.skyblock.guicallback.Confirmation;
import nl.vlcraft.skyblock.guicallback.SlotItem;
import nl.vlcraft.skyblock.objects.Island;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static nl.vlcraft.skyblock.util.ItemStackMethods.genItem;

public class InteractionManager {
    public static void openIslandSelectionMenu(Main main, Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§3Mijn eilanden");
        for(int i = 0; i < 9; i++) inv.setItem(i, genItem(Material.STAINED_GLASS_PANE, (short) 7));
        for(int i = 18; i < 26; i++) inv.setItem(i, genItem(Material.STAINED_GLASS_PANE, (short) 7));

        inv.setItem(26, genItem(Material.NETHER_STAR, (short) 0, "§aNieuw eiland maken"));

        List<SlotItem> ownedIslandsItems = Islands.getOwnedIslands(main, player.getUniqueId().toString(), 10);
        if(ownedIslandsItems.size() > 0) inv.setItem(9, genItem(Material.ARROW, (short) 0, "§aJouw eilanden"));

        List<SlotItem> trustedIslandItems = Islands.getTrustedIslands(main, player.getUniqueId().toString(), 12 + ownedIslandsItems.size());
        if(trustedIslandItems.size() > 0) inv.setItem(11 + ownedIslandsItems.size(), genItem(Material.ARROW, (short) 0, "§aVertrouwde eilanden"));

        List<SlotItem> items = ownedIslandsItems;
        items.addAll(trustedIslandItems);
        items.add(new SlotItem(26, inv.getItem(26), "NEW_ISLAND"));

        for(SlotItem item : items) {
            inv.setItem(item.getSlot(), item.getItem());
        }

        player.openInventory(inv);

        new Confirmation(player, new ConfirmCallback() {
            @Override
            public void callback(Player player, String response) {
                if(response.equals("CLOSED")) {
                    player.closeInventory();
                } else if(response.equals("NEW_ISLAND")) {
                    int playerIslandAmount = Islands.getAmountOfAvailableIslands(main, player.getUniqueId().toString());
                    int maxPlayerIslandAmount = 1;

                    if(player.hasPermission("skyblock.maxislands.legende")) maxPlayerIslandAmount = 6;
                    else if(player.hasPermission("skyblock.maxislands.nakker")) maxPlayerIslandAmount = 5;
                    else if(player.hasPermission("skyblock.maxislands.superfan")) maxPlayerIslandAmount = 4;
                    else if(player.hasPermission("skyblock.maxislands.goeiefan")) maxPlayerIslandAmount = 3;
                    else if(player.hasPermission("skyblock.maxislands.fan")) maxPlayerIslandAmount = 2;

                    if(playerIslandAmount >= maxPlayerIslandAmount) {
                        player.closeInventory();
                        player.sendMessage(main.prefix + "Je kunt slechts toegang tot §e" + maxPlayerIslandAmount + " §7eilanden hebben.");
                        if(!player.hasPermission("skyblock.maxislands.legende")) player.sendMessage(main.prefix + "Koop een (hogere) rank voor toegang tot meer eilanden!");
                    } else openIslandCreationMenu(main, player);
                } else {
                    openIslandOwnerPanel(main, response, player);
                }
            }
        }, main, inv, items);
    }

    public static void openIslandCreationMenu(Main main, Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§3Eiland aanmaken");
        for(int i = 0; i < 10; i++) inv.setItem(i, genItem(Material.STAINED_GLASS_PANE, (short) 7));
        for(int i = 17; i < 27; i++) inv.setItem(i, genItem(Material.STAINED_GLASS_PANE, (short) 7));

        List<SlotItem> items = new ArrayList<>();

        // Ugly code lellel i'm lazy
        ItemStack spelerIsland = genItem(Material.WOOL, (short) 8, "§aSpeler");
        inv.setItem(10, spelerIsland);
        items.add(new SlotItem(10, spelerIsland, "spelerIsland"));

        if(player.hasPermission("skyblock.islandtype.2")) {
            ItemStack fanIsland = genItem(Material.WOOL, (short) 4, "§aFan");
            inv.setItem(11, fanIsland);
            items.add(new SlotItem(11, fanIsland, "fanIsland"));
        }

        if(player.hasPermission("skyblock.islandtype.3")) {
            ItemStack goeieFanIsland = genItem(Material.WOOL, (short) 5, "§aGoeie Fan");
            inv.setItem(12, goeieFanIsland);
            items.add(new SlotItem(12, goeieFanIsland, "goeieFanIsland"));
        }

        if(player.hasPermission("skyblock.islandtype.4")) {
            ItemStack superFanIsland = genItem(Material.WOOL, (short) 14, "§aSuper Fan");
            inv.setItem(13, superFanIsland);
            items.add(new SlotItem(13, superFanIsland, "superFanIsland"));
        }

        if(player.hasPermission("skyblock.islandtype.5")) {
            ItemStack nakkerIsland = genItem(Material.WOOL, (short) 10, "§aNakker");
            inv.setItem(14, nakkerIsland);
            items.add(new SlotItem(14, nakkerIsland, "nakkerIsland"));
        }

        if(player.hasPermission("skyblock.islandtype.6")) {
            ItemStack legendeIsland = genItem(Material.WOOL, (short) 1, "§aLegende");
            inv.setItem(15, legendeIsland);
            items.add(new SlotItem(15, legendeIsland, "legendeIsland"));
        }

        player.openInventory(inv);

        new Confirmation(player, new ConfirmCallback() {
            @Override
            public void callback(Player player, String response) {
                Island island = null;
                if(response.equals("CLOSED")) {
                    player.closeInventory();
                } else if(response.equals("spelerIsland")) {
                    island = Skyblock.createNewIsland(main, player, "speler");
                } else if(response.equals("fanIsland")) {
                    island = Skyblock.createNewIsland(main, player, "fan");
                } else if(response.equals("goeieFanIsland")) {
                    island = Skyblock.createNewIsland(main, player, "goeiefan");
                } else if(response.equals("superFanIsland")) {
                    island = Skyblock.createNewIsland(main, player, "superfan");
                } else if(response.equals("nakkerIsland")) {
                    island = Skyblock.createNewIsland(main, player, "nakker");
                } else if(response.equals("legendeIsland")) {
                    island = Skyblock.createNewIsland(main, player, "legende");
                }
                if(island != null) {
                    player.sendMessage(main.prefix + "Eiland aangemaakt!");
                    player.teleport(island.getHome(main).clone().add(0, 2, 0));
                }
            }
        }, main, inv, items);
    }

    public static void openIslandOwnerPanel(Main main, String islandId, Player player) {

    }

    public static void openIslandMemberPanel(Main main, String islandId, Player player) {

    }
}
