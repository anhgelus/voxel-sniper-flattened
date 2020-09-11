package com.thevoxelbox.voxelsniper.brush.type.performer;

import com.thevoxelbox.voxelsniper.sniper.Sniper;
import com.thevoxelbox.voxelsniper.sniper.Undo;
import com.thevoxelbox.voxelsniper.sniper.snipe.Snipe;
import com.thevoxelbox.voxelsniper.sniper.snipe.message.SnipeMessenger;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolkitProperties;
import com.thevoxelbox.voxelsniper.util.painter.Painters;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

/**
 * A brush that creates a solid ball.
 */
public class BallBrush extends AbstractPerformerBrush {

    private boolean trueCircle;

    @Override
    public void handleCommand(final String[] parameters, final Snipe snipe) {
        SnipeMessenger messenger = snipe.createMessenger();
        for (final String parameter : parameters) {
            if (parameter.equalsIgnoreCase("info")) {
                messenger.sendMessage(ChatColor.GOLD + "Ball Brush Parameters:");
                messenger.sendMessage(ChatColor.AQUA + "/b b true -- will use a true sphere algorithm instead of the skinnier version with classic sniper nubs. /b b false will switch back. (false is default)");
                return;
            } else if (parameter.startsWith("true")) {
                this.trueCircle = true;
                messenger.sendMessage(ChatColor.AQUA + "True circle mode ON.");
            } else if (parameter.startsWith("false")) {
                this.trueCircle = false;
                messenger.sendMessage(ChatColor.AQUA + "True circle mode OFF.");
            } else {
                messenger.sendMessage(ChatColor.RED + "Invalid brush parameters! use the info parameter to display parameter info.");
            }
        }
    }

    @Override
    public void handleArrowAction(final Snipe snipe) {
        Block targetBlock = getTargetBlock();
        ball(snipe, targetBlock);
    }

    @Override
    public void handleGunpowderAction(final Snipe snipe) {
        Block lastBlock = getLastBlock();
        ball(snipe, lastBlock);
    }

    private void ball(final Snipe snipe, final Block targetBlock) {
        ToolkitProperties toolkitProperties = snipe.getToolkitProperties();
        int brushSize = toolkitProperties.getBrushSize();
        Painters.sphere()
            .center(targetBlock)
            .radius(brushSize)
            .trueCircle(this.trueCircle)
            .blockSetter(position -> {
                Block block = clampY(position);
                this.performer.perform(block);
            })
            .paint();
        Sniper sniper = snipe.getSniper();
        Undo undo = this.performer.getUndo();
        sniper.storeUndo(undo);
    }

    @Override
    public void sendInfo(final Snipe snipe) {
        snipe.createMessageSender()
            .brushNameMessage()
            .brushSizeMessage()
            .send();
    }
}
