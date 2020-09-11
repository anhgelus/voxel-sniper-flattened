package com.thevoxelbox.voxelsniper.performer.type.material;

import com.thevoxelbox.voxelsniper.performer.type.AbstractPerformer;
import com.thevoxelbox.voxelsniper.sniper.Undo;
import com.thevoxelbox.voxelsniper.sniper.snipe.performer.PerformerSnipe;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolkitProperties;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class MaterialPerformer extends AbstractPerformer {

    private Material material;

    @Override
    public void initialize(final PerformerSnipe snipe) {
        ToolkitProperties toolkitProperties = snipe.getToolkitProperties();
        this.material = toolkitProperties.getBlockType();
    }

    @Override
    public void perform(final Block block) {
        if (block.getType() != this.material) {
            Undo undo = getUndo();
            undo.put(block);
            block.setType(this.material);
        }
    }

    @Override
    public void sendInfo(final PerformerSnipe snipe) {
        snipe.createMessageSender()
            .performerNameMessage()
            .blockTypeMessage()
            .send();
    }
}
