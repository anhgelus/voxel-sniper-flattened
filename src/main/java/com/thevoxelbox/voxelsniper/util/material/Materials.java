package com.thevoxelbox.voxelsniper.util.material;

import org.bukkit.Material;

public final class Materials {

    private Materials() {
        throw new UnsupportedOperationException("Cannot create instance of this class");
    }

    public static boolean isEmpty(final Material material) {
        return material == Material.AIR || material == Material.CAVE_AIR || material == Material.VOID_AIR;
    }
}
