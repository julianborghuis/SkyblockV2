package nl.vlcraft.skyblock.objects;

import nl.vlcraft.skyblock.Main;
import nl.vlcraft.skyblock.util.Islands;
import org.bukkit.Location;

import java.util.List;

public class Island {

    private String islandId;
    private Region region;

    public Island(String islandId, Region region) {
        this.islandId = islandId;
        this.region = region;
    }

    // ==== Setters ====

    public void setOwner(String uuid) {
        // Make previous owner mod
        throw new UnsupportedOperationException();
    }

    public void addMod(String uuid) {
        throw new UnsupportedOperationException();
    }

    public void removeMod(String uuid) {
        throw new UnsupportedOperationException();
    }

    public void addMember(String uuid) {
        throw new UnsupportedOperationException();
    }

    public void removeMember(String uuid) {
        throw new UnsupportedOperationException();
    }

    // ==== Getters ====

    public List<String> getIslandPlayers(Main main) {
        return Islands.getIslandPlayers(main, islandId);
    }

    public List<String> getMembers(Main main) {
        return Islands.getIslandMembers(main, islandId);
    }

    public List<String> getMods(Main main) {
        return Islands.getIslandMods(main, islandId);
    }

    public String getOwner(Main main) {
        return Islands.getIslandOwner(main, islandId);
    }

    public Location getHome(Main main) {
        return Islands.getIslandHome(main, islandId);
    }

    public static Island getIsland(String islandId) {
        throw new UnsupportedOperationException();
    }
}
