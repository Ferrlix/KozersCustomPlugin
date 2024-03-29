package prog.ferrlix.kozers.commands;

import com.palmergames.bukkit.towny.object.Government;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.entity.Player;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.integration.towny.chat.TownyChat;
import prog.ferrlix.kozers.messages.Colors;
import prog.ferrlix.kozers.messages.Prefix;

import java.util.Map;

import static net.kyori.adventure.text.Component.text;

public class MainCommand {
    CommandAPICommand mainCommand = new CommandAPICommand("kozers")
            .withOptionalArguments(new StringArgument("action"))
            .executes((sender, arguments) -> {
                assert arguments != null;
                String action = (String) arguments.get("action");
                switch(action.toLowerCase()){
                    case "reload" -> {
                        Kozers.instance.reload();
                        sender.sendMessage(text()
                                .append(new Prefix().get())
                                        .append(text(" Plugin reloaded!").color(Colors.content()))
                                        .build()
                                );
                    }
                    case "chatmap","map","playerchatmap" -> {
                        Map<Player, Government> map = TownyChat.playerChatMap;
                        sender.sendMessage(" - KEYS  -  VALUES - ");
                        map.forEach((key, value) -> {
                            sender.sendMessage(" - " + key.toString() + " = " + value.toString());
                        });
                    }
                }
            });
    /**
     * internal, do not use
     * @return main command for registration
     */
    public CommandAPICommand getMainCommand(){return this.mainCommand;}
}
