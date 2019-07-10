package nl.vlcraft.skyblock.events;

import nl.vlcraft.skyblock.Main;
import nl.vlcraft.skyblock.objects.Region;
import nl.vlcraft.skyblock.util.RegionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class RegionEvents implements Listener {

    private Main main;

    public RegionEvents(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        Region playerRegion = main.playerRegion.get(player);

        // Check ender pearl / chorus fruit teleports
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL || e.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
            if(playerRegion != null) {
                if(!RegionManager.isInRegion(playerRegion, e.getTo())) e.setCancelled(true);
            } else e.setCancelled(true);
            return;
        }

        Region newRegion = RegionManager.determineRegion(main, e.getTo());
        if(playerRegion != null) player.sendMessage("Old: x=" + playerRegion.getCenterX() + ", z=" + playerRegion.getCenterZ());
        else player.sendMessage("Old: none");

        if(newRegion != null) {
            player.sendMessage("New: x=" + newRegion.getCenterX() + ", z=" + newRegion.getCenterZ());
            main.playerRegion.put(player, newRegion);
        } else {
            main.playerRegion.remove(player);
            player.sendMessage("New: none");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Region region = main.playerRegion.get(player);
        if(region != null) {
            if(e.getTo().getX() > region.getMaxX() || e.getTo().getX() < region.getMinX() ||
                    e.getTo().getZ() > region.getMaxZ() || e.getTo().getZ() < region.getMinZ()) {
                player.teleport(e.getFrom());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        main.playerRegion.remove(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Region region = RegionManager.determineRegion(main, e.getPlayer().getLocation());
        if(region != null) main.playerRegion.put(e.getPlayer(), region);
    }
}
