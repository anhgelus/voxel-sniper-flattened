package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.util.math.vector.Vector3i;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public final class Vectors {

    private Vectors() {
        throw new UnsupportedOperationException("Cannot create an instance of this class");
    }

    public static Vector3i of(final Block block) {
        return new Vector3i(block.getX(), block.getY(), block.getZ());
    }

    public static Vector3i of(final Location location) {
        return new Vector3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Vector3i of(final Vector vector) {
        return new Vector3i(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public static Vector toBukkit(final Vector3i vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }
}
