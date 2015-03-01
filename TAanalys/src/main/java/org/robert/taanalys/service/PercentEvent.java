package org.robert.taanalys.service;

/**
 * Definierar en händelse på en dags med procentenhet som mätdata.
  */
public class PercentEvent {
	private int minuteOfIntraday;
	private double percent;

	public void setMinuteOfIntraday(int minuteOfIntraday) {
		this.minuteOfIntraday = minuteOfIntraday;
	}

	public int getMinuteOfIntraday() {
		return minuteOfIntraday;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getPercent() {
		return percent;
	}

	public String toString() {
		return "Minute of Intraday = " + minuteOfIntraday + ", percent = "
				+ percent;
	}
}
