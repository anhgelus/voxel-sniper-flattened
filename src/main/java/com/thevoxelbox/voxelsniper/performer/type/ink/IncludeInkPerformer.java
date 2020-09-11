package com.thevoxelbox.voxelsniper.performer.type.ink;

import java.util.List;
import com.thevoxelbox.voxelsniper.performer.type.AbstractPerformer;
import com.thevoxelbox.voxelsniper.sniper.Undo;
import com.thevoxelbox.voxelsniper.sniper.snipe.performer.PerformerSnipe;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolkitProperties;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class IncludeInkPerformer extends AbstractPerformer {

    private List<BlockData> includeList;
    private BlockData blockData;

    @Override
    public void initialize(final PerformerSnipe snipe) {
        ToolkitProperties toolkitProperties = snipe.getToolkitProperties();
        this.blockData = toolkitProperties.getBlockData();
        this.includeList = toolkitProperties.getVoxelList();
    }

    @Override
    public void perform(final Block block) {
        BlockData blockData = block.getBlockData();
        if (this.includeList.contains(blockData)) {
            Undo undo = getUndo();
            undo.put(block);
            block.setBlockData(this.blockData);
        }
    }

    @Override
    public void sendInfo(final PerformerSnipe snipe) {
        snipe.createMessageSender()
            .performerNameMessage()
            .voxelListMessage()
            .blockDataMessage()
            .send();
    }
}
