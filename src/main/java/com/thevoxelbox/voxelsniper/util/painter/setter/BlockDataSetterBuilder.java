package com.thevoxelbox.voxelsniper.util.painter.setter;

import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class BlockDataSetterBuilder {

    private World world;
    private BlockData blockData;
    private boolean applyPhysics;

    public BlockDataSetterBuilder world(final Block block) {
        World world = block.getWorld();
        return world(world);
    }

    public BlockDataSetterBuilder world(final Location location) {
        World world = location.getWorld();
        Objects.requireNonNull(world);
        return world(world);
    }

    public BlockDataSetterBuilder world(final World world) {
        this.world = world;
        return this;
    }

    public BlockDataSetterBuilder blockData(final Material material) {
        BlockData blockData = material.createBlockData();
        return blockData(blockData);
    }

    public BlockDataSetterBuilder blockData(final BlockData blockData) {
        this.blockData = blockData;
        return this;
    }

    public BlockDataSetterBuilder applyPhysics() {
        this.applyPhysics = true;
        return this;
    }

    public BlockDataSetter build() {
        if (this.world == null) {
            throw new RuntimeException("World must be specified");
        }
        if (this.blockData == null) {
            throw new RuntimeException("Block data must be specified");
        }
        return new BlockDataSetter(this.world, this.blockData, this.applyPhysics);
    }
}
