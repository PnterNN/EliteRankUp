package mc.pnternn.elitrankup.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import mc.pnternn.elitrankup.ElitRankUp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MysqlData implements Listener {
    ElitRankUp plugin = (ElitRankUp)ElitRankUp.getPlugin(ElitRankUp.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createPlayer(player.getUniqueId(), player);
    }

    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = this.plugin.getConnection().prepareStatement("SELECT * FROM " + this.plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            if (results.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(UUID uuid, Player player) {
        try {
            PreparedStatement statement = this.plugin.getConnection().prepareStatement("SELECT * FROM " + this.plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            if (playerExists(uuid) != true) {
                PreparedStatement insert = this.plugin.getConnection().prepareStatement("INSERT INTO " + this.plugin.table + " (`UUID`,`NAME`,`RANK`) VALUES (?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
