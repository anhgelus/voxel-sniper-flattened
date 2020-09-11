package com.thevoxelbox.voxelsniper.command;

import java.util.List;
import com.thevoxelbox.voxelsniper.command.property.CommandProperties;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Command extends org.bukkit.command.Command {

    private final CommandProperties properties;
    private final CommandExecutor executor;
    private TabCompleter tabCompleter;

    public Command(final CommandProperties properties, final CommandExecutor executor) {
        super(properties.getName(), properties.getDescriptionOrDefault(), properties.getUsage(), properties.getAliases());
        setupPermission(properties);
        this.properties = properties;
        this.executor = executor;
        if (executor instanceof TabCompleter) {
            this.tabCompleter = (TabCompleter) executor;
        }
    }

    private void setupPermission(final CommandProperties properties) {
        String permission = properties.getPermission();
        setPermission(permission);
        setPermissionMessage(ChatColor.RED + "Insufficient permissions.");
    }

    @Override
    public boolean execute(@NotNull final CommandSender sender, @NotNull final String commandLabel, @NotNull final String[] args) {
        Class<? extends CommandSender> senderType = this.properties.getSenderTypeOrDefault();
        if (!senderType.isInstance(sender)) {
            sender.sendMessage(ChatColor.RED + "Only " + senderType.getSimpleName() + " can execute this command.");
            return true;
        }
        String permission = this.properties.getPermission();
        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "Insufficient permissions.");
            return true;
        }
        this.executor.executeCommand(sender, args);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String alias, @NotNull final String[] args, @Nullable final Location location) {
        if (this.tabCompleter == null) {
            return super.tabComplete(sender, alias, args, location);
        }
        return this.tabCompleter.complete(sender, args);
    }
}
