package net.mandomc.mandomcremade.utility;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {

    public static String str(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String prefix(){
        return str("&a&lMandoMC &8 » &7");
    }

    public static void msg(Player player, String string){
        player.sendMessage(prefix() + str(string));
    }
    public static void noPermission(Player player){
        player.sendMessage(str(prefix() + "&cThe Force is not with you..."));
    }

    /*
    &0 = black
    &1 = dark blue
    &2 = dark green
    &3 = dark aqua
    &4 = dark red
    &5 = dark purple
    &6 = gold
    &7 = gray
    &8 = dark gray
    &9 = blue
    &a = green
    &b = aqua
    &c = red
    &d = light purple
    &e = yellow
    &f = white

    &k = obfuscated
    &l = bold
    &m = strikethrough
    &n = underline
    &o = italic
    &r = reset

     */
}
