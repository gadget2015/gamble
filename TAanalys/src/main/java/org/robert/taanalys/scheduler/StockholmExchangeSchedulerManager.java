package org.robert.taanalys.scheduler;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.robert.taanalys.OMXS30AvanzaReader;
import org.robert.taanalys.OMXS30SwedbankReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class StockholmExchangeSchedulerManager {

	@EJB
	ReadInstrumentdataTask omxTask = new ReadInstrumentdataTask();
	public LinkedList<TaskExecutionInfo> result = new LinkedList<TaskExecutionInfo>();
	private Logger logger = LoggerFactory
			.getLogger(StockholmExchangeSchedulerManager.class);

	public StockholmExchangeSchedulerManager() {
		init();
	}

	public void init() {
		this.omxTask.setReaders(new OMXS30AvanzaReader(),
				new OMXS30SwedbankReader());
	}

	/**
	 * Används för att Mocka.
	 */
	public StockholmExchangeSchedulerManager(String string) {
		result.add(new TaskExecutionInfo("Det gick bra för OMXS30.", new Date()));
		result.add(new TaskExecutionInfo("FEL för OMXS30.", new Date()));
		result.add(new TaskExecutionInfo("Det gick bra för OMXS30.", new Date()));
	}

	@Schedule(minute = "*/1", hour = "7-16", dayOfWeek="Mon, Tue, Wed, Thu, Fri")
	public void executeTasks() {
		init();
		String executionMessage = omxTask.execute();
		TaskExecutionInfo info = new TaskExecutionInfo();
		info.setMessage(executionMessage);
		info.setExecutionTime(Calendar.getInstance().getTime());

		if (result.size() >= 100) {
			result.removeLast();
		} else {
			result.addFirst(info);
		}

		logger.debug("Message=" + info.getMessage());
	}

	public Collection<TaskExecutionInfo> result() {
		return result;
	}

}
