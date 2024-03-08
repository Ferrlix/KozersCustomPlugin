package prog.ferrlix.kozers.events;

import com.palmergames.bukkit.towny.object.Government;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.integration.towny.chat.TownyChat;

import java.util.Map;

public class PlayerQuitEvent implements Listener {
    Map<Player, Government> playerChatMap = Kozers.townyChat.getPlayerChatMap();
    TownyChat townyChat = Kozers.townyChat;
    @EventHandler
    private void onQuit(org.bukkit.event.player.PlayerQuitEvent event){
        Player p = event.getPlayer();
        if (playerChatMap.containsKey(p)){
            townyChat.removePlayerFromChatMap(p);
        }
    }
}
