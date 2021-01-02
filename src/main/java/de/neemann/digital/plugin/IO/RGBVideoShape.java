package de.neemann.digital.plugin;

import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.Value;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.PinDescriptions;
import de.neemann.digital.draw.elements.IOState;
import de.neemann.digital.draw.elements.Pin;
import de.neemann.digital.draw.elements.Pins;
import de.neemann.digital.draw.graphics.Graphic;
import de.neemann.digital.draw.graphics.Orientation;
import de.neemann.digital.draw.graphics.Style;
import de.neemann.digital.draw.shapes.InteractorInterface;
import de.neemann.digital.draw.shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static de.neemann.digital.draw.graphics.Vector.vec;
import static de.neemann.digital.draw.shapes.GenericShape.SIZE;
import static de.neemann.digital.draw.shapes.GenericShape.SIZE2;



public class RGBVideoShape implements Shape {

    private final PinDescriptions inputs;
    //private final Dimension ImageSize;
    private final int ImageSizeScale;
    private BufferedImage image;


    public RGBVideoShape(ElementAttributes elementAttributes, PinDescriptions inputs, PinDescriptions outputs) {
        this.inputs = inputs;
        ImageSizeScale = elementAttributes.get(RGBVideo.KEY_SIZESCALE);
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    }


    @Override
    public Pins getPins() {
        return new Pins()
                .add(new Pin(vec(0, 0), inputs.get(0)))
                .add(new Pin(vec(0, SIZE * 2), inputs.get(1)))
                .add(new Pin(vec(0, SIZE * 3), inputs.get(2)))
                .add(new Pin(vec(0, SIZE * 4), inputs.get(3)))
                .add(new Pin(vec(0, SIZE * 5), inputs.get(4)));
    }


    @Override
    public InteractorInterface applyStateMonitor(IOState ioState) {
        return null;
    }

    @Override
    public void drawTo(Graphic graphic, Style highLight) {
    }
}
