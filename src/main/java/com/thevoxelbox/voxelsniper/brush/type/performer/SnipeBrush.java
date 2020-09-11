package com.thevoxelbox.voxelsniper.brush.type.performer;

import com.thevoxelbox.voxelsniper.sniper.Sniper;
import com.thevoxelbox.voxelsniper.sniper.snipe.Snipe;
import com.thevoxelbox.voxelsniper.sniper.snipe.message.SnipeMessenger;
import org.bukkit.block.Block;

public class SnipeBrush extends AbstractPerformerBrush {

    @Override
    public void handleArrowAction(final Snipe snipe) {
        Block targetBlock = getTargetBlock();
        this.performer.perform(targetBlock);
        Sniper sniper = snipe.getSniper();
        sniper.storeUndo(this.performer.getUndo());
    }

    @Override
    public void handleGunpowderAction(final Snipe snipe) {
        Block lastBlock = getLastBlock();
        this.performer.perform(lastBlock);
        Sniper sniper = snipe.getSniper();
        sniper.storeUndo(this.performer.getUndo());
    }

    @Override
    public void sendInfo(final Snipe snipe) {
        SnipeMessenger messenger = snipe.createMessenger();
        messenger.sendBrushNameMessage();
    }
}
