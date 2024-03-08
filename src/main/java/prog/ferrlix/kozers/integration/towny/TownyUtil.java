package prog.ferrlix.kozers.integration.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyPermission;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import prog.ferrlix.kozers.Kozers;

import java.util.*;

/**
 * Contains many helper methods for using the Towny API
 */
public class TownyUtil {
    /**
     * static TownyAPI instance
     */
    public static TownyAPI townyApi = TownyAPI.getInstance();

    private @NotNull Map<TownyPermission.PermLevel, TownyPermission.ActionType> getTownyPermissions(){
        ArrayList<TownyPermission.ActionType> actionTypes = new ArrayList<>();
        actionTypes.add(TownyPermission.ActionType.ITEM_USE);
        actionTypes.add(TownyPermission.ActionType.BUILD);
        actionTypes.add(TownyPermission.ActionType.DESTROY);
        actionTypes.add(TownyPermission.ActionType.SWITCH);
        ArrayList<TownyPermission.PermLevel> permLevels = new ArrayList<>();
        permLevels.add(TownyPermission.PermLevel.RESIDENT);
        permLevels.add(TownyPermission.PermLevel.ALLY);
        permLevels.add(TownyPermission.PermLevel.NATION);
        permLevels.add(TownyPermission.PermLevel.OUTSIDER);
        Map<TownyPermission.PermLevel, TownyPermission.ActionType> townyPermissions = new HashMap<>();
        Iterator<TownyPermission.PermLevel> keyIterator = permLevels.iterator();
        Iterator<TownyPermission.ActionType> valueIterator = actionTypes.iterator();
        while (keyIterator.hasNext() && valueIterator.hasNext()) {
            townyPermissions.put(keyIterator.next(), valueIterator.next());
        }
        return townyPermissions;
    }

    /**
     * gets a resident from Player
     * @param p Player
     * @return Resident
     */
    public Resident getResident(Player p){
        return townyApi.getResident(p);
    }
    @Nullable
    public Town getTownOrNull(Player p){
        return getResident(p).getTownOrNull();
    }
    @Nullable
    public Town getTownOrNull(String town){
        return townyApi.getTown(town);
    }
    @Nullable
    public Town getTownOrNull(UUID uuid){
        return townyApi.getTown(uuid);
    }
    @Nullable
    public Nation getNationOrNull(Player p){
        return getResident(p).getNationOrNull();
    }
    @Nullable
    public Nation getNationOrNull(String nation){
        return townyApi.getNation(nation);
    }
    @Nullable
    public Nation getNationOrNull(UUID uuid){
        return townyApi.getNation(uuid);
    }

    /**
     * Merge two towns using Towny API
     * @param prevailingTown town that absorbs succumbingTown
     * @param succumbingTown town to be deleted and absorbed by prevailingTown
     * @return true if success, else false
     */
    public boolean mergeTowns(Town prevailingTown, Town succumbingTown){
        townyApi.getDataSource().mergeTown(prevailingTown,succumbingTown);
        if (getTownOrNull(succumbingTown.getName()) != null){
            Kozers.logger.warning("Succumbing town still exists, merge failed");
            return false;
        }else{
            return true;
        }
    }
    public boolean getPVPAtLocation(Location location){
        return townyApi.isPVP(location);
    }
    @Nullable
    public TownyPermission getPermissionsAtLocationOrNull(Location location){
        return townyApi.getTownBlock(location).getPermissions();
    }
    public ArrayList<Town> getTowns(){
        return (ArrayList<Town>) townyApi.getTowns();
    }
}
