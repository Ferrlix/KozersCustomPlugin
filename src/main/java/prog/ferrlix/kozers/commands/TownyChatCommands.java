package prog.ferrlix.kozers.commands;

import com.palmergames.bukkit.towny.object.Government;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.integration.towny.chat.TownyChat;
import prog.ferrlix.kozers.messages.Colors;
import prog.ferrlix.kozers.messages.Prefix;
import prog.ferrlix.kozers.util.MessageUtil;

import java.util.Map;

import static net.kyori.adventure.text.Component.text;

public class TownyChatCommands {
    Prefix prefix = new Prefix();
    Map<Player, Government> playerChatMap = TownyChat.playerChatMap;
    CommandAPICommand townCommand = new CommandAPICommand("townchat")
            .withOptionalArguments(new GreedyStringArgument("message"))
            .withAliases("tc")
            .executesPlayer((p,arguments) -> {
                String message = (String) arguments.get("message");
                if (p!=null) {
                    if (playerChatMap.containsKey(p)){
                        if (playerChatMap.get(p) instanceof Town){
                            TownyChat.removePlayerFromChatMap(p);
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
                        Object govern = TownyChat.addPlayerToChatMap(p, TownyChat.townyChatType.TOWN);
                        if (govern == null) {
                            Kozers.logger.info("Player " + p.getName() + " could not join town chat");}
                        else{
                            p.sendMessage(text()
                                    .append(prefix.get().append(text(" ")))
                                    .append(text("Successfully joined town chat!").color(Colors.content()))
                                    .build());
                            Kozers.logger.info(p.getName() + " joined town chat");}
                    }else{
                        TownyChat.addPlayerToChatMap(p, TownyChat.townyChatType.TOWN);
                        TownyChat.sendChat(p,message, TownyChat.townyChatType.TOWN);
                        TownyChat.removePlayerFromChatMap(p);
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
                            TownyChat.removePlayerFromChatMap(p);
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
                        Object govern = TownyChat.addPlayerToChatMap(p, TownyChat.townyChatType.NATION);
                        if (govern == null) {
                            Kozers.logger.info("Player " + p.getName() + " could not join nation chat");
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
                            Kozers.logger.info(p.displayName() + " joined nation chat");}
                    }else{
                        TownyChat.addPlayerToChatMap(p, TownyChat.townyChatType.NATION);
                        TownyChat.sendChat(p,message, TownyChat.townyChatType.NATION);
                        TownyChat.removePlayerFromChatMap(p);
                    }}})
            .executesConsole(sender -> {
                ConsoleCommandSender console = (ConsoleCommandSender) sender;
                console.sendMessage("Console cannot join nation chat :D");
            });

    /**
     * internal, do not use
     * @return town command for registration
     */
    public CommandAPICommand getTownCommand(){return this.townCommand;}
    /**
     * internal, do not use
     * @return nation command for registration
     */
    public CommandAPICommand getNationCommand(){return this.nationCommand;}

}
