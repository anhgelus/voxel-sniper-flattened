package com.thevoxelbox.voxelsniper.command.property;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;

public class CommandPropertiesBuilder {

    private String name;
    private String description;
    private String permission;
    private final List<String> aliases = new ArrayList<>(0);
    private final List<String> usageMessages = new ArrayList<>(1);
    private Class<? extends CommandSender> senderType;

    public CommandPropertiesBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public CommandPropertiesBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public CommandPropertiesBuilder permission(final String permission) {
        this.permission = permission;
        return this;
    }

    public CommandPropertiesBuilder alias(final String alias) {
        this.aliases.add(alias);
        return this;
    }

    public CommandPropertiesBuilder usage(final String message) {
        this.usageMessages.add(message);
        return this;
    }

    public CommandPropertiesBuilder sender(final Class<? extends CommandSender> senderType) {
        this.senderType = senderType;
        return this;
    }

    public CommandProperties build() {
        if (this.name == null) {
            throw new RuntimeException("Command name must be specified");
        }
        return new CommandProperties(this.name, this.description, this.permission, this.aliases, this.usageMessages, this.senderType);
    }
}
