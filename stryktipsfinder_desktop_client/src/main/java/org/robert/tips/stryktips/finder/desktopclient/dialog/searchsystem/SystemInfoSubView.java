package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

/**
 * Defines the view for the system info dialog where information like: system
 * name, type of algorithm and number or rows in reduced system.
 * 
 * @author Robert
 * 
 */
public class SystemInfoSubView extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private JLabel systemName = new JLabel();
	private String SYSTEM_NAME_LABEL = "Systemnamn: ";
	private JLabel numberOfRowsInMathematicalSystem = new JLabel();
	private String NUMBER_OF_ROWS_IN_MATHEMATICAL_SYSTEM = "Antal rader oreducerat: ";
	private JLabel numberOfRowsInRSystem = new JLabel();
	private String NUMBER_OF_ROWS_IN_RSYSTEM = "Antal rader reducerat: ";
	private JLabel algorithm = new JLabel();
	private String ALGORITHM_LABEL = "Algoritm: ";
	private SystemInfoModel model;
	private String SPEED = "Hastighet: ";
	private JLabel speed = new JLabel();
	private String ROWS_IN_CURRENT_SYSTEM = "Rader i system under test: ";
	private JLabel rowsInCurrentSystem = new JLabel();

	public SystemInfoSubView(SystemInfoModel model,
			StryktipsFinderBD businessDelegate) {
		this.model = model;

		setLayout(new GridBagLayout());
		EtchedBorder etchedBorder = new EtchedBorder();
		TitledBorder titledBorder = new TitledBorder(etchedBorder);
		titledBorder.setTitle("System Info");
		setBorder(titledBorder);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;

		// System name
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(systemName, constraints);

		// Number of rows system consists of
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(numberOfRowsInMathematicalSystem, constraints);

		// Number of rows system consists of in reduced form.
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(numberOfRowsInRSystem, constraints);

		// Algorithm type.
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(algorithm, constraints);

		// Speed type.
		constraints.gridx = 0;
		constraints.gridy = 4;
		add(speed, constraints);

		// Current number of rows in test system.
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(rowsInCurrentSystem, constraints);

		// Call controller
		InitSystemInfoViewController controller = new InitSystemInfoViewController(
				businessDelegate, model, this);
		controller.perform();

		// Add change listener
		this.model.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// Update view from model data.
		systemName.setText(SYSTEM_NAME_LABEL + model.searchInfo.getName());
		numberOfRowsInMathematicalSystem
				.setText(NUMBER_OF_ROWS_IN_MATHEMATICAL_SYSTEM
						+ model.searchInfo
								.getNumberOfRowsInMathematicalSystem()
						+ " rader.");
		numberOfRowsInRSystem.setText(NUMBER_OF_ROWS_IN_RSYSTEM
				+ model.searchInfo.getNumberOfRows() + " rader.");
		algorithm.setText(ALGORITHM_LABEL
				+ model.searchInfo.getTypeOfAlgorithm() + ".");

		speed.setText(SPEED + model.currentSpeed + " tester/sekund.");
		rowsInCurrentSystem.setText(ROWS_IN_CURRENT_SYSTEM + model.rowsInSystemUnderTest + " rader.");
	}

	public void showTechnicalException(String message) {
		JOptionPane.showMessageDialog(null, message, "Tekniskt fel",
				JOptionPane.ERROR_MESSAGE);
	}
}
