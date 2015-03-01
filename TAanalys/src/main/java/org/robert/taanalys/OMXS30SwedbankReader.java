package org.robert.taanalys;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.robert.taanalys.domain.model.InstrumentEvent;

public class OMXS30SwedbankReader extends InstrumentReader {
	public OMXS30SwedbankReader() {
		super.instrumentURI = "http://public.fsb.solutions.six.se/fsb.public/site/public/index/indexdetail.page?magic=(cc (detail (id OMXS30) (src OM)))";
	}

	public String instrumentURI;

	public InstrumentEvent readInstrumentInfo() throws ConnectionProblem,
			ConversionException {
		try {
			URL instrumentURL = new URL(instrumentURI);
			String content = IOUtils.toString(instrumentURL.openStream());
			InstrumentEvent event = new InstrumentEvent();

			// Senast uppdaterad
			String time = extractValue(content, "class=\"tbl-aktie-h\" headers=\"tm\">&nbsp;",
					"</td>[\\s]{1,9}</tr>[\\s]{1,9}</tbody>");
			event.time = convertTime(time, "HH:mm");

			// kursen.
			String quote = extractValue(content, "class=\"tbl-aktie-h\" headers=\"pd\">&nbsp;",
					"</td>[\\s]{1,9}<td class=\"tbl-aktie-h\" headers=\"hi\">");
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
