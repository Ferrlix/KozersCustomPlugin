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
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.integration.towny.TownyUtil;
import prog.ferrlix.kozers.messages.Colors;
import prog.ferrlix.kozers.messages.Prefix;
import prog.ferrlix.kozers.util.MessageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.kyori.adventure.text.Component.text;

public class TownyChat{
    private final Map<Player, Object> playerChatMap = new HashMap<>();

    public Map<Player, Object> getPlayerChatMap(){return this.playerChatMap;}
    public enum townyChatType{
        TOWN,
        NATION
    }
    TownyUtil townyUtil = new TownyUtil();


    public void sendChat(Player speaker, Component message, townyChatType type){
        Government govern = null;
        switch(type){
            case TOWN -> {
                govern = townyUtil.getTownOrNull(speaker);
            }
            case NATION -> {
                govern = townyUtil.getNationOrNull(speaker);
            }
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
    void sendChat(Player speaker, String message, townyChatType type){
        Government govern = null;
        switch(type){
            case TOWN -> {
                govern = townyUtil.getTownOrNull(speaker);
            }
            case NATION -> {
                govern = townyUtil.getNationOrNull(speaker);
            }
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
    Prefix prefix = new Prefix();
    CommandAPICommand townCommand = new CommandAPICommand("townchat")
            .withOptionalArguments(new GreedyStringArgument("message"))
            .withAliases("tc")
            .executesPlayer((p,arguments) -> {
                String message = (String) arguments.get("message");
                if (p!=null) {
                    if (playerChatMap.containsKey(p)){
                        if (playerChatMap.get(p) instanceof Town){
                            removePlayerFromChatMap(p);
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(text("Left town chat!").color(Colors.error()))
                                    .build());
                        }else{
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(text("Already in nation chat!").color(Colors.error()))
                                    .build());}
                    }else if (message == null){
                        Object govern = addPlayerToChatMap(p,townyChatType.TOWN);
                        if (govern == null) {
                            Kozers.logger().info("Player " + p.getName() + " could not join town chat");}
                        else{
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(text("Successfully joined town chat!").color(Colors.content()))
                                    .build());
                            Kozers.logger().info(p.getName() + " joined town chat");}
                    }else{
                        addPlayerToChatMap(p,townyChatType.TOWN);
                        sendChat(p,message,townyChatType.TOWN);
                        removePlayerFromChatMap(p);
                    }}})
            .executesConsole(sender -> {
                ConsoleCommandSender console = (ConsoleCommandSender) sender;
                console.sendMessage("Console cannot join town chat :D");
            });
    CommandAPICommand nationCommand = new CommandAPICommand("nationchat")
            .withOptionalArguments(new GreedyStringArgument("message"))
            .withAliases("nc")
            .executesPlayer((p,arguments) -> {
                String message = (String) arguments.get("message");
                if (p!=null) {
                    if (playerChatMap.containsKey(p)){
                        if (playerChatMap.get(p) instanceof Nation){
                            removePlayerFromChatMap(p);
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(text("Left nation chat!").color(Colors.error()))
                                    .build());
                        }else{
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(text("Already in town chat!").color(Colors.error()))
                                    .build());}
                    }else if (message == null){
                        Object govern = addPlayerToChatMap(p,townyChatType.NATION);
                        if (govern == null) {
                            Kozers.logger().info("Player " + p.getName() + " could not join nation chat");
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(MessageUtil.stringToComponent("<#ff0000>Could not join nation chat because you are not in a <#0000ff>nation!"))
                                    .build());
                        }
                        else{
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(text("Successfully joined nation chat!").color(Colors.content()))
                                    .build());
                            Kozers.logger().info(p.displayName() + " joined nation chat");}
                    }else{
                        addPlayerToChatMap(p,townyChatType.NATION);
                        sendChat(p,message,townyChatType.NATION);
                        removePlayerFromChatMap(p);
                    }}})
            .executesConsole(sender -> {
                ConsoleCommandSender console = (ConsoleCommandSender) sender;
                console.sendMessage("Console cannot join nation chat :D");
            });
    public CommandAPICommand getTownCommand(){
        return this.townCommand;
    }

    public CommandAPICommand getNationCommand(){
        return this.nationCommand;
    }


    private Object addPlayerToChatMap(Player player, townyChatType type){
        Object govern = null;
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
    public void removePlayerFromChatMap(Player player){
        try{
            playerChatMap.remove(player);
        }catch(Exception e){e.printStackTrace();}
    }
}
