package de.neemann.digital.plugin;

import de.neemann.digital.draw.library.ComponentManager;
import de.neemann.digital.draw.library.ComponentSource;
import de.neemann.digital.draw.library.ElementLibrary;
import de.neemann.digital.draw.library.InvalidNodeException;
import de.neemann.digital.draw.shapes.GenericShape;
import de.neemann.digital.gui.Main;

/**
 * Adds some components to Digital
 */
public class NALibSource implements ComponentSource {

    /**
     * Attach your components to the simulator by calling the add methods
     *
     * @param manager the ComponentManager
     * @throws InvalidNodeException InvalidNodeException
     */
    @Override
    public void registerComponents(ComponentManager manager) throws InvalidNodeException {

        /*
        // add a component and use the default shape
        manager.addComponent("my folder/my sub folder", MyAnd.DESCRIPTION);

        // add a component and also provide a custom shape
        manager.addComponent("my folder/my sub folder", MyOr.DESCRIPTION, MyOrShape::new);

        // add a component and use the default shape
        manager.addComponent("my folder/my sub folder", MultiNot.DESCRIPTION);

        // add a component and use the default shape
        manager.addComponent("my folder/RAM", MultiPortRAM.DESCRIPTION,
                (attr, inputs, outputs) ->
                        new GenericShape("RAM", inputs, outputs, attr.getLabel(), true, 5));
        */

        manager.addComponent("NALib/IO", RectLedMatrix.DESCRIPTION);

        manager.addComponent("NALib/IO", RGBVideo.DESCRIPTION);

        manager.addComponent("NALib/Misc",HighZStat.DESCRIPTION);
    }

    /**
     * Start Digital with this ComponentSource attached to make debugging easier.
     * IMPORTANT: Remove the jar from Digitals settings!!!
     *
     * @param args args
     */
    public static void main(String[] args) {
        new Main.MainBuilder()
                .setLibrary(new ElementLibrary().registerComponentSource(new NALibSource()))
                .openLater();
    }
}
