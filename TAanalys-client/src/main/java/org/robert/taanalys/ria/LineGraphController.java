package org.robert.taanalys.ria;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.chart.LineChart;

public class LineGraphController {
	public int NUMBER_OF_COLORS_POINTS_TO_INCREASE = 32;
	
	public void colorizeGraph(LineChart<Integer, Double> graph) {
		Color customColor = new Color(127, 0, 0);

		// loopar över alla dagar.
		for (LineChart.Series<Integer, Double> serie : graph.getData()) {
			// Sätter färg på serien.
			serie.getNode().setStyle(
					"-fx-stroke: " + getHex(customColor)
							+ "; -fx-stroke-width: 1px; -fx-effect: null;");

			// Hämtar nästa färg.
			customColor = nastaFarg(customColor);
		}
	}

	public Color nastaFarg(Color rgb) {
		int red = rgb.getRed();
		int green = rgb.getGreen();
		int blue = rgb.getBlue();

		if (red <= 248 && green == 0 && blue == 0) {
			red += NUMBER_OF_COLORS_POINTS_TO_INCREASE;
		} else if (red == 0 && green <= 248 && blue == 0) {
			green += NUMBER_OF_COLORS_POINTS_TO_INCREASE;
		} else if (red == 0 && green == 255 && blue == 0) {
			green = 0;
			blue = 127;
		} else if (red == 0 && green == 0 && blue <= 248) {
			blue += NUMBER_OF_COLORS_POINTS_TO_INCREASE;
		} else if (red == 255 && green == 0 && blue == 0) {
			red = 0;
			green = 127;
		} else if (red == 0 && green == 0 && blue == 255) {
			red = 127;
			green = 0;
			blue = 0;
		}

		return new Color(red, green, blue);
	}

	/**
	 * Returns the HEX value representing the colour in the default sRGB
	 * ColorModel.
	 * 
	 * @return the HEX value of the colour in the default sRGB ColorModel
	 */
	public String getHex(Color rgb) {
		return toHex(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
	}

	/**
	 * Returns a web browser-friendly HEX value representing the colour in the
	 * default sRGB ColorModel.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 * @return a browser-friendly HEX value
	 */
	public static String toHex(int r, int g, int b) {
		return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
				+ toBrowserHexValue(b);
	}

	private static String toBrowserHexValue(int number) {
		StringBuilder builder = new StringBuilder(
				Integer.toHexString(number & 0xff));
		while (builder.length() < 2) {
			builder.append("0");
		}
		return builder.toString().toUpperCase();
	}

	protected Calendar konvertera(String time, String pattern)
			throws RuntimeException {
		if (time == null || time.length() == 0) {
			return null;
		}

		try {
			// Konverterar
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date timeData = sdf.parse(time);
			Calendar datum = Calendar.getInstance();
			datum.setTime(timeData);

			return datum;
		} catch (ParseException e1) {
			throw new RuntimeException("Problem att konvertera datum, värde = "
					+ time + ", med pattern=" + pattern + ",Message ="
					+ e1.getMessage());
		}
	}
}
