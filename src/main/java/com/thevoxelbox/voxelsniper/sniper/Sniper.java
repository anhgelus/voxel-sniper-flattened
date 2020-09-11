package com.thevoxelbox.voxelsniper.sniper;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import com.thevoxelbox.voxelsniper.brush.Brush;
import com.thevoxelbox.voxelsniper.brush.PerformerBrush;
import com.thevoxelbox.voxelsniper.brush.property.BrushProperties;
import com.thevoxelbox.voxelsniper.sniper.snipe.Snipe;
import com.thevoxelbox.voxelsniper.sniper.snipe.message.SnipeMessenger;
import com.thevoxelbox.voxelsniper.sniper.toolkit.BlockTracer;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolAction;
import com.thevoxelbox.voxelsniper.sniper.toolkit.Toolkit;
import com.thevoxelbox.voxelsniper.sniper.toolkit.ToolkitProperties;
import com.thevoxelbox.voxelsniper.util.material.Materials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Nullable;

public class Sniper {

    private static final String DEFAULT_TOOLKIT_NAME = "default";

    private final UUID uuid;
    private boolean enabled = true;
    private final int undoCacheSize;
    private final Deque<Undo> undoList = new LinkedList<>();
    private final List<Toolkit> toolkits = new ArrayList<>();

    public Sniper(final UUID uuid, final int undoCacheSize) {
        this.uuid = uuid;
        this.undoCacheSize = undoCacheSize;
        Toolkit defaultToolkit = createDefaultToolkit();
        this.toolkits.add(defaultToolkit);
    }

    private Toolkit createDefaultToolkit() {
        Toolkit toolkit = new Toolkit("default");
        toolkit.addToolAction(Material.ARROW, ToolAction.ARROW);
        toolkit.addToolAction(Material.GUNPOWDER, ToolAction.GUNPOWDER);
        return toolkit;
    }

