package com.thevoxelbox.voxelsniper.brush.type.stamp;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class StampBrushBlockWrapper {

    private final BlockData blockData;
    private int x;
    private int y;
    private int z;

    public StampBrushBlockWrapper(final Block block, final int x, final int y, final int z) {
        this.blockData = block.getBlockData();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockData getBlockData() {
        return this.blockData;
    }

    public int getX() {
        return this.x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(final int z) {
        this.z = z;
    }
}
