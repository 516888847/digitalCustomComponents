package de.neemann.digital.plugin;

import de.neemann.digital.core.Node;
import de.neemann.digital.core.NodeException;
import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.ObservableValues;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;

import static de.neemann.digital.core.element.PinInfo.input;

public class DelayOneTick extends Node implements Element {

    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(
            DelayOneTick.class,
            input("C").setClock(),
            input("D")
            )
            .addAttribute(Keys.ROTATE)  
            .addAttribute(Keys.BITS);  

    private final int bits;
    private final ObservableValue out;

    private ObservableValue InputClock;
    private ObservableValue InputData;
    private boolean lastClock;
    private long outValue;
    private long beforeValue;

    public DelayOneTick(ElementAttributes attr) {
        bits = attr.getBits();
        out = new ObservableValue("out", bits);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        InputClock = inputs.get(0).addObserverToValue(this).checkBits(1, this);
        InputData = inputs.get(1).addObserverToValue(this).checkBits(bits, this);
    }

    @Override
    public void readInputs() {
        boolean actClock = InputClock.getBool();
        if (actClock ^ lastClock) {
            outValue = beforeValue;
            beforeValue = InputData.getValue();
        }
        lastClock = actClock;
    }

    /**
     * This method is called if you have to update your output.
     * It is not allowed to read one of the inputs!!!
     */
    @Override
    public void writeOutputs() {
        out.setValue(outValue);
    }

    @Override
    public ObservableValues getOutputs() {
        return new ObservableValues(out);
    }
}
