package mc.pnternn.elitrankup.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import mc.pnternn.elitrankup.ElitRankUp;

public class RankData {
    private String uuid;

    private int groupNumber;

    ElitRankUp plugin = (ElitRankUp)ElitRankUp.getPlugin(ElitRankUp.class);

    public RankData(UUID uuid) {
        this.uuid = uuid.toString();
        try {
            PreparedStatement statement = this.plugin.getConnection().prepareStatement("SELECT * FROM " + this.plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            this.groupNumber = results.getInt("RANK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUUID() {
        return this.uuid;
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }

    public boolean setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
        try {
            PreparedStatement statement = this.plugin.getConnection().prepareStatement("UPDATE " + this.plugin.table + " SET `RANK`=? WHERE `UUID`=?");
            statement.setInt(1, groupNumber);
            statement.setString(2, this.uuid.toString());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            ElitRankUp.getInstance().getLogger().warning(this.uuid + " adlı oyuncunun grubu " + this.uuid + " numaralı bir hata oluştu");
            return false;
        }
    }
}
