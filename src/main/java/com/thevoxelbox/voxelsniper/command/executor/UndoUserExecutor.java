package com.thevoxelbox.voxelsniper.command.executor;

import com.thevoxelbox.voxelsniper.VoxelSniperPlugin;
import com.thevoxelbox.voxelsniper.command.CommandExecutor;
import com.thevoxelbox.voxelsniper.sniper.Sniper;
import com.thevoxelbox.voxelsniper.sniper.SniperRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UndoUserExecutor implements CommandExecutor {

    private final VoxelSniperPlugin plugin;

    public UndoUserExecutor(final VoxelSniperPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void executeCommand(final CommandSender sender, final String[] arguments) {
        SniperRegistry sniperRegistry = this.plugin.getSniperRegistry();
        Player player = Bukkit.getPlayer(arguments[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.GREEN + "Player not found.");
            return;
        }
        Sniper sniper = sniperRegistry.getSniper(player);
        if (sniper == null) {
            return;
        }
        sniper.undo(sender, 1);
    }
}
