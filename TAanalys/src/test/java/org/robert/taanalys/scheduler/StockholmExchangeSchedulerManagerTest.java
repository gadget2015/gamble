package org.robert.taanalys.scheduler;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.infrastructure.InstrumentRepository;
import static org.mockito.Mockito.mock;

public class StockholmExchangeSchedulerManagerTest {

	@Test
	public void bordeUtforaEnTask() {
		// Given
		StockholmExchangeSchedulerManager scheduler = new StockholmExchangeSchedulerManagerMock();
		File f = new File("");
		scheduler.omxTask.readers[0].instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20130822.htm";
		scheduler.omxTask.repository = mock(InstrumentRepository.class);
		
		// When
		scheduler.executeTasks();

		// Then
		Assert.assertEquals("Borde vara ett OK result.", 1, scheduler.result()
				.size());
		Assert.assertEquals("Borde vara OMXS30.",
				"Det gick bra att hämta instrumentdata för 'OMXS30'.",
				scheduler.result().iterator().next().getMessage());
	}

	@Test
	public void bordeUtforaEnTask120GangerMenEndastVisaResultatOm100Korningar() {
		// Given
		StockholmExchangeSchedulerManager scheduler = new StockholmExchangeSchedulerManager();
		File f = new File("");
		scheduler.omxTask.readers[0].instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20130822.htm";
		scheduler.omxTask.repository = mock(InstrumentRepository.class);

		// When
		for (int i = 0; i < 120; i++) {
			scheduler.executeTasks();
		}

		// Then
		Assert.assertEquals(
				"Borde vara 100 stycken poster i resultatet, e.g listan ska inte växa i oöndlighet.",
				100, scheduler.result().size());
	}
}
