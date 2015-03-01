package org.robert.taanalys.domain.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class BearbetningsEnhetTest {

	@Test
	public void bordeDelauppITvaEnheter() {
		// Given
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 1, 9, 17, 10);
		Date fromDate = calendar.getTime();
		
		calendar.set(2013, 2, 15, 14, 5);
		Date toDate = calendar.getTime();
				
		// When
		List<BearbetningsEnhet> enheter= BearbetningsEnhet.delaUppArbetet(fromDate, toDate);
		
		// Then
		Assert.assertEquals("Borde vara två delar.", 2, enheter.size());
		Assert.assertEquals("Del 1.", "2013-02-09 17:10 till 2013-03-11 17:10" , enheter.get(0).toString());
		Assert.assertEquals("Del 2.", "2013-03-11 17:10 till 2013-03-15 14:05" , enheter.get(1).toString());
		
	}
	
	@Test
	public void bordeDelauppI6Enheter() {
		// Given
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 1, 1, 17, 10);
		Date fromDate = calendar.getTime();
		
		calendar.set(2013, 6, 1, 18, 5);
		Date toDate = calendar.getTime();
				
		// When
		List<BearbetningsEnhet> enheter= BearbetningsEnhet.delaUppArbetet(fromDate, toDate);
		
		// Then
		Assert.assertEquals("Borde vara 6 delar.", 6, enheter.size());
		Assert.assertEquals("Del 1.", "2013-02-01 17:10 till 2013-03-03 17:10" , enheter.get(0).toString());
		Assert.assertEquals("Del 2.", "2013-03-03 17:10 till 2013-04-02 17:10" , enheter.get(1).toString());
		Assert.assertEquals("Del 3.", "2013-04-02 17:10 till 2013-05-02 17:10" , enheter.get(2).toString());
		Assert.assertEquals("Del 4.", "2013-05-02 17:10 till 2013-06-01 17:10" , enheter.get(3).toString());
		Assert.assertEquals("Del 5.", "2013-06-01 17:10 till 2013-07-01 17:10" , enheter.get(4).toString());
		Assert.assertEquals("Del 6.", "2013-07-01 17:10 till 2013-07-01 18:05" , enheter.get(5).toString());
	}
}
