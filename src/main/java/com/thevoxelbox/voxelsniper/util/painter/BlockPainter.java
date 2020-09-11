package com.thevoxelbox.voxelsniper.util.painter;

import java.util.ArrayList;
import java.util.List;
import com.thevoxelbox.voxelsniper.util.math.vector.Vector3i;

public class BlockPainter implements Painter {

    private final Vector3i center;
    private final BlockSetter blockSetter;
    private final List<Vector3i> shifts = new ArrayList<>();

    public BlockPainter(final Vector3i center, final BlockSetter blockSetter) {
        this.center = center;
        this.blockSetter = blockSetter;
    }

    public BlockPainter at(final int xShift, final int yShift, final int zShift) {
        Vector3i shift = new Vector3i(xShift, yShift, zShift);
        return at(shift);
    }

    public BlockPainter at(final Vector3i shift) {
        this.shifts.add(shift);
        return this;
    }

    @Override
    public void paint() {
        this.shifts.forEach(this::paintBlock);
    }

    private void paintBlock(final Vector3i shift) {
        Vector3i position = this.center.plus(shift);
        this.blockSetter.setBlockAt(position);
    }

    @Override
    public Vector3i getCenter() {
        return this.center;
    }

    @Override
    public BlockSetter getBlockSetter() {
        return this.blockSetter;
    }
}
