package com.areeoh.sdj;

import com.areeoh.sdj.doublejump.DoubleJumpHandler;
import com.areeoh.sdj.doublejump.commands.SDJCommand;
import com.areeoh.sdj.options.Options;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Options options;
    private DoubleJumpHandler doubleJumpHandler;

    @Override
    public void onEnable() {
        checkConfig();
        getCommand("sdj").setExecutor(new SDJCommand(this));
    }

    private void checkConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadOptions();
    }

    public void reloadOptions() {
        reloadConfig();

        final boolean enabled = getConfig().getBoolean("Simple-Double-Jump.Enabled");
        final double doubleJumpPower = getConfig().getDouble("Simple-Double-Jump.Double-Jump-Power");
        final double doubleYJumpPower = getConfig().getDouble("Simple-Double-Jump.Double-Jump-Y-Power");
        final Sound doubleJumpNoise = Sound.valueOf(getConfig().getString("Simple-Double-Jump.Double-Jump-Noise"));
        final double crouchPower = getConfig().getDouble("Simple-Double-Jump.Crouch-Power");
        final double crouchYPower = getConfig().getDouble("Simple-Double-Jump.Crouch-Y-Power");
        final Sound crouchNoise = Sound.valueOf(getConfig().getString("Simple-Double-Jump.Crouch-Noise"));
        final String reloadPermission = getConfig().getString("Simple-Double-Jump.Reload-Permission");

        this.options = new Options(enabled, doubleJumpPower, doubleYJumpPower, doubleJumpNoise, crouchPower, crouchYPower, crouchNoise, reloadPermission);

        if(doubleJumpHandler == null) {
            doubleJumpHandler = new DoubleJumpHandler(this);
        }
        if(options.isEnabled()) {
            Bukkit.getServer().getPluginManager().registerEvents(doubleJumpHandler, this);
            for (Player online : Bukkit.getOnlinePlayers()) {
                if(online.getGameMode() == GameMode.CREATIVE || online.getGameMode() == GameMode.SPECTATOR) {
                    continue;
                }
                online.setAllowFlight(true);
            }
        } else {
            HandlerList.unregisterAll(doubleJumpHandler);

            for (Player online : Bukkit.getOnlinePlayers()) {
                if(online.getGameMode() == GameMode.CREATIVE || online.getGameMode() == GameMode.SPECTATOR) {
                    continue;
                }
                online.setAllowFlight(false);
            }
        }
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}
