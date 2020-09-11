package com.thevoxelbox.voxelsniper.sniper.toolkit;

import java.util.Iterator;
import com.thevoxelbox.voxelsniper.util.material.Materials;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class BlockTracer {

    private Block targetBlock;
    private Block lastBlock;

    BlockTracer(final Player player, final int distance) {
        Location eyeLocation = player.getEyeLocation();
        Block block = eyeLocation.getBlock();
        this.targetBlock = block;
        this.lastBlock = block;
        Iterator<Block> iterator = new BlockIterator(player, distance);
        iterate(iterator);
    }

    private void iterate(final Iterator<? extends Block> iterator) {
        while (iterator.hasNext()) {
            Block block = iterator.next();
            this.lastBlock = this.targetBlock;
            this.targetBlock = block;
            if (!Materials.isEmpty(block.getType())) {
                return;
            }
        }
    }

    public Block getTargetBlock() {
        return this.targetBlock;
    }

    public Block getLastBlock() {
        return this.lastBlock;
    }
}
