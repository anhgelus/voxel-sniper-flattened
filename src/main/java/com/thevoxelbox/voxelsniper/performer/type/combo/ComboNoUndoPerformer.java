package com.thevoxelbox.voxelsniper.performer.type.combo;

import com.thevoxelbox.voxelsniper.performer.type.AbstractPerformer;
import com.thevoxelbox.voxelsniper.sniper.snipe.performer.PerformerSnipe;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolkitProperties;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class ComboNoUndoPerformer extends AbstractPerformer {

    private BlockData blockData;

    @Override
    public void initialize(final PerformerSnipe snipe) {
        ToolkitProperties toolkitProperties = snipe.getToolkitProperties();
        this.blockData = toolkitProperties.getBlockData();
    }

    @Override
    public void perform(final Block block) {
        BlockData blockData = block.getBlockData();
        if (blockData.equals(this.blockData)) {
            block.setBlockData(this.blockData);
        }
    }

    @Override
    public void sendInfo(final PerformerSnipe snipe) {
        snipe.createMessageSender()
            .performerNameMessage()
            .blockTypeMessage()
            .blockDataMessage()
            .send();
    }
}
