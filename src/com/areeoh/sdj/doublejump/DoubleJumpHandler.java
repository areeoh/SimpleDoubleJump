package com.areeoh.sdj.doublejump;

import com.areeoh.sdj.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DoubleJumpHandler implements Listener {

    private final Main instance;
    private final Set<UUID> sneakSet = Collections.newSetFromMap(new WeakHashMap<UUID, Boolean>());

    public DoubleJumpHandler(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        player.setAllowFlight(true);
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (event.getNewGameMode() == GameMode.CREATIVE || event.getNewGameMode() == GameMode.SPECTATOR) {
            return;
        }
        player.setAllowFlight(true);
    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (event.isSneaking()) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        if (!sneakSet.contains(player.getUniqueId())) {
            return;
        }
        Location location = player.getLocation();
        if (location.clone().subtract(0, 2, 0).getBlock().getType() != Material.AIR) {
            return;
        }
        sneakSet.remove(player.getUniqueId());

        for (int i = 0; i < 30; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D);
            double y = ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D);
            double z = ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D);

            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_NORMAL, true, (float) (location.getX() + x), (float) (location.getY() + y), (float) (location.getZ() + z), 0, 0, 0, 0, 1);
            for (Player online : player.getWorld().getPlayers()) {
                ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
            }
        }

        if (instance.getOptions().getCrouchYPower() == 0) {
            player.setVelocity(location.getDirection().multiply(instance.getOptions().getCrouchPower()));
        } else {
            player.setVelocity(location.getDirection().multiply(instance.getOptions().getCrouchPower()).setY(instance.getOptions().getCrouchYPower()));
        }
        Sound sound = instance.getOptions().getCrouchNoise();
        if (sound != null) {
            player.playSound(location, sound, 1.0F, 1.0F);
        }
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        event.setCancelled(true);
        Location location = player.getLocation();
        if (location.clone().subtract(0, 2, 0).getBlock().getType() == Material.AIR) {
            return;
        }
        sneakSet.add(player.getUniqueId());

        float radius = 0.7F;
        for (double i = 0; i < 25; i += 0.5) {
            float x = radius * (float)Math.sin(i);
            float z = radius * (float)Math.cos(i);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float)location.getX() + x, (float)location.getY(), (float)location.getZ() + z, 0, 0, 0, 0, 1);
            for (Player online : player.getWorld().getPlayers()) {
                ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
            }
        }

        player.setVelocity(location.getDirection().multiply(instance.getOptions().getDoubleJumpPower()).setY(instance.getOptions().getDoubleYJumpPower()));
        Sound sound = instance.getOptions().getDoubleJumpNoise();
        if (sound != null) {
            player.playSound(location, sound, 1.0F, 1.0F);
        }
    }
}
