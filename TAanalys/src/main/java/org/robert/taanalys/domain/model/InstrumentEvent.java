package org.robert.taanalys.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.robert.taanalys.infrastructure.InfrastructureNamedQuery;

@Entity
@NamedQueries({ 
	@NamedQuery(name = InfrastructureNamedQuery.SEARCH_FOR_INSTRUMENT_EVENTS_FROM_DATE, query = "SELECT event FROM InstrumentEvent event WHERE event.time>=:franDatum"),
	@NamedQuery(name = InfrastructureNamedQuery.SEARCH_FOR_INSTRUMENT_EVENTS_BY_DATES, query = "SELECT event FROM InstrumentEvent event WHERE event.time>=:franDatum AND event.time<=:tillDatum ORDER BY event.time")
	}
)
public class InstrumentEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public long id;
	public String name;
	public float quote;

	@Temporal(TemporalType.TIMESTAMP)
	public Date time;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(quote);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstrumentEvent other = (InstrumentEvent) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(quote) != Float.floatToIntBits(other.quote))
			return false;
		return true;
	}

	public String toString() {
		return name + ":" + quote + "," + time;
	}
}