    public Player getPlayer() {
        Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            throw new UnknownSniperPlayerException();
        }
        return player;
    }

    @Nullable
    public Toolkit getCurrentToolkit() {
        Player player = getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInHand = inventory.getItemInMainHand();
        Material itemType = itemInHand.getType();
        if (Materials.isEmpty(itemType)) {
            return getToolkit(DEFAULT_TOOLKIT_NAME);
        }
        return getToolkit(itemType);
    }

    public void addToolkit(final Toolkit toolkit) {
        this.toolkits.add(toolkit);
    }

    @Nullable
    public Toolkit getToolkit(final Material itemType) {
        return this.toolkits.stream()
            .filter(toolkit -> toolkit.hasToolAction(itemType))
            .findFirst()
            .orElse(null);
    }

    @Nullable
    public Toolkit getToolkit(final String toolkitName) {
        return this.toolkits.stream()
            .filter(toolkit -> toolkitName.equals(toolkit.getToolkitName()))
            .findFirst()
            .orElse(null);
    }

    public void removeToolkit(final Toolkit toolkit) {
        this.toolkits.remove(toolkit);
    }

    /**
     * Sniper execution call.
     *
     * @param action Action player performed
     * @param usedItem Item in hand of player
     * @param clickedBlock Block that the player targeted/interacted with
     * @param clickedBlockFace Face of that targeted Block
     * @return true if command visibly processed, false otherwise.
     */
    public boolean snipe(final Player player, final Action action, final Material usedItem, @Nullable final Block clickedBlock, final BlockFace clickedBlockFace) {
        Toolkit toolkit = getToolkit(usedItem);
        if (toolkit == null) {
            return false;
        }
        ToolAction toolAction = toolkit.getToolAction(usedItem);
        if (toolAction == null) {
            return false;
        }
        BrushProperties currentBrushProperties = toolkit.getCurrentBrushProperties();
        String permission = currentBrushProperties.getPermission();
        if (permission != null && !player.hasPermission(permission)) {
            player.sendMessage("You are not allowed to use this brush. You're missing the permission node '" + permission + "'");
            return false;
        }
        ToolkitProperties toolkitProperties = toolkit.getProperties();
        BlockTracer blockTracer = toolkitProperties.createBlockTracer(player);
        Block targetBlock = clickedBlock == null ? blockTracer.getTargetBlock() : clickedBlock;
        if (player.isSneaking()) {
            SnipeMessenger messenger = new SnipeMessenger(toolkitProperties, currentBrushProperties, player);
            if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
                if (toolAction == ToolAction.ARROW) {
                    if (Materials.isEmpty(targetBlock.getType())) {
                        toolkitProperties.resetBlockData();
                    } else {
                        Material type = targetBlock.getType();
                        toolkitProperties.setBlockType(type);
                    }
                    messenger.sendBlockTypeMessage();
                    return true;
                } else if (toolAction == ToolAction.GUNPOWDER) {
                    if (Materials.isEmpty(targetBlock.getType())) {
                        toolkitProperties.resetBlockData();
                    } else {
                        BlockData blockData = targetBlock.getBlockData();
                        toolkitProperties.setBlockData(blockData);
                    }
                    messenger.sendBlockDataMessage();
                    return true;
                }
                return false;
            } else if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                if (toolAction == ToolAction.ARROW) {
                    if (targetBlock == null) {
                        toolkitProperties.resetReplaceBlockData();
                    } else {
                        Material type = targetBlock.getType();
                        toolkitProperties.setReplaceBlockType(type);
                    }
                    messenger.sendReplaceBlockTypeMessage();
                    return true;
                } else if (toolAction == ToolAction.GUNPOWDER) {
                    if (targetBlock == null) {
                        toolkitProperties.resetReplaceBlockData();
                    } else {
                        BlockData blockData = targetBlock.getBlockData();
                        toolkitProperties.setReplaceBlockData(blockData);
                    }
                    messenger.sendReplaceBlockDataMessage();
                    return true;
                }
                return false;
            }
            return false;
        } else {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (Materials.isEmpty(targetBlock.getType())) {
                    player.sendMessage(ChatColor.RED + "Snipe target block must be visible.");
                    return true;
                }
                Brush currentBrush = toolkit.getCurrentBrush();
                if (currentBrush == null) {
                    return false;
                }
                Snipe snipe = new Snipe(this, toolkit, toolkitProperties, currentBrushProperties, currentBrush);
                if (currentBrush instanceof PerformerBrush) {
                    PerformerBrush performerBrush = (PerformerBrush) currentBrush;
                    performerBrush.initialize(snipe);
                }
                Block lastBlock = clickedBlock == null ? blockTracer.getLastBlock() : clickedBlock.getRelative(clickedBlockFace);
                currentBrush.perform(snipe, toolAction, targetBlock, lastBlock);
                return true;
            }
        }
        return false;
    }

    public void storeUndo(final Undo undo) {
        if (this.undoCacheSize <= 0) {
            return;
        }
        if (undo.isEmpty()) {
            return;
        }
        while (this.undoList.size() >= this.undoCacheSize) {
            this.undoList.pollLast();
        }
        this.undoList.push(undo);
    }

    public void undo(final CommandSender sender, final int amount) {
        if (this.undoList.isEmpty()) {
            sender.sendMessage(ChatColor.GREEN + "There's nothing to undo.");
            return;
        }
        int sum = 0;
        for (int index = 0; index < amount && !this.undoList.isEmpty(); index++) {
            Undo undo = this.undoList.pop();
            undo.undo();
            sum += undo.getSize();
        }
        sender.sendMessage(ChatColor.GREEN + "Undo successful:  " + ChatColor.RED + sum + ChatColor.GREEN + " blocks have been replaced.");
    }

    public void sendInfo(final CommandSender sender) {
        Toolkit toolkit = getCurrentToolkit();
        if (toolkit == null) {
            sender.sendMessage("Current toolkit: none");
            return;
        }
        sender.sendMessage("Current toolkit: " + toolkit.getToolkitName());
        BrushProperties brushProperties = toolkit.getCurrentBrushProperties();
        Brush brush = toolkit.getCurrentBrush();
        if (brush == null) {
            sender.sendMessage("No brush selected.");
            return;
        }
        ToolkitProperties toolkitProperties = toolkit.getProperties();
        Snipe snipe = new Snipe(this, toolkit, toolkitProperties, brushProperties, brush);
        brush.sendInfo(snipe);
        if (brush instanceof PerformerBrush) {
            PerformerBrush performer = (PerformerBrush) brush;
            performer.sendPerformerInfo(snipe);
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
