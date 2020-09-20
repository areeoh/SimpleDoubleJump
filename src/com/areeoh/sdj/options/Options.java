package com.areeoh.sdj.options;

import org.bukkit.Sound;

public class Options {

    private final boolean enabled;
    private final double doubleJumpPower;
    private final double doubleYJumpPower;
    private final Sound doubleJumpNoise;
    private final double crouchPower;
    private final double crouchYPower;
    private final Sound crouchNoise;
    private final String reloadPermission;

    public Options(boolean enabled, double doubleJumpPower, double doubleYJumpPower, Sound doubleJumpNoise, double crouchPower, double crouchYPower, Sound crouchNoise, String reloadPermission) {
        this.enabled = enabled;
        this.doubleJumpPower = doubleJumpPower;
        this.doubleYJumpPower = doubleYJumpPower;
        this.doubleJumpNoise = doubleJumpNoise;
        this.crouchPower = crouchPower;
        this.crouchYPower = crouchYPower;
        this.crouchNoise = crouchNoise;
        this.reloadPermission = reloadPermission;
    }

    public double getCrouchYPower() {
        return crouchYPower;
    }

    public double getDoubleYJumpPower() {
        return doubleYJumpPower;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getDoubleJumpPower() {
        return doubleJumpPower;
    }

    public Sound getDoubleJumpNoise() {
        return doubleJumpNoise;
    }

    public double getCrouchPower() {
        return crouchPower;
    }

    public Sound getCrouchNoise() {
        return crouchNoise;
    }

    public String getReloadPermission() {
        return reloadPermission;
    }
}
