/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.plugin;

import de.neemann.digital.lang.Lang;
import de.neemann.digital.gui.components.graphics.MoveFocusTo;

import javax.swing.*;
import java.awt.*;

/**
 * The LED matrix dialog
 */
public class RectLedMatrixDialog extends JDialog {

    private final RectLedMatrixComponent RectledMatrixComponent;

    /**
     * Create a new instance
     *
     * @param parent     the parent frame
     * @param dy         height of matrix
     * @param data       data
     * @param color      the LEDs color
     * @param ledPersist if true the LEDs light up indefinite
     */
    public RectLedMatrixDialog(JFrame parent, int dy, long[] data, Color color, boolean ledPersist) {
        super(parent, Lang.get("elem_LedMatrix"), false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        RectledMatrixComponent = new RectLedMatrixComponent(dy, data, color, ledPersist);
        getContentPane().add(RectledMatrixComponent);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        addWindowFocusListener(new MoveFocusTo(parent));
    }

    /**
     * Update the graphic
     *
     * @param colAddr col update
     * @param rowData updated data
     */
    public void updateGraphic(int colAddr, long rowData) {
        RectledMatrixComponent.updateGraphic(colAddr, rowData);
    }
}
