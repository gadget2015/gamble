package org.robert.tips.stryktipsfinder.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Defines a stryktips system.
 * 
 * @author Robert
 * 
 */
@Entity
public class StryktipsSystem {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public long Id;
	
	private int numberOfRows;
	
	@Lob
	private String systemRows;	
	private long numberOfTestRuns;

	/**
	 * Link to previous stryktips system.
	 */
	private long previousStryktipsSystem;

	public StryktipsSystem() {
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Id:");
		sb.append(Id);
		sb.append("NumberOfRows:");
		sb.append(numberOfRows);
		sb.append("previousStryktipsSystemId:");
		sb.append(previousStryktipsSystem);
		
		return sb.toString();
	}
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setSystemRows(String systemRows) {
		this.systemRows = systemRows;
	}

	public String getSystemRows() {
		return systemRows;
	}

	public void setPreviousStryktipsSystem(long previousStryktipsSystem) {
		this.previousStryktipsSystem = previousStryktipsSystem;
	}

	public long getPreviousStryktipsSystem() {
		return previousStryktipsSystem;
	}

	public void setNumberOfTestRuns(long numberOfTestRuns) {
		this.numberOfTestRuns = numberOfTestRuns;
	}

	public long getNumberOfTestRuns() {
		return numberOfTestRuns;
	}

	public void addNumberOfTestRuns(int numberOfTests) {
		this.numberOfTestRuns += numberOfTests;
	}
}
