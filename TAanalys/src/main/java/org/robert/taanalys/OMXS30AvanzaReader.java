package org.robert.taanalys;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.robert.taanalys.domain.model.InstrumentEvent;

public class OMXS30AvanzaReader extends InstrumentReader {
	public OMXS30AvanzaReader() {
		super.instrumentURI = "https://www.avanza.se/index/om-indexet.html/19002/omx-stockholm-30";
		
	}

	public InstrumentEvent readInstrumentInfo() throws ConnectionProblem,
			ConversionException {
		try {
			URL instrumentURL = new URL(instrumentURI);
			String content = IOUtils.toString(instrumentURL.openStream());

			// html-sidan är i UTF-8 format så den måste hanteras som UTF-8
			// explicit annars
			// används encodingen som finns i JVM:en.
			byte[] b = content.getBytes();
			content = new String(b, "UTF-8");
			//content = content.substring(5000);
			//content = new String(content.getBytes("UTF-8"), "UTF-8");
			
			InstrumentEvent event = new InstrumentEvent();

			// Senast uppdaterad
			String time = extractValue(content, "Senast uppdaterad:",
					"\">.{8}</span></span>");
			event.time = convertTime(time, "HH:mm:ss");

			// kursen.
			String quote = extractValue(content, "Senast uppdaterad: .{8}\">",
					"</span></span>[\\s]{5,30}</li>[\\s]{1,9}<li");
			// remove Non-breaking space, char=160.
			quote = quote.replace((char) 160, (char) 32);
			event.quote = convertQuote(quote, "#,###,##0.00", ',', ' ');

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
