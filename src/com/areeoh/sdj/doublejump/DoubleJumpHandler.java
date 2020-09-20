package com.areeoh.sdj.doublejump;

import com.areeoh.sdj.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class DoubleJumpHandler implements Listener {

    private Main instance;

    public DoubleJumpHandler(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        player.setAllowFlight(true);
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if(event.getNewGameMode() == GameMode.CREATIVE || event.getNewGameMode() == GameMode.SPECTATOR) {
            return;
        }
        player.setAllowFlight(true);
    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if(event.isSneaking()) {
            return;
        }
        if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        event.setCancelled(true);
        if(player.getLocation().clone().subtract(0, 2, 0).getBlock().getType() != Material.AIR) {
            return;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(instance.getOptions().getCrouchPower()).setY(instance.getOptions().getCrouchYPower()));
        Sound sound = instance.getOptions().getCrouchNoise();
        if(sound != null) {
            player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
        }
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        event.setCancelled(true);
        if(player.getLocation().clone().subtract(0, 2, 0).getBlock().getType() == Material.AIR) {
            return;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(instance.getOptions().getDoubleJumpPower()).setY(instance.getOptions().getDoubleYJumpPower()));
        Sound sound = instance.getOptions().getDoubleJumpNoise();
        if(sound != null) {
            player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
        }
    }
}
