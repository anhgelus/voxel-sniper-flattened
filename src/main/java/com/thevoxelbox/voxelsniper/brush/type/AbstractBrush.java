package com.thevoxelbox.voxelsniper.brush.type;

import com.thevoxelbox.voxelsniper.brush.Brush;
import com.thevoxelbox.voxelsniper.sniper.Sniper;
import com.thevoxelbox.voxelsniper.sniper.snipe.Snipe;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolAction;
import com.thevoxelbox.voxelsniper.util.math.vector.Vector3i;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public abstract class AbstractBrush implements Brush {

    protected static final int CHUNK_SIZE = 16;

    private Block targetBlock;
    private Block lastBlock;

    @Override
    public void handleCommand(final String[] parameters, final Snipe snipe) {
        Sniper sniper = snipe.getSniper();
        Player player = sniper.getPlayer();
        player.sendMessage(ChatColor.RED + "This brush does not accept additional parameters.");
    }

    @Override
    public void perform(final Snipe snipe, final ToolAction action, final Block targetBlock, final Block lastBlock) {
        this.targetBlock = targetBlock;
        this.lastBlock = lastBlock;
        if (action == ToolAction.ARROW) {
            handleArrowAction(snipe);
        } else if (action == ToolAction.GUNPOWDER) {
            handleGunpowderAction(snipe);
        }
    }

    public Block clampY(final Vector3i position) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        return clampY(x, y, z);
    }

    public Block clampY(final int x, final int y, final int z) {
        int clampedY = y;
        World world = this.targetBlock.getWorld();
        if (clampedY < 0) {
            clampedY = 0;
        } else {
            int maxHeight = world.getMaxHeight();
            if (clampedY > maxHeight) {
                clampedY = maxHeight;
            }
        }
        return getBlock(x, clampedY, z);
    }

    public Material getBlockType(final Vector3i position) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        return getBlockType(x, y, z);
    }

    public Material getBlockType(final int x, final int y, final int z) {
        Block block = getBlock(x, y, z);
        return block.getType();
    }

    public BlockData getBlockData(final Vector3i position) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        return getBlockData(x, y, z);
    }

    public BlockData getBlockData(final int x, final int y, final int z) {
        Block block = getBlock(x, y, z);
        return block.getBlockData();
    }

    public void setBlockType(final Vector3i position, final Material type) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        setBlockType(x, y, z, type);
    }

    public void setBlockType(final int x, final int y, final int z, final Material type) {
        Block block = getBlock(x, y, z);
        block.setType(type);
    }

    public void setBlockData(final Vector3i position, final BlockData blockData) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        setBlockData(x, y, z, blockData);
    }

    public void setBlockData(final int x, final int y, final int z, final BlockData blockData) {
        Block block = getBlock(x, y, z);
        block.setBlockData(blockData);
    }

    public Block getBlock(final Vector3i position) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        return getBlock(x, y, z);
    }

    public Block getBlock(final int x, final int y, final int z) {
        World world = getWorld();
        return world.getBlockAt(x, y, z);
    }

    public World getWorld() {
        return this.targetBlock.getWorld();
    }

    public Block getTargetBlock() {
        return this.targetBlock;
    }

    /**
     * @return Block before target Block.
     */
    public Block getLastBlock() {
        return this.lastBlock;
    }
}
