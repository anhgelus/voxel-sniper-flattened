package com.thevoxelbox.voxelsniper.listener;

import java.util.UUID;
import com.thevoxelbox.voxelsniper.VoxelSniperPlugin;
import com.thevoxelbox.voxelsniper.config.VoxelSniperConfig;
import com.thevoxelbox.voxelsniper.sniper.Sniper;
import com.thevoxelbox.voxelsniper.sniper.SniperRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

    private final VoxelSniperPlugin plugin;

    public PlayerJoinListener(final VoxelSniperPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @Override
    public void listen(final PlayerJoinEvent event) {
        VoxelSniperConfig config = this.plugin.getVoxelSniperConfig();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Sniper sniper = getSniperFromRegistry(uuid);
        if (config.isMessageOnLoginEnabled() && player.hasPermission("voxelsniper.sniper")) {
            sniper.sendInfo(player);
        }
    }

    private Sniper getSniperFromRegistry(final UUID uuid) {
        SniperRegistry sniperRegistry = this.plugin.getSniperRegistry();
        Sniper sniper = sniperRegistry.getSniper(uuid);
        if (sniper == null) {
            return registerNewSniper(uuid, sniperRegistry);
        }
        return sniper;
    }

    private Sniper registerNewSniper(final UUID uuid, final SniperRegistry sniperRegistry) {
        VoxelSniperConfig config = this.plugin.getVoxelSniperConfig();
        int undoCacheSize = config.getUndoCacheSize();
        Sniper newSniper = new Sniper(uuid, undoCacheSize);
        sniperRegistry.register(newSniper);
        return newSniper;
    }
}
