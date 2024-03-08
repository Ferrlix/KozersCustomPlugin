package prog.ferrlix.kozers.messages;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.util.ConfigUtil;
import prog.ferrlix.kozers.util.MessageUtil;

import static net.kyori.adventure.text.Component.text;
public class Prefix {
    ConfigUtil config = ConfigUtil.getInstance(Kozers.plugin,"config.yml");
    /**
     * the Component prefix
     * @return the Component from messages.prefix.string in config.yml
     */
    @NotNull
    public Component get() {
        Component finalComponent;
        try {
            String string = "[Kozers]"; // fallback prefix
            string = config.getConfig().get("messages.prefix.string", string).toString();
            finalComponent = MessageUtil.stringToComponent(string);
        } catch (Exception e) {
            Kozers.logger.warning("Plugin prefix from config threw an exception! (It is probably invalid)");
            e.printStackTrace();
            return text().build();
        }
        return finalComponent;
    }
}
