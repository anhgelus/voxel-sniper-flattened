package com.thevoxelbox.voxelsniper.brush.type.performer;

import java.util.Arrays;
import com.thevoxelbox.voxelsniper.PerformerRegistrar;
import com.thevoxelbox.voxelsniper.brush.PerformerBrush;
import com.thevoxelbox.voxelsniper.brush.type.AbstractBrush;
import com.thevoxelbox.voxelsniper.performer.Performer;
import com.thevoxelbox.voxelsniper.performer.PerformerRegistry;
import com.thevoxelbox.voxelsniper.performer.property.PerformerCreator;
import com.thevoxelbox.voxelsniper.performer.property.PerformerProperties;
import com.thevoxelbox.voxelsniper.sniper.snipe.Snipe;
import com.thevoxelbox.voxelsniper.sniper.snipe.performer.PerformerSnipe;

public abstract class AbstractPerformerBrush extends AbstractBrush implements PerformerBrush {

    private PerformerProperties performerProperties;
    protected Performer performer;

    public AbstractPerformerBrush() {
        this.performerProperties = PerformerRegistrar.DEFAULT_PERFORMER_PROPERTIES;
        PerformerCreator performerCreator = this.performerProperties.getCreator();
        this.performer = performerCreator.create();
    }

    @Override
    public void handlePerformerCommand(final String[] parameters, final Snipe snipe, final PerformerRegistry performerRegistry) {
        String parameter = parameters[0];
        PerformerProperties performerProperties = performerRegistry.getPerformerProperties(parameter);
        if (performerProperties == null) {
            super.handleCommand(hackTheArray(parameters), snipe);
            return;
        }
        this.performerProperties = performerProperties;
        PerformerCreator performerCreator = this.performerProperties.getCreator();
        this.performer = performerCreator.create();
        sendInfo(snipe);
        PerformerSnipe performerSnipe = new PerformerSnipe(snipe, this.performerProperties, this.performer);
        this.performer.sendInfo(performerSnipe);
        if (parameters.length > 1) {
            String[] additionalArguments = Arrays.copyOfRange(parameters, 1, parameters.length);
            super.handleCommand(hackTheArray(additionalArguments), snipe);
        }
    }

    /**
     * Padds an empty String to the front of the array.
     *
     * @param args Array to pad empty string in front of
     * @return padded array
     */
    private String[] hackTheArray(final String[] args) {
        String[] returnValue = new String[args.length + 1];
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            String arg = args[i];
            returnValue[i + 1] = arg;
        }
        return returnValue;
    }

    @Override
    public void initialize(final Snipe snipe) {
        PerformerSnipe performerSnipe = new PerformerSnipe(snipe, this.performerProperties, this.performer);
        this.performer.initialize(performerSnipe);
        this.performer.initializeUndo();
    }

    @Override
    public void sendPerformerInfo(final Snipe snipe) {
        PerformerSnipe performerSnipe = new PerformerSnipe(snipe, this.performerProperties, this.performer);
        this.performer.sendInfo(performerSnipe);
    }

    public PerformerProperties getPerformerProperties() {
        return this.performerProperties;
    }

    public Performer getPerformer() {
        return this.performer;
    }
}
