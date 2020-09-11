package com.thevoxelbox.voxelsniper.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.thevoxelbox.voxelsniper.command.property.CommandProperties;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

public class CommandRegistry {

    private final Plugin plugin;

    public CommandRegistry(final Plugin plugin) {
        this.plugin = plugin;
    }

    public void register(final CommandProperties properties, final CommandExecutor executor) {
        Command command = new Command(properties, executor);
        register(command);
    }

    public void register(final Command command) {
        Server server = this.plugin.getServer();
        CommandMap commandMap = getCommandMap(server);
        commandMap.register("voxel_sniper", command);
    }

    private CommandMap getCommandMap(final Server server) {
        try {
            Method method = server.getClass().getDeclaredMethod("getCommandMap");
            return (CommandMap) method.invoke(server);
        } catch (final NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }
}
