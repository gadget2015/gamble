package org.robert.taanalys;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.robert.taanalys.domain.model.InstrumentEvent;

public class OMXS30YahooReader extends InstrumentReader {
	public OMXS30YahooReader() {
		super.instrumentURI = "http://finance.yahoo.com/q?s=%5EOMX";
	}

	public InstrumentEvent readInstrumentInfo() throws ConnectionProblem,
			ConversionException {
		try {
			URL instrumentURL = new URL(instrumentURI);
			String content = IOUtils.toString(instrumentURL.openStream());
			InstrumentEvent event = new InstrumentEvent();

			// Senast uppdaterad 11:30AM EST
			String time = extractValue(content,
					"\"><span id=\"yfs_t10_\\^omx\">",
					"</span></span></span></p></div>");
			time="11:30AM EST";
			event.time = convertTime(time, "KK:mmaa zz");

			// kursen.1,132.84
			String quote = extractValue(content,
					"time_rtq_ticker\"><span id=\"yfs_l10_\\^omx\">", ""
							+ "</span></span> <span class=\".{3,8}time_rtq_");
			event.quote = convertQuote(quote, "#,###,##0.00", '.', ',');

			event.name = "OMXS30";

			return event;
		} catch (MalformedURLException e) {
			throw new ConnectionProblem("Malformed URL till instrumentet."
					+ instrumentURI);
		} catch (IOException e) {
			throw new ConnectionProblem(
					"Problem att hämta information om intrumentet "
							+ instrumentURI + ".");
		} 
	}
}
