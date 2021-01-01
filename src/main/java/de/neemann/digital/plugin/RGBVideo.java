/*
 * Copyright (c) 2019 Helmut Neemann.
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.plugin;

import de.neemann.digital.core.*;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;
import de.neemann.digital.lang.Lang;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static de.neemann.digital.core.element.PinInfo.input;

/**
 * Analyzes RGBVideo signals
 */
public class RGBVideo extends Node implements Element {

    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(RGBVideo.class,
            input("XCoord"),
            input("YCoord"),
            input("Write"),
            input("Clock").setClock())
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL);

    private ObservableValue XCoord;
    private ObservableValue YCoord;
    private ObservableValue WriteEnable;
    private ObservableValue clock;
    private boolean lastClock;
    private BufferedImage image;
    private RGBVideoDialog graphicDialog;
    private String label;

    /**
     * Creates a new instance
     *
     * @param attr the attributes
     */
    public RGBVideo(ElementAttributes attr) {
        label = attr.getLabel();
        image = new BufferedImage(256, 256, BufferedImage.TYPE_BYTE_BINARY);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        XCoord = inputs.get(0).checkBits(8, this, 0).addObserverToValue(this);
        YCoord = inputs.get(1).checkBits(8, this, 1).addObserverToValue(this);
        WriteEnable = inputs.get(2).checkBits(1, this ,2).addObserverToValue(this);
        clock = inputs.get(3).checkBits(1, this, 3).addObserverToValue(this);
    }

    @Override
    public ObservableValues getOutputs() {
        return ObservableValues.EMPTY_LIST;
    }

    @Override
    public void readInputs() throws NodeException {
        boolean actClock = clock.getBool();
        if (actClock && !lastClock) {
            setPixel();
        }
        lastClock = actClock;
    }

    private void setPixel() {
        int xpos = (int)XCoord.getValue();
        int ypos = (int)YCoord.getValue();
        int RGBC = Color.WHITE.getRGB();
        image.setRGB(xpos, ypos, RGBC);
        updateGraphic();
    }


    @Override
    public void writeOutputs() throws NodeException {
    }

    @Override
    public void init(Model model) {
    }

    private final AtomicBoolean paintPending = new AtomicBoolean();

    private void updateGraphic() {
        if (paintPending.compareAndSet(false, true)) {
            SwingUtilities.invokeLater(() -> {
                if (graphicDialog == null || !graphicDialog.isVisible()) {
                    graphicDialog = new RGBVideoDialog(getModel().getWindowPosManager().getMainFrame(), "VVFF", image);
                    getModel().getWindowPosManager().register("VGA_" + label, graphicDialog);
                }
                paintPending.set(false);
                graphicDialog.updateGraphic();
            });
        }
    }

}
