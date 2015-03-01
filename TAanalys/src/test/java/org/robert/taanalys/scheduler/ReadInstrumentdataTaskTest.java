package org.robert.taanalys.scheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.robert.taanalys.OMXS30AvanzaReader;
import org.robert.taanalys.OMXS30YahooReader;
import org.robert.taanalys.domain.model.InstrumentEvent;
import org.robert.taanalys.infrastructure.InstrumentRepository;

public class ReadInstrumentdataTaskTest {

	@Test
	public void bordeHamtaInstrumentdataIfranAvanzaDarTvaSajterFinnsUppsatta() {
		// Given
		InstrumentRepository repo = mock(InstrumentRepository.class);
		File f = new File("");
		OMXS30AvanzaReader avanzaReader = new OMXS30AvanzaReader();
		avanzaReader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20130822.htm";
		OMXS30YahooReader yahooReader = new OMXS30YahooReader();
		yahooReader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/yahoo_omxs30/OMXS30.htm";
		ReadInstrumentdataTask task = new ReadInstrumentdataTask();
		task.setReaders(avanzaReader, yahooReader);
		task.repository = repo;

		// When
		task.execute();

		// Then
		Assert.assertEquals("Borde gått bra att hämta data", TaskStatus.OK,
				task.status);
		InstrumentEvent event = new InstrumentEvent();
		event.name = "OMXS30";
		event.quote = 1237.59f;
		verify(repo).sparaInstrument(event);
	}

	@Test
	public void bordeMisslyckasHamtaInstrumentdata() {
		// Given
		InstrumentRepository repo = mock(InstrumentRepository.class);
		File f = new File("");
		OMXS30AvanzaReader avanzaReader = new OMXS30AvanzaReader();
		avanzaReader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30.htm_FEL";
		OMXS30YahooReader yahooReader = new OMXS30YahooReader();
		yahooReader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/yahoo_omxs30/OMXS30.htm_FEL";
		ReadInstrumentdataTask task = new ReadInstrumentdataTask();
		task.setReaders(avanzaReader, yahooReader);
		task.repository = repo;

		// When
		task.execute();

		// Then
		Assert.assertEquals("Borde INTE gå att hämta data", TaskStatus.FEL,
				task.status);
	}

//	@Test
	public void bordeHamtaInstrumentdataIfranYahooDarTvaSajterFinnsUppsatta() {
		// Given
		InstrumentRepository repo = mock(InstrumentRepository.class);
		File f = new File("");
		OMXS30AvanzaReader avanzaReader = new OMXS30AvanzaReader();
		avanzaReader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30.htm_FEL";
		OMXS30YahooReader yahooReader = new OMXS30YahooReader();
		yahooReader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/yahoo_omxs30/OMXS30.htm";
		ReadInstrumentdataTask task = new ReadInstrumentdataTask();
		task.setReaders(avanzaReader, yahooReader);
		task.repository = repo;

		// When
		task.execute();

		// Then
		Assert.assertEquals("Borde gått bra att hämta data", TaskStatus.OK,
				task.status);
		InstrumentEvent event = new InstrumentEvent();
		event.name = "OMXS30";
		event.quote = 1139.15f;
		verify(repo).sparaInstrument(event);
	}

}
