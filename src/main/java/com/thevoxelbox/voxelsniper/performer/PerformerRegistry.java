package com.thevoxelbox.voxelsniper.performer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thevoxelbox.voxelsniper.performer.property.PerformerProperties;
import org.jetbrains.annotations.Nullable;

public class PerformerRegistry {

    private final Map<String, PerformerProperties> performerProperties = new HashMap<>();

    public void register(final PerformerProperties properties) {
        List<String> aliases = properties.getAliases();
        for (final String alias : aliases) {
            this.performerProperties.put(alias, properties);
        }
    }

    @Nullable
    public PerformerProperties getPerformerProperties(final String alias) {
        return this.performerProperties.get(alias);
    }

    public Map<String, PerformerProperties> getPerformerProperties() {
        return Collections.unmodifiableMap(this.performerProperties);
    }
}
