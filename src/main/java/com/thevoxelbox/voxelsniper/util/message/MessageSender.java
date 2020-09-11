package com.thevoxelbox.voxelsniper.util.message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;

public class MessageSender {

    private static final int BRUSH_SIZE_WARNING_THRESHOLD = 20;

    private final CommandSender sender;
    private final List<String> messages = new ArrayList<>(0);

    public MessageSender(final CommandSender sender) {
        this.sender = sender;
    }

    public MessageSender brushNameMessage(final String brushName) {
        this.messages.add(ChatColor.AQUA + "Brush Type: " + ChatColor.LIGHT_PURPLE + brushName);
        return this;
    }

    public MessageSender performerNameMessage(final String performerName) {
        this.messages.add(ChatColor.DARK_PURPLE + "Performer: " + ChatColor.DARK_GREEN + performerName);
        return this;
    }

    public MessageSender blockTypeMessage(final Material blockType) {
        this.messages.add(ChatColor.GOLD + "Voxel: " + ChatColor.RED + blockType.getKey());
        return this;
    }

    public MessageSender blockDataMessage(final BlockData blockData) {
        this.messages.add(ChatColor.BLUE + "Data Variable: " + ChatColor.DARK_RED + blockData.getAsString(true));
        return this;
    }

    public MessageSender replaceBlockTypeMessage(final Material replaceBlockType) {
        this.messages.add(ChatColor.AQUA + "Replace Material: " + ChatColor.RED + replaceBlockType.getKey());
        return this;
    }

    public MessageSender replaceBlockDataMessage(final BlockData replaceBlockData) {
        this.messages.add(ChatColor.DARK_GRAY + "Replace Data Variable: " + ChatColor.DARK_RED + replaceBlockData.getAsString(true));
        return this;
    }

    public MessageSender brushSizeMessage(final int brushSize) {
        this.messages.add(ChatColor.GREEN + "Brush Size: " + ChatColor.DARK_RED + brushSize);
        if (brushSize >= BRUSH_SIZE_WARNING_THRESHOLD) {
            this.messages.add(ChatColor.RED + "WARNING: Large brush size selected!");
        }
        return this;
    }

    public MessageSender cylinderCenterMessage(final int cylinderCenter) {
        this.messages.add(ChatColor.DARK_BLUE + "Brush Center: " + ChatColor.DARK_RED + cylinderCenter);
        return this;
    }

    public MessageSender voxelHeightMessage(final int voxelHeight) {
        this.messages.add(ChatColor.DARK_AQUA + "Brush Height: " + ChatColor.DARK_RED + voxelHeight);
        return this;
    }

    public MessageSender voxelListMessage(final List<? extends BlockData> voxelList) {
        if (voxelList.isEmpty()) {
            this.messages.add(ChatColor.DARK_GREEN + "No blocks selected!");
        }
        String message = voxelList.stream()
            .map(blockData -> blockData.getAsString(true))
            .map(dataAsString -> dataAsString + " ")
            .collect(Collectors.joining("", ChatColor.DARK_GREEN + "Block Types Selected: " + ChatColor.AQUA, ""));
        this.messages.add(message);
        return this;
    }

    public MessageSender message(final String message) {
        this.messages.add(message);
        return this;
    }

    public void send() {
        this.messages.forEach(this.sender::sendMessage);
    }
}
