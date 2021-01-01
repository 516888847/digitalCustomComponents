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
import de.neemann.digital.core.element.Key;
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

    private static final Key.KeyInteger KEY_SIZESCALE = new Key.KeyInteger("GraphicScale", 1).setMin(1).setMax(32);
    
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(RGBVideo.class,
            input("X"),
            input("Y"),
            input("W"),
            input("RGB"),
            input("C").setClock())
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL)
            .addAttribute(Keys.GRAPHIC_WIDTH)
            .addAttribute(Keys.GRAPHIC_HEIGHT)
            .addAttribute(KEY_SIZESCALE)
            ;

    private ObservableValue InputXCoord;
    private ObservableValue InputYCoord;
    private ObservableValue InputWriteEnable;
    private ObservableValue InputRGB;
    private ObservableValue InputClock;
    private boolean lastClock;
    private BufferedImage image;
    private RGBVideoDialog graphicDialog;
    private String label;
    private int SizeW;
    private int SizeH;
    private int SizeScale;

    /**
     * Creates a new instance
     *
     * @param attr the attributes
     */
    public RGBVideo(ElementAttributes attr) {
        SizeH = Math.min(attr.get(Keys.GRAPHIC_WIDTH),4096);
        SizeW = Math.min(attr.get(Keys.GRAPHIC_HEIGHT),4096);
        SizeScale = Math.max(1,Math.min(attr.get(KEY_SIZESCALE),32));
        label = attr.getLabel();
        image = new BufferedImage(SizeW, SizeH, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        InputXCoord = inputs.get(0);
        InputYCoord = inputs.get(1);
        InputWriteEnable = inputs.get(2).checkBits(1, this ,2).addObserverToValue(this);
        InputRGB = inputs.get(3).checkBits(24,this,3).addObserverToValue(this);
        InputClock = inputs.get(4).checkBits(1, this, 4).addObserverToValue(this);

    }

    @Override
    public ObservableValues getOutputs() {
        return ObservableValues.EMPTY_LIST;
    }

    @Override
    public void readInputs() throws NodeException {
        boolean actClock = InputClock.getBool();
        boolean actWriteEnable = InputWriteEnable.getBool();
        if (actClock && !lastClock && actWriteEnable) {
            setPixel();
        }
        lastClock = actClock;
    }

    private void setPixel() {
        int xpos = Math.min((int)InputXCoord.getValue(),SizeW-1);
        int ypos = Math.min((int)InputYCoord.getValue(),SizeH-1);
        int RGBC = (int)InputRGB.getValue();
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
                    graphicDialog = new RGBVideoDialog(getModel().getWindowPosManager().getMainFrame(), "RGBVideo", image, SizeScale);
                    getModel().getWindowPosManager().register("RGBVideo_" + label, graphicDialog);
                }
                paintPending.set(false);
                graphicDialog.updateGraphic();
            });
        }
    }

}
