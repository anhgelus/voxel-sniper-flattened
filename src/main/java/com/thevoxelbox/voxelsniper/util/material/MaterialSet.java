package com.thevoxelbox.voxelsniper.util.material;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

public class MaterialSet implements Iterable<Material> {

    private final Set<Material> materials;

    public static MaterialSetBuilder builder() {
        return new MaterialSetBuilder();
    }

    public MaterialSet(final Collection<Material> materials) {
        this.materials = EnumSet.copyOf(materials);
    }

    public boolean contains(final Block block) {
        Material type = block.getType();
        return contains(type);
    }

    public boolean contains(final BlockData blockData) {
        Material material = blockData.getMaterial();
        return contains(material);
    }

    public boolean contains(final BlockState blockState) {
        Material type = blockState.getType();
        return contains(type);
    }

    public boolean contains(final Material material) {
        return this.materials.contains(material);
    }

    @Override
    public Iterator<Material> iterator() {
        return this.materials.iterator();
    }

    public Set<Material> getMaterials() {
        return Collections.unmodifiableSet(this.materials);
    }
}
