package org.robert.tips.stryktipsfinder.domain.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Unit of work for client to process/complete.
 */
@Entity
public class StepThroughUnitOfWork {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public long id;

	public long startCombination;

	/**
	 * When the unit of work is completed this is the number of the combinations
	 * that have been tested.
	 */
	public long testedUpToCombination;

	@Enumerated(EnumType.STRING)
	public StepThroughUnitOfWorkStatus status;

	public StepThroughUnitOfWork() {
		status = StepThroughUnitOfWorkStatus.UNCOMPLETED;
	}

	/**
	 * Compare this object to the given object.
	 * 
	 * @param o
	 *            The object to compare with.
	 * @return boolean true = object is equal to the given one.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof StepThroughUnitOfWork) {
			StepThroughUnitOfWork position = (StepThroughUnitOfWork) o;

			if (position.startCombination == startCombination
					&& position.testedUpToCombination == testedUpToCombination) {
				return true; // object is equal.
			} else {
				return false;
			}
		} else {
			return false; // object is not equal.
		}
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		String value = "Id:" + Long.toString(id) + ", startCombination:"
				+ Long.toString(startCombination) + ", status:" + status
				+ ", testedUpToCombination:"
				+ Long.toString(testedUpToCombination);

		return value;
	}

	/**
	 * Check if the given unit of work is the one that should be after this unit
	 * of work.
	 * 
	 * @return true = the given unit of work is the next in sequence.
	 */
	public boolean isEqualToNextInSequence(StepThroughUnitOfWork nextUnitOfWork) {
		if (isNull()) {
			// If this unit of work is considered as null this implicit
			// defines that this is the first unit of work. Always return
			// true.
			return true;
		} else {
			if (nextUnitOfWork.startCombination == testedUpToCombination) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Check if this object is considered null/empty.
	 * 
	 * @return true=null.
	 */
	private boolean isNull() {
		if (status == null && testedUpToCombination == 0) {
			return true;
		} else {
			return false;
		}
	}

}
