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


/**
 * A shape for the new Or component
 */
public class RGBVideoShape implements Shape {

    private final PinDescriptions inputs;
    //private final Dimension ImageSize;
    private final int ImageSizeScale;
    private BufferedImage image;

    /**
     * Creates a new instance.
     *
     * @param elementAttributes the attributes of the component
     * @param inputs            the inputs
     * @param outputs           the outputs
     */
    public RGBVideoShape(ElementAttributes elementAttributes, PinDescriptions inputs, PinDescriptions outputs) {
        this.inputs = inputs;
        ImageSizeScale = elementAttributes.get(RGBVideo.KEY_SIZESCALE);
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Defines a positions for each input and output.
     * CAUTION: The coordinates needs to be multiple of the grid size!
     *
     * @return the pins to draw
     */
    @Override
    public Pins getPins() {
        return new Pins()
                .add(new Pin(vec(0, 0), inputs.get(0)))
                .add(new Pin(vec(0, SIZE * 2), inputs.get(1)))
                .add(new Pin(vec(0, SIZE * 3), inputs.get(2)))
                .add(new Pin(vec(0, SIZE * 4), inputs.get(3)))
                .add(new Pin(vec(0, SIZE * 5), inputs.get(4)));
    }

    /**
     * This method call connects the created model element to the shape which represents the model node.
     * If the look of the shape depends on an input or output state, the shape has to register the
     * guiObserver to all of the input or output ObservableValue instances it depends on.
     * To access the actual state while drawing, the Shape needs to store the IOState or the needed inputs
     * or outputs in a member variable.
     * This shape should reflect the output state, so we have to register the shape to the output value and
     * keep the {@link ObservableValue} representing the output for later use.
     * In this case null is returned because there is no user interaction with the shape.
     *
     * @param ioState     The state of the element. Is never null.
     * @return The interactor which is used to interact with the shape during the simulation.
     */
    @Override
    public InteractorInterface applyStateMonitor(IOState ioState) {
        return null;
    }

    /**
     * The draw method is not allowed to access the model, thus the draw method can not read the output state
     * of the OR gate. To do so, this method is used to read values from the model.
     * During execution of this method the model is locked. Thus this method should return as fast as possible.
     */


    /**
     * Draw the component.
     * This method is not allowed to access the model!
     * Use the readObservableValues method to pick the necessary values from the model.
     *
     * @param graphic   interface to draw to
     * @param highLight Null means no highlighting at all. If highlight is not null, highlighting is active.
     *                  The given style should be used to highlight the drawing.
     */
    @Override
    public void drawTo(Graphic graphic, Style highLight) {
    }
}
