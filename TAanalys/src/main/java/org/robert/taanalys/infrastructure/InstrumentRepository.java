package org.robert.taanalys.infrastructure;

import java.util.Collection;
import java.util.Date;

import org.robert.taanalys.domain.model.InstrumentEvent;

public interface InstrumentRepository {

	InstrumentEvent sparaInstrument(InstrumentEvent event);

	Collection<InstrumentEvent> hamtaInstrumentEventsFran(
			Date datumAttHamtaIfran);

	Collection<InstrumentEvent> hamtaInstrumentEvents(Date startDatum,
			Date slutDatum);

}
