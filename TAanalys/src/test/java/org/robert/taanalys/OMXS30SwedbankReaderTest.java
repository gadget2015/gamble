package org.robert.taanalys;

import java.io.File;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.domain.model.InstrumentEvent;

public class OMXS30SwedbankReaderTest {

	@Test
	public void bordeHamtaIntrumentInfo_20130310() throws ConnectionProblem,
			ConversionException {
		// Given
		OMXS30SwedbankReader reader = new OMXS30SwedbankReader();
		File f = new File("");
		reader.instrumentURI = "file:///"
				+ f.getAbsolutePath().replaceAll("\\\\", "/")
				+ "/src/test/resources/swedbank/OMXS30_20130311.htm";

		// When
		InstrumentEvent instrument = reader.readInstrumentInfo();

		// Then
		Assert.assertEquals("Borde vara rätt kurs.", 1218.077f, instrument.quote);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 17);
		now.set(Calendar.MINUTE, 30);
		Assert.assertEquals("Borde vara rätt tid.", "17:30", instrument.time
				.toString().substring(11, 16));
	}
}
