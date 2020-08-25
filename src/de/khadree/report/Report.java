package de.khadree.report;

import de.khadree.report.commands.ReportCMD;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Report extends JavaPlugin {

    public void onEnable() {
        new MySQL().connect();
        new MySQL().update("CREATE TABLE IF NOT EXISTS Reports (melder varchar(100), gemeldeter varchar(100), grund varchar(100), datum varchar(100), uhrzeit varchar(100), status int)");
        new ReportCMD(this);

        Bukkit.getConsoleSender().sendMessage("§8-------- [§4Report§8] --------");
        Bukkit.getConsoleSender().sendMessage("§8| §e/Report §8>> §breport.use");
        Bukkit.getConsoleSender().sendMessage("§8| §e/Report list §8>> §breport.list");
        Bukkit.getConsoleSender().sendMessage("§8-------- [§4Report§8] --------");
    }

    @Override
    public void onDisable() {
        new MySQL().disconnect();
    }

    public static TextComponent getClickableMessage(String message, String hover, String command){
        TextComponent textComponent = new TextComponent(message);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
        return textComponent;
    }

    public static TextComponent getAcceptMessage(Player target){

        TextComponent textComponent = new TextComponent("§8❘ §eKlicke ");
        textComponent.addExtra(getClickableMessage( "§b§nhier§r ", "§8[§3Klicken zum teleportieren§8]", "/tp " + target.getName()));
        textComponent.addExtra("§eum dich zu teleportieren");

        return textComponent;
    }
}