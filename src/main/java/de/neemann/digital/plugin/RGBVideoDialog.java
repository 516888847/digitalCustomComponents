/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.plugin;

import de.neemann.digital.gui.components.graphics.MoveFocusTo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The dialog used to show the RGBVideo screen
 */
public class RGBVideoDialog extends JDialog {
    private final MyComponent graphicComponent;

    /**
     * Creates a new instance of the given size
     *
     * @param parent the parent window
     * @param title  the window title
     * @param image  the image data
     */
    private Dimension iSize;
    public RGBVideoDialog(Window parent, String title, BufferedImage image, int SizeScale) {
        super(parent, title, ModalityType.MODELESS);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        iSize = new Dimension(image.getWidth()*SizeScale+15,image.getHeight()*SizeScale+38);

        graphicComponent = new MyComponent(image,SizeScale);
        getContentPane().add(graphicComponent);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        addWindowFocusListener(new MoveFocusTo(parent));
    }

    /**
     * Updates the graphics data
     */
    public void updateGraphic() {
        setSize(iSize);
        graphicComponent.repaint();
    }

    private static final class MyComponent extends JComponent {
        private final BufferedImage image;
        private int iWidth;
        private int iHeight;
        private MyComponent(BufferedImage image , int SizeScale) {
            super();
            this.image = image;
            iWidth = image.getWidth() * SizeScale;
            iHeight = image.getHeight() * SizeScale;
            setPreferredSize(new Dimension( iWidth, iHeight));
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0,iWidth,iHeight, null);

        }
    }
}
