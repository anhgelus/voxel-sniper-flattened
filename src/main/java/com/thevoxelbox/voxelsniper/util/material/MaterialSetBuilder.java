package com.thevoxelbox.voxelsniper.util.material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.Tag;

public class MaterialSetBuilder {

    private final List<Material> materials = new ArrayList<>(1);

    public MaterialSetBuilder add(final Material material) {
        this.materials.add(material);
        return this;
    }

    public MaterialSetBuilder with(final Material... materials) {
        List<Material> list = Arrays.asList(materials);
        this.materials.addAll(list);
        return this;
    }

    public MaterialSetBuilder with(final Collection<Material> materials) {
        this.materials.addAll(materials);
        return this;
    }

    public MaterialSetBuilder with(final MaterialSet materialSet) {
        Set<Material> materials = materialSet.getMaterials();
        this.materials.addAll(materials);
        return this;
    }

    public MaterialSetBuilder with(final Tag<Material> tag) {
        Set<Material> materials = tag.getValues();
        this.materials.addAll(materials);
        return this;
    }

    public MaterialSetBuilder filtered(final Predicate<? super Material> filter) {
        List<Material> materials = Arrays.stream(Material.values())
            .filter(filter)
            .collect(Collectors.toList());
        this.materials.addAll(materials);
        return this;
    }

    public MaterialSet build() {
        return new MaterialSet(this.materials);
    }
}
