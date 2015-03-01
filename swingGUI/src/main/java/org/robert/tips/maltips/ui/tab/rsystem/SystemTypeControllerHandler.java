package org.robert.tips.maltips.ui.tab.rsystem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import org.robert.tips.maltips.MaltipsDocument;
import org.robert.tips.maltips.types.RSystemType;

/**
 * This is an eventhandler class for the comboboxes that handles the system type.
 * @author Robert Siwerz.
 */
class SystemTypeControllerHandler implements ActionListener {

    private MaltipsDocument maltipsDocument;

    /**
     * Contructor.
     * @param maltipsDocument Reference to the current stryktips document.
     */
    public SystemTypeControllerHandler(MaltipsDocument maltipsDocument) {
        this.maltipsDocument = maltipsDocument;
    }

    /**
     * This method is called everytime an action event is fired.
     * @param ActionEvent The event object
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        Object value = ((JComboBox) event.getSource()).getSelectedItem();
        RSystemType type = new RSystemType((String) value);
        maltipsDocument.getMaltipsSystem().getRsystem().setRSystemType(type);
        maltipsDocument.setDocumentIsDirty(true);
    }
}
