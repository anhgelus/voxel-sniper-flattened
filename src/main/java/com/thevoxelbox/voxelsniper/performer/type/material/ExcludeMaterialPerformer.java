package com.thevoxelbox.voxelsniper.performer.type.material;

import java.util.List;
import com.thevoxelbox.voxelsniper.performer.type.AbstractPerformer;
import com.thevoxelbox.voxelsniper.sniper.Undo;
import com.thevoxelbox.voxelsniper.sniper.snipe.performer.PerformerSnipe;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolkitProperties;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class ExcludeMaterialPerformer extends AbstractPerformer {

    private List<BlockData> excludeList;
    private Material type;

    @Override
    public void initialize(final PerformerSnipe snipe) {
        ToolkitProperties toolkitProperties = snipe.getToolkitProperties();
        this.type = toolkitProperties.getBlockType();
        this.excludeList = toolkitProperties.getVoxelList();
    }

    @Override
    public void perform(final Block block) {
        BlockData blockData = block.getBlockData();
        if (!this.excludeList.contains(blockData)) {
            Undo undo = getUndo();
            undo.put(block);
            block.setType(this.type);
        }
    }

    @Override
    public void sendInfo(final PerformerSnipe snipe) {
        snipe.createMessageSender()
            .performerNameMessage()
            .voxelListMessage()
            .blockTypeMessage()
            .send();
    }
}
