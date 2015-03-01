package org.robert.taanalys;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robert.taanalys.domain.model.InstrumentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InstrumentReader {
	private Logger logger = LoggerFactory.getLogger(InstrumentReader.class);

	public abstract InstrumentEvent readInstrumentInfo()
			throws ConnectionProblem, ConversionException;

	public String instrumentURI;

	/**
	 * Hämtar slutpositionen för första träffen av RegExp'et.
	 */
	private int getEndPosition(String content, String matchPattern)
			throws ConnectionProblem {
		// Start position
		Pattern pattern = Pattern.compile(matchPattern);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			printDebug(matcher);
			int endPostition = matcher.end();

			return endPostition;
		} else {
			throw new ConnectionProblem(
					"Kan inte hitta slut position för söksträngen. Söksträng = "
							+ matchPattern + ", content=" + content);
		}
	}

	/**
	 * Hämtar startpositionen för första träffen av RegExp'et.
	 * 
	 * @throws ConnectionProblem
	 */
	private int getStartPosition(String content, String matchPattern)
			throws ConnectionProblem {
		// Start position
		Pattern pattern = Pattern.compile(matchPattern);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			printDebug(matcher);
			int startPostition = matcher.start();

			return startPostition;
		} else {
			throw new ConnectionProblem(
					"Kan inte hitta start position för söksträngen. Söksträng = "
							+ matchPattern + ", content=" + content);
		}
	}

	protected String extractValue(String content, String startMatch,
			String endMatch) throws ConnectionProblem {
		int startPostition = getEndPosition(content, startMatch);
		int slutPos = getStartPosition(content, endMatch);

		String time = content.substring(startPostition, slutPos);

		return time;
	}

	private void printDebug(Matcher matcher) {
		logger.debug("Start index: " + matcher.start());
		logger.debug("End index: " + matcher.end() + " ");
		logger.debug(matcher.group());
	}

	protected float convertQuote(String quote, String pattern,
			char decimalSeparator, char groupingSeparator)
			throws ConversionException {
		try {
			DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
			unusualSymbols.setDecimalSeparator(decimalSeparator);
			unusualSymbols.setGroupingSeparator(groupingSeparator);
			DecimalFormat df = new DecimalFormat(pattern, unusualSymbols);
			float convertedQuote = df.parse(quote).floatValue();

			return convertedQuote;
		} catch (ParseException e) {
			throw new ConversionException("Problem att konvertera data."
					+ quote);
		}
	}

	protected Date convertTime(String time, String pattern)
			throws ConversionException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date timeData = sdf.parse(time);
			Calendar timeConvert = Calendar.getInstance();
			timeConvert.setTime(timeData);

			Calendar canonicalTime = Calendar.getInstance();
			canonicalTime.set(Calendar.HOUR_OF_DAY,
					timeConvert.get(Calendar.HOUR_OF_DAY));
			canonicalTime
					.set(Calendar.MINUTE, timeConvert.get(Calendar.MINUTE));
			Date convertedTime = canonicalTime.getTime();
			return convertedTime;
		} catch (ParseException e1) {
			throw new ConversionException(
					"Problem att konvertera datum, värde = " + time
							+ ", med pattern=" + pattern + ",Message ="
							+ e1.getMessage());
		}
	}
}
