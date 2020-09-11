package com.thevoxelbox.voxelsniper.command.property;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

public class CommandProperties {

    private static final String DEFAULT_DESCRIPTION = "";
    private static final Class<? extends CommandSender> DEFAULT_SENDER_TYPE = CommandSender.class;

    private final String name;
    @Nullable
    private final String description;
    @Nullable
    private final String permission;
    private final List<String> aliases;
    private final List<String> usageLines;
    @Nullable
    private final Class<? extends CommandSender> senderType;

    public static CommandPropertiesBuilder builder() {
        return new CommandPropertiesBuilder();
    }

    CommandProperties(final String name, @Nullable final String description, @Nullable final String permission, final List<String> aliases, final List<String> usageLines, @Nullable final Class<? extends CommandSender> senderType) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
        this.usageLines = usageLines;
        this.senderType = senderType;
    }

    public String getDescriptionOrDefault() {
        return this.description == null ? DEFAULT_DESCRIPTION : this.description;
    }

    public String getUsage() {
        return String.join("\n", this.usageLines);
    }

    public Class<? extends CommandSender> getSenderTypeOrDefault() {
        return this.senderType == null ? DEFAULT_SENDER_TYPE : this.senderType;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Nullable
    public String getPermission() {
        return this.permission;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public List<String> getUsageLines() {
        return this.usageLines;
    }

    @Nullable
    public Class<? extends CommandSender> getSenderType() {
        return this.senderType;
    }
}
