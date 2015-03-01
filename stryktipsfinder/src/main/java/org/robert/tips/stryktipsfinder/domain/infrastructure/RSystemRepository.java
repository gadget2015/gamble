package org.robert.tips.stryktipsfinder.domain.infrastructure;

import java.util.List;

import javax.ejb.Local;

import org.robert.tips.stryktipsfinder.domain.model.RSystemSearchInfo;

/**
 * Repository for working with the RSystem aggregate root.
 * 
 * @author Robert
 * 
 */
@Local
public interface RSystemRepository {
	/**
	 * Update a RSystem.
	 */
	RSystemSearchInfo update(RSystemSearchInfo rsystem);

	/**
	 * Find all active/on-going system searches. If none is found
	 * return an empty list.
	 */
	List<RSystemSearchInfo> findAllActiveRSystems();

	/**
	 * Find system search info with given database id.
	 */
	RSystemSearchInfo findSystem(long id);
	
	void insert(RSystemSearchInfo rsystem);
}
