package org.robert.taanalys;

import java.io.File;
import java.util.Calendar;

import junit.framework.Assert;

import org.robert.taanalys.domain.model.InstrumentEvent;

public class OMXS30YahooReaderTest {
	//@Test
	public void bordeHamtaIntrumentInfo() throws ConnectionProblem,
			ConversionException {
		// Given
		OMXS30YahooReader reader = new OMXS30YahooReader();
		File f = new File("");
		reader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/yahoo_omxs30/OMXS30.htm";
		// When
		InstrumentEvent instrument = reader.readInstrumentInfo();

		// Then
		Assert.assertEquals("Borde vara rätt kurs.", 1139.15f, instrument.quote);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 17);
		now.set(Calendar.MINUTE, 30);
		Assert.assertEquals("Borde vara rätt tid.", now.getTime().toString(),
				instrument.time.toString());

	}
}
