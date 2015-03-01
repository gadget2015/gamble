package org.robert.tips;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import org.robert.tips.exceptions.GeneralApplicationException;

/**
 * Entry point for the Gamble application.
 * @author Robert Siwerz.
 */
public class Main {

    private final static String ERROR_TITLE = "Error while starting application";

    public static void main(String[] args) {
        JFrame frame = null;
        try {
            frame = new MainFrame();
        } catch (GeneralApplicationException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    ERROR_TITLE,
                    JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
