package mc.pnternn.elitrankup.util;

import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
    public static Messages instance;
    public String SON_RUTBE;
    public String RUTBE_ATLADIN;
    public String BIR_HATA_OLUSTU;
    public String YETKIN_YOK;
    public String GEREKLILER_EKSIK;
    public String YETERSIZ_PARA;

    public Messages(FileConfiguration fc) {
        instance = this;
        this.YETERSIZ_PARA = this.colorize(fc.getString("Messages.yetersiz-para"));
        this.SON_RUTBE = this.colorize(fc.getString("Messages.son-seviyedesin"));
        this.RUTBE_ATLADIN = this.colorize(fc.getString("Messages.seviye-atladin"));
        this.BIR_HATA_OLUSTU = this.colorize(fc.getString("Messages.bir-hata-olustu"));
        this.YETKIN_YOK = this.colorize(fc.getString("Messages.yetkin-yok"));
        this.GEREKLILER_EKSIK = this.colorize(fc.getString("Messages.eksik-gereklilik"));
    }

    private String colorize(String str) {
        str = str.replace("&", "\u00a7");
        return str;
    }
}

