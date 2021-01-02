package de.neemann.digital.plugin;

import de.neemann.digital.core.Node;
import de.neemann.digital.core.NodeException;
import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.ObservableValues;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;
import de.neemann.digital.core.element.Key;

import static de.neemann.digital.core.element.PinInfo.input;

public class HighZStat extends Node implements Element {

    public static final Key<Boolean> USENOTOUT = new Key<>("UseNotOutput", false);
    public static final Key<Boolean> NOACTIVEOUT = new Key<>("NoActiveOut", false);

    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(
            HighZStat.class,
            input("D"),input("actv")
            )
            .addAttribute(Keys.ROTATE)
            .addAttribute(USENOTOUT)
            .addAttribute(NOACTIVEOUT)
            ;  

    private ObservableValue InputData;
    private ObservableValue InputSignal;
    private final ObservableValue out;
    private final boolean UseNotOutput;
    private final boolean NoActiveOut;
    private boolean Signal;
    private long outValue;

    public HighZStat(ElementAttributes attr) {
        UseNotOutput = attr.get(USENOTOUT);
        NoActiveOut = attr.get(NOACTIVEOUT);
        out = new ObservableValue("Stat", 1);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        InputData = inputs.get(0).addObserverToValue(this);
        InputSignal = inputs.get(1).addObserverToValue(this).checkBits(1, this ,1);
    }

    @Override
    public void readInputs() {
        if (Signal) {
            outValue = (InputData.getCopy().isHighZ())?(!UseNotOutput?1:0):((UseNotOutput)?1:0);
        }else{
            Signal = Signal | InputSignal.getBool();
            outValue = NoActiveOut ? 1 : 0;
        }

    }

    @Override
    public void writeOutputs() {
        out.setValue(outValue);
    }

    @Override
    public ObservableValues getOutputs() {
        return new ObservableValues(out);
    }
}
