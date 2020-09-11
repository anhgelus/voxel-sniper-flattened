package com.thevoxelbox.voxelsniper.brush.type.blend;

import java.util.Map;
import java.util.Map.Entry;
import com.thevoxelbox.voxelsniper.brush.type.AbstractBrush;
import com.thevoxelbox.voxelsniper.sniper.Undo;
import com.thevoxelbox.voxelsniper.sniper.snipe.Snipe;
import com.thevoxelbox.voxelsniper.sniper.snipe.message.SnipeMessenger;
import com.thevoxelbox.voxelsniper.util.material.Materials;
import com.thevoxelbox.voxelsniper.util.math.vector.Vector3i;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public abstract class AbstractBlendBrush extends AbstractBrush {

    private boolean airExcluded = true;
    private boolean waterExcluded = true;

    @Override
    public void handleCommand(final String[] parameters, final Snipe snipe) {
        SnipeMessenger messenger = snipe.createMessenger();
        for (final String parameter : parameters) {
            if (parameter.equalsIgnoreCase("water")) {
                this.waterExcluded = !this.waterExcluded;
                messenger.sendMessage(ChatColor.AQUA + "Water Mode: " + (this.waterExcluded ? "exclude" : "include"));
            }
        }
    }

    @Override
    public void handleArrowAction(final Snipe snipe) {
        this.airExcluded = false;
        blend(snipe);
    }

    @Override
    public void handleGunpowderAction(final Snipe snipe) {
        this.airExcluded = true;
        blend(snipe);
    }

    public abstract void blend(Snipe snipe);

    protected void setBlocks(final Map<Vector3i, Material> materials, final Undo undo) {
        for (final Entry<Vector3i, Material> entry : materials.entrySet()) {
            Vector3i position = entry.getKey();
            Material material = entry.getValue();
            if (checkExclusions(material)) {
                Material currentBlockType = getBlockType(position);
                if (currentBlockType != material) {
                    Block clamped = clampY(position);
                    undo.put(clamped);
                }
                setBlockType(position, material);
            }
        }
    }

    protected CommonMaterial findCommonMaterial(final Map<Material, Integer> materialsFrequencies) {
        CommonMaterial commonMaterial = new CommonMaterial();
        for (final Entry<Material, Integer> entry : materialsFrequencies.entrySet()) {
            Material material = entry.getKey();
            int frequency = entry.getValue();
            if (frequency > commonMaterial.getFrequency() && checkExclusions(material)) {
                commonMaterial.setMaterial(material);
                commonMaterial.setFrequency(frequency);
            }
        }
        return commonMaterial;
    }

    private boolean checkExclusions(final Material material) {
        return (!this.airExcluded || !Materials.isEmpty(material)) && (!this.waterExcluded || material != Material.WATER);
    }

    @Override
    public void sendInfo(final Snipe snipe) {
        snipe.createMessageSender()
            .brushNameMessage()
            .brushSizeMessage()
            .blockTypeMessage()
            .message(ChatColor.BLUE + "Water Mode: " + (this.waterExcluded ? "exclude" : "include"))
            .send();
    }
}
