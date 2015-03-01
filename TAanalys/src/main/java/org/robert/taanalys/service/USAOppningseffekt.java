package org.robert.taanalys.service;

public class USAOppningseffekt {

	public int uppgang;
	public int nedgang;
	public int ingenRorlese;
	public int antalDagar;

	public USAOppningseffekt clone() {
		USAOppningseffekt clone = new USAOppningseffekt();
		clone.uppgang = uppgang;
		clone.nedgang = nedgang;
		clone.ingenRorlese = ingenRorlese;

		return clone;
	}

	public boolean isNotChanged(USAOppningseffekt statistik) {
		if (uppgang == statistik.uppgang && nedgang == statistik.nedgang) {
			return true;
		} else {

			return false;
		}
	}

	public boolean arEnUppgang(USAOppningseffekt before) {
		if (uppgang > before.uppgang) {
			return true;
		} else {
			return false;
		}
	}

	public boolean arEnNedgang(USAOppningseffekt before) {
		if (nedgang > before.nedgang) {
			return true;
		} else {
			return false;
		}
	}
}
