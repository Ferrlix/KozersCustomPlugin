package prog.ferrlix.kozers.integration.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import prog.ferrlix.kozers.Kozers;

import java.util.*;

public class TownyUtil {
    private TownyAPI api = TownyAPI.getInstance();

    private Map<TownyPermission.PermLevel, TownyPermission.ActionType> getTownyPermissions(){
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

    public Resident getResident(Player p){
        return api.getResident(p);
    }
    @Nullable
    public Town getTownOrNull(Player p){
        return getResident(p).getTownOrNull();
    }
    @Nullable
    public Town getTownOrNull(String town){
        return api.getTown(town);
    }
    @Nullable
    public Town getTownOrNull(UUID uuid){
        return api.getTown(uuid);
    }
    @Nullable
    public Nation getNationOrNull(Player p){
        return getResident(p).getNationOrNull();
    }
    @Nullable
    public Nation getNationOrNull(String nation){
        return api.getNation(nation);
    }
    @Nullable
    public Nation getNationOrNull(UUID uuid){
        return api.getNation(uuid);
    }
    public boolean mergeTowns(Town prevailingTown, Town succumbingTown){
        api.getDataSource().mergeTown(prevailingTown,succumbingTown);
        if (getTownOrNull(succumbingTown.getName()) != null){
            Kozers.logger().warning("Succumbing town still exists, merge failed");
            return false;
        }else{
            return true;
        }
    }
    public boolean getPVPAtLocation(Location location){
        return api.isPVP(location);
    }
    @Nullable
    public TownyPermission getPermissionsAtLocationOrNull(Location location){
        return api.getTownBlock(location).getPermissions();
    }
    public ArrayList<Town> getTowns(){
        return (ArrayList<Town>) api.getTowns();
    }
    @NotNull
    public String getTownTag(Player p){
        Town town = null;
        try{
            town = api.getTown(p);}
        catch(Exception e){
            e.printStackTrace();
        }
        if (town==null){
            return "null";
        }
        return town.getTag();
    }
}
