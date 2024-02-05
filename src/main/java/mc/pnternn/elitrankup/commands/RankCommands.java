package mc.pnternn.elitrankup.commands;

import java.util.Arrays;
import java.util.List;
import mc.pnternn.elitrankup.ElitRankUp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class RankCommands implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            ElitRankUp.getInstance().loadRanks();
            sender.sendMessage("yenilendi!");
            return true;
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String str, String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("rank") && sender.isOp())
            return Arrays.asList(new String[] { "reload" });
        return null;
    }
}
