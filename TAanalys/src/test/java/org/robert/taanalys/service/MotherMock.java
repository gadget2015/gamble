package org.robert.taanalys.service;

import java.util.Calendar;

public class MotherMock {
	public static ProcentIntradag skapaTestdata(float procent,
			int totalNumberOfMinutes) {
		ProcentIntradag dag = new ProcentIntradag();
		dag.startQuote = 1200;
		dag.endQuote = 1205;
		Calendar now = Calendar.getInstance();
		dag.time = now.getTime();

		// Konstant kurs för att göra det enklare att simulera testerna.
		int minuteOfIntraday = 0;

		for (int i = 0; i < totalNumberOfMinutes; i++) {
			PercentEvent aMinute = new PercentEvent();
			aMinute.setMinuteOfIntraday(minuteOfIntraday++);
			aMinute.setPercent(procent);
			dag.laggTillEvent(aMinute);
		}

		return dag;
	}
}
