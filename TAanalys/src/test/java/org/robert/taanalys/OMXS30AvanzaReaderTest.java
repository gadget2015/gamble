package org.robert.taanalys;

import java.io.File;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.domain.model.InstrumentEvent;

public class OMXS30AvanzaReaderTest {

	
	@Test
	public void bordeHamtaIntrumentInfo_20140317() throws ConnectionProblem, ConversionException {
		// Given
		OMXS30AvanzaReader reader = new OMXS30AvanzaReader();
		File f = new File("");
		reader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20140317.htm";

		// When
		InstrumentEvent instrument = reader.readInstrumentInfo();

		// Then
		Assert.assertEquals("Borde vara rätt kurs.", 1349.39f,
				instrument.quote);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 17);
		now.set(Calendar.MINUTE, 30);
		Assert.assertEquals("Borde vara rätt tid.", "17:30", instrument.time.toString().substring(11,16));
	}
	
	@Test
	public void bordeHamtaIntrumentInfo_20130220() throws ConnectionProblem, ConversionException {
		// Given
		OMXS30AvanzaReader reader = new OMXS30AvanzaReader();
		File f = new File("");
		reader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20130220.htm";

		// When
		InstrumentEvent instrument = reader.readInstrumentInfo();

		// Then
		Assert.assertEquals("Borde vara rätt kurs.", 1198.33f,
				instrument.quote);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 17);
		now.set(Calendar.MINUTE, 30);
		Assert.assertEquals("Borde vara rätt tid.", "12:02", instrument.time.toString().substring(11,16));
	}
	
	@Test
	public void bordeHamtaIntrumentInfo_20130310() throws ConnectionProblem, ConversionException {
		// Given
		OMXS30AvanzaReader reader = new OMXS30AvanzaReader();
		File f = new File("");
		reader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20130310.htm";

		// When
		InstrumentEvent instrument = reader.readInstrumentInfo();

		// Then
		Assert.assertEquals("Borde vara rätt kurs.", 1215.14f,
				instrument.quote);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 17);
		now.set(Calendar.MINUTE, 30);
		Assert.assertEquals("Borde vara rätt tid.", "08:35", instrument.time.toString().substring(11,16));
	}
	
	@Test
	public void bordeHamtaIntrumentInfo_20130609() throws ConnectionProblem, ConversionException {
		// Given
		OMXS30AvanzaReader reader = new OMXS30AvanzaReader();
		File f = new File("");
		reader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20130609.htm";

		// When
		InstrumentEvent instrument = reader.readInstrumentInfo();

		// Then
		Assert.assertEquals("Borde vara rätt kurs.", 1192.98f,
				instrument.quote);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 17);
		now.set(Calendar.MINUTE, 30);
		Assert.assertEquals("Borde vara rätt tid.", "08:35", instrument.time.toString().substring(11,16));
	}
	
	@Test
	public void bordeHamtaIntrumentInfo_20130822() throws ConnectionProblem, ConversionException {
		// Given
		OMXS30AvanzaReader reader = new OMXS30AvanzaReader();
		File f = new File("");
		reader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/avanza_omxs30/OMXS30_20130822.htm";

		// When
		InstrumentEvent instrument = reader.readInstrumentInfo();

		// Then
		Assert.assertEquals("Borde vara rätt kurs.", 1237.59f,
				instrument.quote);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 17);
		now.set(Calendar.MINUTE, 30);
		Assert.assertEquals("Borde vara rätt tid.", "09:38", instrument.time.toString().substring(11,16));
	}
}
