package de.neemann.digital.plugin;

import de.neemann.digital.gui.components.graphics.MoveFocusTo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class RGBVideoDialog extends JDialog {
    private final MyComponent graphicComponent;

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
