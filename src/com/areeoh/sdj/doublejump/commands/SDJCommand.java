package com.areeoh.sdj.doublejump.commands;

import com.areeoh.sdj.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SDJCommand implements CommandExecutor {

    private Main instance;

    public SDJCommand(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(instance.getOptions().getReloadPermission())) {
            sender.sendMessage(ChatColor.RED + "You do not have the permission to run this command.");
            return false;
        }
        if (args == null || args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "SDJ Information:");
            sender.sendMessage(ChatColor.GREEN + "/sdj reload - Reloads config");
            sender.sendMessage(ChatColor.GREEN + "Author: Areeoh - www.github.com/areeoh");
            return false;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            instance.reloadOptions();
            sender.sendMessage(ChatColor.GREEN + "Reloaded SDJ config.");
        }
        return false;
    }
}
