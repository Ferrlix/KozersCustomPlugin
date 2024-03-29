package prog.ferrlix.kozers.events;

import com.palmergames.bukkit.towny.object.Government;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.integration.towny.chat.TownyChat;

import java.util.Map;

/**
 * handles AsyncChatEvent, mainly for TownyChat
 */
public class AsyncChatEvent implements Listener {
    Map<Player, Government> playerChatMap = TownyChat.playerChatMap;
    @EventHandler
    private void onChat(io.papermc.paper.event.player.AsyncChatEvent event){
        Player p = event.getPlayer();
        if (playerChatMap.containsKey(p)) {
            Kozers.logger.info(p.getName() + " Sent a message and is in the player chat map, sending message! - TownyChat");
            event.setCancelled(true);
            Object govern = playerChatMap.get(p);
            if (govern instanceof Town){
                TownyChat.sendChat(p, event.message(), TownyChat.townyChatType.TOWN);
            }else if (govern instanceof Nation){
                TownyChat.sendChat(p, event.message(), TownyChat.townyChatType.NATION);
            }
        }else{
            Kozers.logger.info(p.getName() + " was not in the chat map, message ignored - TownyChat");
        }
    }
}
