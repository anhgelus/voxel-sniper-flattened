package com.thevoxelbox.voxelsniper.performer.property;

import java.util.ArrayList;
import java.util.List;

public class PerformerPropertiesBuilder {

    private String name;
    private boolean usingReplaceMaterial;
    private final List<String> aliases = new ArrayList<>(1);
    private PerformerCreator creator;

    public PerformerPropertiesBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public PerformerPropertiesBuilder usingReplaceMaterial() {
        this.usingReplaceMaterial = true;
        return this;
    }

    public PerformerPropertiesBuilder alias(final String alias) {
        this.aliases.add(alias);
        return this;
    }

    public PerformerPropertiesBuilder creator(final PerformerCreator creator) {
        this.creator = creator;
        return this;
    }

    public PerformerProperties build() {
        if (this.name == null) {
            throw new RuntimeException("Performer name must be specified");
        }
        if (this.creator == null) {
            throw new RuntimeException("Performer creator must be specified");
        }
        return new PerformerProperties(this.name, this.usingReplaceMaterial, this.aliases, this.creator);
    }
}
