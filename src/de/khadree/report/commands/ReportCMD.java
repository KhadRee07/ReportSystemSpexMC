package de.khadree.report.commands;

import de.khadree.report.MySQL;
import de.khadree.report.Report;
import de.khadree.report.api.ItemAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReportCMD implements CommandExecutor, Listener {

    private Report main;
    String s;
    String s2;
    // ❘ » •

    public ReportCMD(Report main) {
        this.main = main;
        Bukkit.getPluginCommand("Report").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, main);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player p = (Player) sender;
            if (p.hasPermission("report.use")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        if (p.hasPermission("report.list")) {
                            MySQL.getReport(p);
                        } else {
                            p.sendMessage("§8» §4Report §8❘ §cDazu hast du keine Rechte!");
                        }
                    } else if (args[0].equalsIgnoreCase("info")) {
                        MySQL.getReports(p);
                    } else {
                        if (p.hasPermission("report.list")) {
                            p.sendMessage("§8» §4Report §8❘ §cBenutze: /Report [<list>] [<info>]");
                        } else {
                            p.sendMessage("§8» §4Report §8❘ §cBenutze: /Report [<info>]");
                        }
                    }
                } else if (args.length == 0) {
                    Inventory inv = Bukkit.createInventory(null, 27, "§8» §4Report");
                    for (int i = 0; i < 13; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    inv.setItem(13, ItemAPI.createSkull(1, p.getName(), "§8» §eWähle einen Spieler"));
                    for (int i = 14; i < 27; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    p.openInventory(inv);
                } else {
                    if (p.hasPermission("report.list")) {
                        p.sendMessage("§8» §4Report §8❘ §cBenutze: /Report [<list>] [<info>]");
                    } else {
                        p.sendMessage("§8» §4Report §8❘ §cBenutze: /Report [<info>]");
                    }
                }
            } else {
                p.sendMessage("§8» §4Report §8❘ §cDazu hast du keine Rechte!");
            }

        }

        return true;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        try {
            if (e.getInventory().getTitle().equalsIgnoreCase("§8» §4Report")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eWähle einen Spieler")) {
                    p.closeInventory();
                    Inventory inv = Bukkit.createInventory(null, 54, "§8» §eSpieler wählen");
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player != p) {
                            inv.addItem(ItemAPI.createSkull(1, player.getName(), "§8» §e" + player.getName()));
                        }
                    });
                    p.openInventory(inv);
                }
            } else if (e.getInventory().getTitle().equalsIgnoreCase("§8» §eSpieler wählen")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getType() != null) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().contains(player.getName())) {
                            s = player.getName();
                        }
                    });
                    p.closeInventory();
                    Inventory inv = Bukkit.createInventory(null, 27, "§8» §eGrund");
                    for (int i = 0; i < 5; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    for (int i = 5; i < 11; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    inv.setItem(13, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    for (int i = 15; i < 22; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    for (int i = 22; i < 27; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    inv.setItem(4, ItemAPI.createItemWithMaterial(Material.REDSTONE, (byte) 0, 1, "§8» §cHacking"));
                    inv.setItem(11, ItemAPI.createItemWithMaterial(Material.REDSTONE, (byte) 0, 1, "§8» §cRangausnutzung"));
                    inv.setItem(12, ItemAPI.createItemWithMaterial(Material.REDSTONE, (byte) 0, 1, "§8» §cWerbung"));
                    inv.setItem(14, ItemAPI.createItemWithMaterial(Material.REDSTONE, (byte) 0, 1, "§8» §cAnstößiger Skin"));
                    inv.setItem(15, ItemAPI.createItemWithMaterial(Material.REDSTONE, (byte) 0, 1, "§8» §cScamming"));
                    inv.setItem(22, ItemAPI.createItemWithMaterial(Material.REDSTONE, (byte) 0, 1, "§8» §cBeleidigung"));
                    p.openInventory(inv);
                }
            } else if (e.getInventory().getTitle().equalsIgnoreCase("§8» §eGrund")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cHacking")) {
                    String s2 = "Hacking";
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    MySQL.addReport(p.getName(), s, s2, ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear(), lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond(), 1, p);
                    p.closeInventory();
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    p.sendMessage("§8❘ §cDu hast §b" + s + " §cgemeldet");
                    p.sendMessage("§8❘ §eGrund §8» §6" + s2);
                    p.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                    p.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                    p.sendMessage("§8-------- [§4Report§8] --------");

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player != p) {
                            if (player.hasPermission("report.see")) {
                                player.sendMessage("§8-------- [§4Report§8] --------");
                                player.sendMessage("§8❘ §b" + p.getName() + " §chat §b" + s + " §cgemeldet");
                                player.spigot().sendMessage(Report.getAcceptMessage(Bukkit.getPlayer(s)));
                                player.sendMessage("§8❘ §eGrund §8» §6" + s2);
                                player.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                                player.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                                player.sendMessage("§8-------- [§4Report§8] --------");
                            }
                        }
                    });
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cRangausnutzung")) {
                    String s2 = "Rangausnutzung";
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    MySQL.addReport(p.getName(), s, s2, ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear(), lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond(), 1, p);
                    p.closeInventory();
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    p.sendMessage("§8❘ §cDu hast §b" + s + " §cgemeldet");
                    p.sendMessage("§8❘ §eGrund §8» §6" + s2);
                    p.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                    p.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player != p) {
                            if (player.hasPermission("report.see")) {
                                player.sendMessage("§8-------- [§4Report§8] --------");
                                player.sendMessage("§8❘ §b" + p.getName() + " §chat §b" + s + " §cgemeldet");
                                player.spigot().sendMessage(Report.getAcceptMessage(Bukkit.getPlayer(s)));
                                player.sendMessage("§8❘ §eGrund §8» §6" + s2);
                                player.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                                player.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                                player.sendMessage("§8-------- [§4Report§8] --------");
                            }
                        }
                    });
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cWerbung")) {
                    String s2 = "Werbung";
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    MySQL.addReport(p.getName(), s, s2, ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear(), lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond(), 1, p);
                    p.closeInventory();
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    p.sendMessage("§8❘ §cDu hast §b" + s + " §cgemeldet");
                    p.sendMessage("§8❘ §eGrund §8» §6" + s2);
                    p.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                    p.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player != p) {
                            if (player.hasPermission("report.see")) {
                                player.sendMessage("§8-------- [§4Report§8] --------");
                                player.sendMessage("§8❘ §b" + p.getName() + " §chat §b" + s + " §cgemeldet");
                                player.spigot().sendMessage(Report.getAcceptMessage(Bukkit.getPlayer(s)));
                                player.sendMessage("§8❘ §eGrund §8» §6" + s2);
                                player.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                                player.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                                player.sendMessage("§8-------- [§4Report§8] --------");
                            }
                        }
                    });
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cAnstößiger Skin")) {
                    String s2 = "Anstößiger Skin";
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    MySQL.addReport(p.getName(), s, s2, ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear(), lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond(), 1, p);
                    p.closeInventory();
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    p.sendMessage("§8❘ §cDu hast §b" + s + " §cgemeldet");
                    p.sendMessage("§8❘ §eGrund §8» §6" + s2);
                    p.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                    p.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player != p) {
                            if (player.hasPermission("report.see")) {
                                player.sendMessage("§8-------- [§4Report§8] --------");
                                player.sendMessage("§8❘ §b" + p.getName() + " §chat §b" + s + " §cgemeldet");
                                player.spigot().sendMessage(Report.getAcceptMessage(Bukkit.getPlayer(s)));
                                player.sendMessage("§8❘ §eGrund §8» §6" + s2);
                                player.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                                player.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                                player.sendMessage("§8-------- [§4Report§8] --------");
                            }
                        }
                    });
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cScamming")) {
                    String s2 = "Scamming";
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    MySQL.addReport(p.getName(), s, s2, ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear(), lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond(), 1, p);
                    p.closeInventory();
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    p.sendMessage("§8❘ §cDu hast §b" + s + " §cgemeldet");
                    p.sendMessage("§8❘ §eGrund §8» §6" + s2);
                    p.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                    p.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player != p) {
                            if (player.hasPermission("report.see")) {
                                player.sendMessage("§8-------- [§4Report§8] --------");
                                player.sendMessage("§8❘ §b" + p.getName() + " §chat §b" + s + " §cgemeldet");
                                player.spigot().sendMessage(Report.getAcceptMessage(Bukkit.getPlayer(s)));
                                player.sendMessage("§8❘ §eGrund §8» §6" + s2);
                                player.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                                player.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                                player.sendMessage("§8-------- [§4Report§8] --------");
                            }
                        }
                    });
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cBeleidigung")) {
                    String s2 = "Beleidigung";
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    MySQL.addReport(p.getName(), s, s2, ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear(), lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond(), 1, p);
                    p.closeInventory();
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    p.sendMessage("§8❘ §cDu hast §b" + s + " §cgemeldet");
                    p.sendMessage("§8❘ §eGrund §8» §6" + s2);
                    p.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                    p.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                    p.sendMessage("§8-------- [§4Report§8] --------");
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (player != p) {
                            if (player.hasPermission("report.see")) {
                                player.sendMessage("§8-------- [§4Report§8] --------");
                                player.sendMessage("§8❘ §b" + p.getName() + " §chat §b" + s + " §cgemeldet");
                                player.spigot().sendMessage(Report.getAcceptMessage(Bukkit.getPlayer(s)));
                                player.sendMessage("§8❘ §eGrund §8» §6" + s2);
                                player.sendMessage("§8❘ §eDatum §8» §6" + ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear());
                                player.sendMessage("§8❘ §eUhrzeit §8» §6" + lt.getHour() + ":" + lt.getMinute() + ":" + lt.getSecond());
                                player.sendMessage("§8-------- [§4Report§8] --------");
                            }
                        }
                    });
                }
            } else if (e.getInventory().getTitle().equalsIgnoreCase("§8» §4Reports")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getType() == Material.DIAMOND) {
                    int id = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().replace("§8» §eID §8❘ §b", ""));
                    Inventory inv = Bukkit.createInventory(null, 27, "§8» §eID §8❘ §b" + id);
                    for (int i = 0; i < 12; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    inv.setItem(12, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 5, 1, "§8» §aStatus setzen"));
                    inv.setItem(13, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    inv.setItem(14, ItemAPI.createItemWithMaterial(Material.ENDER_PEARL, (byte) 0, 1, "§8» §3Zum Spieler Teleportieren"));
                    for (int i = 15; i < 27; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    p.openInventory(inv);
                }
            } else if (e.getInventory().getTitle().contains("§8» §eID §8❘ §b")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aStatus setzen")) {
                    int id = Integer.parseInt(e.getInventory().getTitle().replace("§8» §eID §8❘ §b", ""));
                    Inventory inv = Bukkit.createInventory(null, 27, "§8» §eID §8❘ §b" + id);
                    for (int i = 0; i < 12; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    inv.setItem(12, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 5, 1, "§8» §aErledigt §a§l✔"));
                    inv.setItem(13, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    inv.setItem(14, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 14, 1, "§8» §4Verwerflich §4§l✘"));
                    for (int i = 15; i < 27; i++) {
                        inv.setItem(i, ItemAPI.createItemWithMaterial(Material.STAINED_GLASS_PANE, (byte) 7, 1, "§7"));
                    }
                    p.openInventory(inv);
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aErledigt §a§l✔")) {
                    p.closeInventory();
                    MySQL.getOfflinePlayer(Integer.parseInt(e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "")));
                    MySQL.setReport(Integer.parseInt(e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "")), 2);
                    p.sendMessage("§8❘ §4Report §8» §aDu hast den Report mit der ID §b" + e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "") + " §aals erledigt makiert");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §4Verwerflich §4§l✘")) {
                    p.closeInventory();

                    MySQL.getOfflinePlayer(Integer.parseInt(e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "")));
                    MySQL.setReport(Integer.parseInt(e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "")), 3);
                    p.sendMessage("§8❘ §4Report §8» §aDu hast den Report mit der ID §b" + e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "") + " §aals §4verworfen §amakiert");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §3Zum Spieler teleportieren")) {
                    if (MySQL.playerIsOnline(Integer.parseInt(e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "")))) {
                        MySQL.teleportToPlayer(p, Integer.parseInt(e.getInventory().getTitle().replace("§8» §eID §8❘ §b", "")));
                    }
                }
            } else if (e.getInventory().getTitle().equalsIgnoreCase("§8» §bDeine §4Reports")) {
                e.setCancelled(true);
            }


        } catch (NullPointerException ex) {

        }
    }
}
    