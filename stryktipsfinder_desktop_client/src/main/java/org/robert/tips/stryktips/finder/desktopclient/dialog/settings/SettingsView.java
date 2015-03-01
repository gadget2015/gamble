package org.robert.tips.stryktips.finder.desktopclient.dialog.settings;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.robert.tips.exceptions.GeneralApplicationException;
import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.util.TextMessages;

/**
 * The Settings dialog.
 * 
 * @author Robert.
 * 
 */
public class SettingsView extends JDialog {
	private static final long serialVersionUID = 1L;
	JComboBox priorities;

	public SettingsView(Frame owner) throws GeneralApplicationException {
		super(owner, true);

		// General dialog configuration
		setSize(new Dimension(200, 110));
		TextMessages textMessages = TextMessages.getInstance();
		setTitle(textMessages.getText("SettingsTitle"));
		centerDialogOnScreen();

		// Add components.
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder());

		// Add priority settings.
		panel.add(new JLabel("Trådprioritet:"));
		priorities = new JComboBox(new String[] { "High", "Normal", "Low" });

		if (Main.model.threadPriority == Thread.MAX_PRIORITY) {
			priorities.setSelectedItem("High");
		} else if (Main.model.threadPriority == Thread.MIN_PRIORITY) {
			priorities.setSelectedItem("Low");
		} else {
			priorities.setSelectedItem("Normal");
		}

		panel.add(priorities);

		// Add buttons.
		JButton ok = new JButton(textMessages.getText("saveButton"));
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute when button is pressed
				String selected = (String) priorities.getSelectedItem();

				if ("High".equals(selected)) {
					Main.model.threadPriority = Thread.MAX_PRIORITY;
				} else if ("Low".equals(selected)) {
					Main.model.threadPriority = Thread.MIN_PRIORITY;
				} else {
					Main.model.threadPriority = Thread.NORM_PRIORITY;
				}

				setVisible(false);
			}
		});

		panel.add(ok);

		JButton cancel = new JButton(textMessages.getText("cancelButton"));
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute when button is pressed
				setVisible(false);
			}
		});

		panel.add(cancel);

		add(panel);
	}

	private void centerDialogOnScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();

		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}

		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
	}
}
