package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.stryktips.finder.desktopclient.dialog.SystemInfoModel;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.BusinessDelegateFactory;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

/**
 * View that displays search frame/dialog. This view contains other views.
 * 
 * @author Robert
 * 
 */
public class SearchSystemView extends JPanel {
	private static final long serialVersionUID = 1L;

	public SearchSystemView() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.ipady = 10;

		// System info
		SystemInfoModel model = new SystemInfoModel();
		model.systemId = Main.model.systemId;
		StryktipsFinderBD businessDelegate = new BusinessDelegateFactory()
				.getRuntimeBD();
		JPanel rootContent = new SystemInfoSubView(model, businessDelegate);
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(rootContent, constraints);

		// Search log
		constraints.gridx = 0;
		constraints.gridy = 1;
		SearchLogView logView = new SearchLogView();
		add(logView, constraints);

		// Action buttons.
		constraints.gridx = 0;
		constraints.gridy = 2;
		ButtonSubView actionView = new ButtonSubView(logView, model);
		add(actionView, constraints);
	}
}
