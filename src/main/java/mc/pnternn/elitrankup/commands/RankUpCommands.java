package mc.pnternn.elitrankup.commands;

import mc.pnternn.elitrankup.ElitRankUp;
import mc.pnternn.elitrankup.util.Messages;
import mc.pnternn.elitrankup.util.RankData;
import mc.pnternn.elitrankup.util.RankUtil;
import mc.pnternn.elitrankup.util.Requirements;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankUpCommands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            RankData rankData = new RankData(p.getUniqueId());
            int rank = rankData.getGroupNumber();
            if (rank == RankUtil.ranks.size()) {
                p.sendMessage(Messages.instance.SON_RUTBE);
                return true;
            }
            RankUtil rankUtil = RankUtil.ranks.get(rank);
            if (!rankUtil.getPerm().equalsIgnoreCase("") && !p.hasPermission(rankUtil.getPerm())) {
                p.sendMessage(Messages.instance.YETKIN_YOK);
                return true;
            }
            int all = rankUtil.getRequirements().size();
            int top = 0;
            for (Requirements req : rankUtil.getRequirements()) {
                String parsedPlaceholder = req.parsePlaceholder(p);
                if (parsedPlaceholder.equalsIgnoreCase(req.getPlaceholder())) {
                    ElitRankUp.getInstance().warning("" + rank + 1 + " numaralr" + rank + 1 + " parselenirken bir hata oluBbir placeholder olmayabilir.");
                    p.sendMessage(Messages.instance.BIR_HATA_OLUSTU);
                    return true;
                }
                if (isInteger(parsedPlaceholder) || isDouble(parsedPlaceholder)) {
                    int i = -999;
                    if (isInteger(parsedPlaceholder)) {
                        i = parseInteger(parsedPlaceholder);
                    } else if (isDouble(parsedPlaceholder)) {
                        i = (int)parseDouble(parsedPlaceholder);
                    }
                    int val = -999;
                    if (isInteger(req.getValue())) {
                        val = parseInteger(req.getValue());
                    } else if (isDouble(req.getValue())) {
                        val = (int)parseDouble(req.getValue());
                    }
                    if (i == -999 || val == -999) {
                        ElitRankUp.getInstance().getLogger().warning(parsedPlaceholder + " veya " + parsedPlaceholder + " sayade");
                                p.sendMessage(Messages.instance.BIR_HATA_OLUSTU);
                        return true;
                    }
                    if (i >= val) {
                        top++;
                        continue;
                    }
                    p.sendMessage(Messages.instance.GEREKLILER_EKSIK.replace("%name%", req.getName()).replace("%value%", req.getValue()));
                    return true;
                }
                if (parsedPlaceholder.equalsIgnoreCase(req.getValue())) {
                    top++;
                    continue;
                }
                p.sendMessage(Messages.instance.GEREKLILER_EKSIK.replace("%name%", req.getName()).replace("%value%", req.getValue()));
                return true;
            }
            if (rankUtil.getTakeMoney() != 0)
                if ((ElitRankUp.getInstance()).economy.getBalance((OfflinePlayer)p) >= rankUtil.getTakeMoney()) {
                    (ElitRankUp.getInstance()).economy.withdrawPlayer((OfflinePlayer)p, rankUtil.getTakeMoney());
                } else {
                    p.sendMessage(Messages.instance.YETERSIZ_PARA);
                    return true;
                }
            if (top == all) {
                rankData.setGroupNumber(rank + 1);
                for (String prize : rankUtil.getPrizes())
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), prize.replace("%player%", p.getName()));
                p.sendMessage(Messages.instance.RUTBE_ATLADIN.replace("%rank%", rankUtil.getName()));
            } else {
                p.sendMessage(Messages.instance.GEREKLILER_EKSIK);
                return true;
            }
        }
        return false;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private int parseInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return -999;
        }
    }

    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return -999.0D;
        }
    }
}
