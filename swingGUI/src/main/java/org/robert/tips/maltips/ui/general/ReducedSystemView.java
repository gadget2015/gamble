package org.robert.tips.maltips.ui.general;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.util.TextMessages;
import org.robert.tips.types.GameTextMessages;
import org.robert.tips.maltips.MaltipsDocument;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * This is a component where the number of reduced rows in the system
 * is displayed in.
 * Uses the MVC pattern.
 * @author Robert Siwerz.
 */
public class ReducedSystemView extends JPanel implements ChangeListener,
        GameTextMessages {

    private MaltipsDocument maltipsDocument;
    private JTextField numberInReducedSystem;

    public ReducedSystemView(MaltipsDocument maltipsDocument) throws GeneralApplicationException {
        this.maltipsDocument = maltipsDocument;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        TextMessages textMessages = TextMessages.getInstance();


        // label
        add(new JLabel(textMessages.getText(NUMBER_IN_REDUCED_SYSTEM)), constraints);

        // text field.
        numberInReducedSystem = new JTextField(5);
        numberInReducedSystem.setEditable(false);
        add(numberInReducedSystem, constraints);

        maltipsDocument.addChangeListener(this);
    }

    /**
     * Implemented method from ChangeListener interface.
     */
    public void stateChanged(ChangeEvent event) {
        int value = maltipsDocument.getMaltipsSystem().getReducedSystem().size();

        numberInReducedSystem.setText(String.valueOf(value));
    }
}
