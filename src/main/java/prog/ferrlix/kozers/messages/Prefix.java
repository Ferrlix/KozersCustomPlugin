package prog.ferrlix.kozers.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.util.ConfigUtil;
import prog.ferrlix.kozers.util.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;

import static net.kyori.adventure.text.Component.text;

public class Prefix {
    ConfigUtil config = Kozers.config;

    /**
     * the Component prefix
     * @return the Component from messages.prefix.string in config.yml
     */
    @NotNull
    public Component get() {
        String prefix;
        Component finalComponent;
        try {
            String string = "[Kozers]"; // fallback prefix
            string = config.getConfig().get("messages.prefix.string", string).toString();
            finalComponent = MessageUtil.stringToComponent(string);
        } catch (Exception e) {
            Kozers.logger().warning("Plugin prefix from config threw an exception! (It is probably invalid)");
            e.printStackTrace();
            return text().build();
        }
        return finalComponent;
    }
}
