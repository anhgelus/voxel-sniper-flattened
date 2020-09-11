package com.thevoxelbox.voxelsniper.brush.type.performer;

import com.thevoxelbox.voxelsniper.sniper.Sniper;
import com.thevoxelbox.voxelsniper.sniper.snipe.Snipe;
import com.thevoxelbox.voxelsniper.sniper.snipe.message.SnipeMessenger;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolkitProperties;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

public class RingBrush extends AbstractPerformerBrush {

    private double trueCircle;
    private double innerSize;

    @Override
    public void handleCommand(final String[] parameters, final Snipe snipe) {
        SnipeMessenger messenger = snipe.createMessenger();
        for (final String parameter : parameters) {
            if (parameter.equalsIgnoreCase("info")) {
                messenger.sendMessage(ChatColor.GOLD + "Ring Brush Parameters:");
                messenger.sendMessage(ChatColor.AQUA + "/b ri true -- will use a true circle algorithm instead of the skinnier version with classic sniper nubs. /b ri false will switch back. (false is default)");
                messenger.sendMessage(ChatColor.AQUA + "/b ri ir2.5 -- will set the inner radius to 2.5 units");
                return;
            } else if (parameter.startsWith("true")) {
                this.trueCircle = 0.5;
                messenger.sendMessage(ChatColor.AQUA + "True circle mode ON.");
            } else if (parameter.startsWith("false")) {
                this.trueCircle = 0;
                messenger.sendMessage(ChatColor.AQUA + "True circle mode OFF.");
            } else if (parameter.startsWith("ir")) {
                try {
                    this.innerSize = Double.parseDouble(parameter.replace("ir", ""));
                    messenger.sendMessage(ChatColor.AQUA + "The inner radius has been set to " + ChatColor.RED + this.innerSize);
                } catch (final NumberFormatException exception) {
                    messenger.sendMessage(ChatColor.RED + "The parameters included are invalid.");
                }
            } else {
                messenger.sendMessage(ChatColor.RED + "Invalid brush parameters! use the info parameter to display parameter info.");
            }
        }
    }

    @Override
    public void handleArrowAction(final Snipe snipe) {
        Block targetBlock = getTargetBlock();
        ring(snipe, targetBlock);
    }

    @Override
    public void handleGunpowderAction(final Snipe snipe) {
        Block lastBlock = getLastBlock();
        ring(snipe, lastBlock);
    }

    private void ring(final Snipe snipe, final Block targetBlock) {
        ToolkitProperties toolkitProperties = snipe.getToolkitProperties();
        int brushSize = toolkitProperties.getBrushSize();
        double outerSquared = Math.pow(brushSize + this.trueCircle, 2);
        double innerSquared = Math.pow(this.innerSize, 2);
        for (int x = brushSize; x >= 0; x--) {
            double xSquared = Math.pow(x, 2);
            for (int z = brushSize; z >= 0; z--) {
                double ySquared = Math.pow(z, 2);
                if (xSquared + ySquared <= outerSquared && xSquared + ySquared >= innerSquared) {
                    this.performer.perform(targetBlock.getRelative(x, 0, z));
                    this.performer.perform(targetBlock.getRelative(x, 0, -z));
                    this.performer.perform(targetBlock.getRelative(-x, 0, z));
                    this.performer.perform(targetBlock.getRelative(-x, 0, -z));
                }
            }
        }
        Sniper sniper = snipe.getSniper();
        sniper.storeUndo(this.performer.getUndo());
    }

    @Override
    public void sendInfo(final Snipe snipe) {
        snipe.createMessageSender()
            .brushNameMessage()
            .brushSizeMessage()
            .message(ChatColor.AQUA + "The inner radius is " + ChatColor.RED + this.innerSize)
            .send();
    }
}
