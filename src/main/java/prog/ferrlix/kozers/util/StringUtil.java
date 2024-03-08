package prog.ferrlix.kozers.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import prog.ferrlix.kozers.Kozers;

import java.util.ArrayList;
import java.util.Arrays;

import static net.kyori.adventure.text.Component.text;

public class StringUtil {
    public static TextColor hexToTextColor(String hexCode) {
        if (hexCode.matches("^#?[A-Fa-f0-9]{6}$")) {
            if (!hexCode.startsWith("#"))
                hexCode = "#" + hexCode;
            return TextColor.fromHexString(hexCode);
        }
        return TextColor.color(255, 255, 255); //fallback color
    }
}
