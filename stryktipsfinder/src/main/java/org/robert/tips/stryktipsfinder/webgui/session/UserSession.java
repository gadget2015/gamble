package org.robert.tips.stryktipsfinder.webgui.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;

/**
 * Defines a session storage for an actor, e.g associated with a specific
 * client.
 * 
 * @author Robert Georen.
 * 
 */
public class UserSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private long selectedSystem;

	private List<RSystemSearchInfo> allSystems;

	public void setAllSystems(List<RSystemSearchInfo> allSystems) {
		this.allSystems = allSystems;
	}

	public List<RSystemSearchInfo> getAllSystems() {
		if (allSystems == null) {
			allSystems = new ArrayList<RSystemSearchInfo>();
		}

		return allSystems;
	}

	public void setSelectedSystem(long selectedSystem) {
		this.selectedSystem = selectedSystem;
	}

	public long getSelectedSystem() {
		return selectedSystem;
	}
}
