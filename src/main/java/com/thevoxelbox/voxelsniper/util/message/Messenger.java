package com.thevoxelbox.voxelsniper.util.message;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;

public class Messenger {

    private static final int BRUSH_SIZE_WARNING_THRESHOLD = 20;

    private final CommandSender sender;

    public Messenger(final CommandSender sender) {
        this.sender = sender;
    }

    public void sendBrushNameMessage(final String brushName) {
        sendMessage(ChatColor.AQUA + "Brush Type: " + ChatColor.LIGHT_PURPLE + brushName);
    }

    public void sendPerformerNameMessage(final String performerName) {
        sendMessage(ChatColor.DARK_PURPLE + "Performer: " + ChatColor.DARK_GREEN + performerName);
    }

    public void sendBlockTypeMessage(final Material blockType) {
        sendMessage(ChatColor.GOLD + "Voxel: " + ChatColor.RED + blockType.getKey());
    }

    public void sendBlockDataMessage(final BlockData blockData) {
        sendMessage(ChatColor.BLUE + "Data Variable: " + ChatColor.DARK_RED + blockData.getAsString(true));
    }

    public void sendReplaceBlockTypeMessage(final Material replaceBlockType) {
        sendMessage(ChatColor.AQUA + "Replace Material: " + ChatColor.RED + replaceBlockType.getKey());
    }

    public void sendReplaceBlockDataMessage(final BlockData replaceBlockData) {
        sendMessage(ChatColor.DARK_GRAY + "Replace Data Variable: " + ChatColor.DARK_RED + replaceBlockData.getAsString(true));
    }

    public void sendBrushSizeMessage(final int brushSize) {
        sendMessage(ChatColor.GREEN + "Brush Size: " + ChatColor.DARK_RED + brushSize);
        if (brushSize >= BRUSH_SIZE_WARNING_THRESHOLD) {
            sendMessage(ChatColor.RED + "WARNING: Large brush size selected!");
        }
    }

    public void sendCylinderCenterMessage(final int cylinderCenter) {
        sendMessage(ChatColor.DARK_BLUE + "Brush Center: " + ChatColor.DARK_RED + cylinderCenter);
    }

    public void sendVoxelHeightMessage(final int voxelHeight) {
        sendMessage(ChatColor.DARK_AQUA + "Brush Height: " + ChatColor.DARK_RED + voxelHeight);
    }

    public void sendVoxelListMessage(final List<? extends BlockData> voxelList) {
        if (voxelList.isEmpty()) {
            sendMessage(ChatColor.DARK_GREEN + "No blocks selected!");
        }
        String message = voxelList.stream()
            .map(blockData -> blockData.getAsString(true))
            .map(dataAsString -> dataAsString + " ")
            .collect(Collectors.joining("", ChatColor.DARK_GREEN + "Block Types Selected: " + ChatColor.AQUA, ""));
        sendMessage(message);
    }

    public void sendMessage(final String message) {
        this.sender.sendMessage(message);
    }
}
