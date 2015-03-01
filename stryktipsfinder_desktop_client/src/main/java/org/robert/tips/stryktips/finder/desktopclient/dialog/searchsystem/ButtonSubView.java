package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.robert.tips.stryktips.finder.desktopclient.dialog.Controller;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.BusinessDelegateFactory;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

/**
 * Shows buttons.
 * 
 * @author Robert.
 * 
 */
public class ButtonSubView extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	SearchLogView searchLogView;
	SystemInfoModel systemInfoModel;
	JButton startSearch;
	JButton stopSearch;

	public ButtonSubView(SearchLogView searchLogView,
			SystemInfoModel systemInfoModel) {
		this.searchLogView = searchLogView;
		this.systemInfoModel = systemInfoModel;

		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;

		// Start search button.
		startSearch = new JButton("Börja söka");
		startSearch.addActionListener(new StartFindSystemAction());

		add(startSearch, constraints);

		// Stop search button.
		stopSearch = new JButton("Stoppa sökning");
		stopSearch.addActionListener(new StopFindSystemAction());
		add(stopSearch, constraints);
		
		// Add change listener
		this.systemInfoModel.addChangeListener(this);
	}

	/**
	 * Swing GUI Button action.
	 * 
	 * @author Robert
	 * 
	 */
	class StartFindSystemAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			BusinessDelegateFactory bdFactory = new BusinessDelegateFactory();
			StryktipsFinderBD stryktipsBD = bdFactory.getRuntimeBD();

			Controller controller = new StartFindSystemController(
					searchLogView, stryktipsBD, systemInfoModel);
			controller.perform();
		}
	}

	/**
	 * Swing GUI Button action.
	 * 
	 * @author Robert
	 * 
	 */
	class StopFindSystemAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Controller controller = new StopFindSystemController(searchLogView,
					systemInfoModel);
			controller.perform();
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// Update view from model data.
		if (systemInfoModel.isSearching) {
			startSearch.setEnabled(false);
			stopSearch.setEnabled(true);
		} else {
			startSearch.setEnabled(true);
			stopSearch.setEnabled(false);
		}
	}
}
