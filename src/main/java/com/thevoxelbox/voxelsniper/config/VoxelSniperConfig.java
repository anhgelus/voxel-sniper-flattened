package com.thevoxelbox.voxelsniper.config;

import java.util.List;
import org.bukkit.Material;

public class VoxelSniperConfig {

    private final int undoCacheSize;
    private final boolean messageOnLoginEnabled;
    private final int litesniperMaxBrushSize;
    private final List<Material> litesniperRestrictedMaterials;

    public VoxelSniperConfig(final int undoCacheSize, final boolean messageOnLoginEnabled, final int litesniperMaxBrushSize, final List<Material> litesniperRestrictedMaterials) {
        this.undoCacheSize = undoCacheSize;
        this.messageOnLoginEnabled = messageOnLoginEnabled;
        this.litesniperMaxBrushSize = litesniperMaxBrushSize;
        this.litesniperRestrictedMaterials = litesniperRestrictedMaterials;
    }

    public int getUndoCacheSize() {
        return this.undoCacheSize;
    }

    public boolean isMessageOnLoginEnabled() {
        return this.messageOnLoginEnabled;
    }

    public int getLitesniperMaxBrushSize() {
        return this.litesniperMaxBrushSize;
    }

    public List<Material> getLitesniperRestrictedMaterials() {
        return this.litesniperRestrictedMaterials;
    }
}
