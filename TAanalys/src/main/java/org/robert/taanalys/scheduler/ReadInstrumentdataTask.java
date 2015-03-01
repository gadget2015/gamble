package org.robert.taanalys.scheduler;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.robert.taanalys.ConnectionProblem;
import org.robert.taanalys.ConversionException;
import org.robert.taanalys.InstrumentReader;
import org.robert.taanalys.domain.model.InstrumentEvent;
import org.robert.taanalys.infrastructure.InstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read instrument data one time.
 * 
 * @author Robert
 */
@Stateless
public class ReadInstrumentdataTask {
	@EJB
	InstrumentRepository repository;
	InstrumentReader[] readers;
	public TaskStatus status;
	private Logger logger = LoggerFactory
			.getLogger(ReadInstrumentdataTask.class);
	public static String newline = System.getProperty("line.separator");

	public ReadInstrumentdataTask() {

	}

	public void setReaders(InstrumentReader... readers) {
		this.readers = readers;
	}

	public String execute() {
		int numberOfReaders = readers.length;
		String executionMessage = "";
		this.status = TaskStatus.FEL;

		for (int i = 0; i < numberOfReaders; i++) {
			try {
				InstrumentEvent event = readers[i].readInstrumentInfo();
				repository.sparaInstrument(event);
				executionMessage = "Det gick bra att hämta instrumentdata för '"
						+ event.name + "'.";
				this.status = TaskStatus.OK;

				break;
			} catch (ConnectionProblem e) {
				executionMessage += e.getMessage() + newline;
				logger.error(executionMessage);
			} catch (ConversionException e) {
				executionMessage += e.getMessage() + newline;
				logger.error(executionMessage);
			} catch(Exception e) {
				executionMessage += e.getMessage() + newline;
				logger.error("Problem att hämta information, provar nästa reader. Felmeddelande" + executionMessage);
			}
		}

		return executionMessage;
	}
}
