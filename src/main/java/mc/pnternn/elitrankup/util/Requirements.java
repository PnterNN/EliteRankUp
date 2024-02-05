package mc.pnternn.elitrankup.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class Requirements {
    private String name;

    private String type;

    private String placeholder;

    private String value;

    public Requirements(String type, String placeholder, String value, String name) {
        this.name = name;
        this.type = type;
        this.placeholder = placeholder;
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public String parsePlaceholder(Player p) {
        String str = getPlaceholder();
        if (this.type.equalsIgnoreCase("placeholder"))
            str = PlaceholderAPI.setPlaceholders(p, getPlaceholder());
        return str;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
