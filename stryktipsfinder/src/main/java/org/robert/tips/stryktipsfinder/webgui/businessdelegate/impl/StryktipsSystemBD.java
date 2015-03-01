package org.robert.tips.stryktipsfinder.webgui.businessdelegate.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.robert.tips.stryktipsfinder.domain.infrastructure.RSystemRepository;
import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;
import org.robert.tips.stryktipsfinder.webgui.businessdelegate.StryktipsSystemBusinessDelegate;

/**
 * Delegates call to repository.
 * 
 * @author Robert
 * 
 */
@Stateless
public class StryktipsSystemBD implements StryktipsSystemBusinessDelegate {
	@EJB
	RSystemRepository repository;

	/**
	 * EJB 3.x default constructor.
	 */
	public StryktipsSystemBD() {
	}

	@Override
	public List<RSystemSearchInfo> getAllRSystems() {
		return repository.findAllActiveRSystems();
	}

}
