package mc.pnternn.elitrankup.util;

import java.util.ArrayList;
import java.util.List;
import mc.pnternn.elitrankup.ElitRankUp;
import org.bukkit.configuration.file.FileConfiguration;

public class RankUtil {
    public static List<RankUtil> ranks = new ArrayList<>();

    private String name;

    private String perm;

    private List<Requirements> requirements = new ArrayList<>();

    private List<String> prizes = new ArrayList<>();

    private int takeMoney;

    public RankUtil(String tag) {
        FileConfiguration fc = ElitRankUp.getInstance().getConfig();
        this.name = fc.getString("Ranks." + tag + ".name");
        this.perm = fc.getString("Ranks." + tag + ".perm");
        for (String reqnum : fc.getConfigurationSection("Ranks." + tag + ".requirements").getKeys(false)) {
            String name = fc.getString("Ranks." + tag + ".requirements." + reqnum + ".name");
            String type = fc.getString("Ranks." + tag + ".requirements." + reqnum + ".type");
            String placeholder = fc.getString("Ranks." + tag + ".requirements." + reqnum + ".placeholder");
            String value = fc.getString("Ranks." + tag + ".requirements." + reqnum + ".value");
            this.requirements.add(new Requirements(type, placeholder, value, name));
        }
        this.takeMoney = fc.getInt("Ranks." + tag + ".take.money", 0);
        this.prizes.addAll(fc.getStringList("Ranks." + tag + ".prizes"));
        ranks.add(this);
    }

    public String getName() {
        return this.name;
    }

    public String getPerm() {
        return this.perm;
    }

    public int getTakeMoney() {
        return this.takeMoney;
    }

    public List<Requirements> getRequirements() {
        return this.requirements;
    }

    public List<String> getPrizes() {
        return this.prizes;
    }
}
