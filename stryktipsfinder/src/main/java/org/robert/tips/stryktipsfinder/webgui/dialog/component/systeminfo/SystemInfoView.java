package org.robert.tips.stryktipsfinder.webgui.dialog.component.systeminfo;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;

/**
 * View for the System info component that shows information about the searched
 * system and the system search path.
 * 
 * @author Robert
 * 
 */
public interface SystemInfoView {

	void setSystemInfo(RSystemSearchInfo selectedInfo);
}
