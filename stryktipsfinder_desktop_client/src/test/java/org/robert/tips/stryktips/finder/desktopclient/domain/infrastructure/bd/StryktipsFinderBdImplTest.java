package org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.RSystemSearchInfo;

/**
 * Integration tests, e.g call webservice.
 * 
 * @author Robert.
 * 
 */
public class StryktipsFinderBdImplTest {
	StryktipsFinderBD bd = new StryktipsFinderBDImpl();

	@Test
	public void shouldNotTestAnything() {
		
	}
	//@Test
	public void shouldGetOneRSystemSearchInfo() throws Exception {
		// Given...load testdata from sql-sript.		

		// When
		RSystemSearchInfo systemInfo = bd.getSystemInfoWithOnlyLatestSystem(1);
		
		// Then
		Assert.assertNotNull(systemInfo);
		Assert.assertEquals("Should be correct name.", "R-2-1", systemInfo.getName());
		Assert.assertEquals("Should be one system.", 1, systemInfo.getSystems().size());
	}
}
