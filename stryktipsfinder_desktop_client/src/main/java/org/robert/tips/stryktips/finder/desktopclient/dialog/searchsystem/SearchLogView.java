package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Displays search log.
 * 
 * @author Robert
 * 
 */
public class SearchLogView extends JPanel {
	private static final long serialVersionUID = 1L;
	JTextArea textArea = new JTextArea(5, 30);
	
	public SearchLogView() {
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;

		// System name
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(new JLabel("Log:"), constraints);
		
		// Log data
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		//setPreferredSize(new Dimension(400, 110));
		add(scrollPane, constraints);
	}
}
