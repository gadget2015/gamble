package org.robert.tips.stryktips.finder.desktopclient;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * This is the about dialog.
 * 
 * @author Robert Siwerz.
 */
public class AboutDialog extends JPanel {
	private static final long serialVersionUID = 1L;

	public AboutDialog() {
		JLabel jLabel1 = new JLabel(
				"Stryktips system finder, version 0.1 (2011-12-21)");
		JLabel jLabel2 = new JLabel("Robert Georen Siwerz");
		JLabel jLabel3 = new JLabel("Copyright (c), 2011");
		setLayout(new GridLayout(3, 1));
		setBorder(new EtchedBorder());
		add(jLabel1);
		add(jLabel2);
		add(jLabel3);
	}
}
