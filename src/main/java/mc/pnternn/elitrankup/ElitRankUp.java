package mc.pnternn.elitrankup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import mc.pnternn.elitrankup.commands.RankCommands;
import mc.pnternn.elitrankup.commands.RankUpCommands;
import mc.pnternn.elitrankup.util.Messages;
import mc.pnternn.elitrankup.util.MysqlData;
import mc.pnternn.elitrankup.util.RankUtil;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ElitRankUp extends JavaPlugin {
    private static ElitRankUp instance;

    Connection connection;

    public String host;

    public String database;

    public String username;

    public String password;

    public String table;

    public int port;

    public boolean hasUpdate;

    public String updateUrl;

    public File dataFile;

    public Economy economy = null;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getLogger().info("ElitRankUp created by PnterNN");
        getLogger().info("Discord: PnterNN#8478");
        if (!setupEconomy()) {
            getLogger().severe("Vault bulunamadEklenti kapat");
                    getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        loadRanks();
        loadDataFiles();
        loadOthers();
        mysqlSetup();
    }

    public static ElitRankUp getInstance() {
        return instance;
    }

    public void mysqlSetup() {
        getServer().getPluginManager().registerEvents((Listener)new MysqlData(), (Plugin)this);
        FileConfiguration fc = getInstance().getConfig();
        this.host = fc.getString("mysql.host");
        this.database = fc.getString("mysql.database");
        this.port = fc.getInt("mysql.port");
        this.username = fc.getString("mysql.username");
        this.password = fc.getString("mysql.password");
        this.table = fc.getString("mysql.table");
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed())
                    return;
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
                Bukkit.getConsoleSender().sendMessage("" + ChatColor.GREEN + "MYSQL CONNECTED");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private void loadOthers() {
        getCommand("rank").setExecutor((CommandExecutor)new RankCommands());
        getCommand("rank").setTabCompleter((TabCompleter)new RankCommands());
        getCommand("rankup").setExecutor((CommandExecutor)new RankUpCommands());
    }

    private void loadDataFiles() {
        this.dataFile = new File(getDataFolder(), "data.yml");
        if (!this.dataFile.exists())
            try {
                this.dataFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void loadRanks() {
        new Messages(getConfig());
        RankUtil.ranks = new ArrayList();
        for (String str : getConfig().getConfigurationSection("Ranks").getKeys(false))
            new RankUtil(str);
    }

    public void warning(String warning) {
        for (OfflinePlayer p : Bukkit.getOperators()) {
            if (!p.isOnline())
                continue;
            p.getPlayer().sendMessage("" + warning);
        }
        getLogger().warning(warning);
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        this.economy = (Economy)rsp.getProvider();
        return (this.economy != null);
    }
}
