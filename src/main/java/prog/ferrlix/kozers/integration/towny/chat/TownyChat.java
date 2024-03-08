package prog.ferrlix.kozers.integration.towny.chat;

import com.palmergames.bukkit.towny.object.Government;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.integration.towny.TownyUtil;
import prog.ferrlix.kozers.messages.Colors;
import prog.ferrlix.kozers.messages.Prefix;
import prog.ferrlix.kozers.util.MessageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.kyori.adventure.text.Component.text;

/**
 * Handles all interactions between player and plugin regarding Towny and chatting
 */
public class TownyChat{
    static TownyUtil townyUtil = new TownyUtil();
    /**
     * the towny chat map
     */
    public static Map<Player, Government> playerChatMap = new HashMap<>();
    /**
     * get the map of players who are chatting in Government chats of some sort
     * @return The map of players in chat
     */
    public Map<Player, Government> getPlayerChatMap(){
        return playerChatMap;}
    /**
     * add a player to the towny chat map for handling
     * @param player the Player key to add the map
     * @param type the type to add, because a Player can have two Governments at the same time
     * @return The Government value that was added with the Player key
     */
    public static @Nullable Government addPlayerToChatMap(Player player, townyChatType type){
        Government govern = null;
        switch(type){
            case TOWN -> govern = townyUtil.getTownOrNull(player);
            case NATION -> govern = townyUtil.getNationOrNull(player);
        }
        if (govern==null){
            return null;
        }
        try{
            playerChatMap.put(player,govern);
        }catch(Exception e){
            e.printStackTrace();
            return null;}
        return govern;
    }
    /**
     * removes a given Player from the towny chat map
     * @param player the Player to remove from the map
     */
    public static void removePlayerFromChatMap(Player player){
        try{
            playerChatMap.remove(player);
        }catch(Exception e){e.printStackTrace();}
    }
    /**
     * internal chat types used in Map iteration and chat events
     */
    public enum townyChatType{
        TOWN,
        NATION
    }
    /**
     * send a towny chat
     * @param speaker the Player who sent the message
     * @param message the message the speaker sent
     * @param type the Government type (Town | Nation) that the speaker's message should get sent
     */
    public static void sendChat(Player speaker, Component message, @NotNull townyChatType type){
        Government govern = null;
        switch(type){
            case TOWN -> govern = townyUtil.getTownOrNull(speaker);
            case NATION -> govern = townyUtil.getNationOrNull(speaker);
        }
        ArrayList<Player> targets = new ArrayList<>();
        Government finalGovern = govern;
        //targets.add(speaker);
        playerChatMap.forEach((keyPlayer, valueGovern) -> {
            if (valueGovern == finalGovern){targets.add(keyPlayer);}
        });
        String tag = finalGovern.getTag();
        TextColor color;
        if (finalGovern instanceof Town){
            color = TextColor.color(NamedTextColor.GREEN);
        }else{
            color = TextColor.color(NamedTextColor.DARK_AQUA);
        }
        Component componentMessage = text()
                .append(text("[").color(NamedTextColor.BLUE))
                .append(text(tag).color(color))
                .append(text("] ").color(NamedTextColor.BLUE))
                .append(speaker.displayName())
                .append(text(" > ").color(NamedTextColor.GRAY))
                .append(message)
                .build();
        for (Player player : targets) {
            player.sendMessage(componentMessage);
        }
    }
    /**
     * send a towny chat
     * @param speaker the Player who sent the message
     * @param message the message the speaker sent
     * @param type the Government type (Town | Nation) that the speaker's message should get sent
     */
    public static void sendChat(Player speaker, String message, @NotNull townyChatType type){
        Government govern = null;
        switch(type){
            case TOWN -> govern = townyUtil.getTownOrNull(speaker);
            case NATION -> govern = townyUtil.getNationOrNull(speaker);
        }
        ArrayList<Player> targets = new ArrayList<>();
        Government finalGovern = govern;
        //targets.add(speaker);
        playerChatMap.forEach((keyPlayer, valueGovern) -> {
            if (valueGovern == finalGovern){targets.add(keyPlayer);}
        });
        String tag = finalGovern.getTag();
        TextColor color;
        if (finalGovern instanceof Town){
            color = TextColor.color(NamedTextColor.GREEN);
        }else{
            color = TextColor.color(NamedTextColor.DARK_AQUA);
        }
        Component componentMessage = text()
                .append(text("[").color(NamedTextColor.BLUE))
                .append(text(tag).color(color))
                .append(text("] ").color(NamedTextColor.BLUE))
                .append(speaker.displayName())
                .append(text(" > ").color(NamedTextColor.GRAY))
                .append(text(message))
                .build();
        for (Player player : targets) {
            player.sendMessage(componentMessage);
        }
    }
}
