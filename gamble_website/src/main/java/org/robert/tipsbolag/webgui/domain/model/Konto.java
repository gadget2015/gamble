package org.robert.tipsbolag.webgui.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Kan vara t.ex. Bank, Kassa, Kontorsmaterial. Alla spelkonton är
 * Tillgångskonton av typen 1910 - Kassa.
 * 
 * Typer av konton:
 * 
 * <pre>
 * -Tillgångar - Skulder - Inkomster - Utgifter
 * </pre>
 * 
 * @see http://www.expowera.se/mentor/ekonomi/bokforing_grunder.htm
 */
@Entity
public class Konto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	private int kontonr;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Transaktion> transaktioner;

	public Konto() {
		this.transaktioner = new ArrayList<Transaktion>();
	}

	public Konto(int kontonr) {
		this.kontonr = kontonr;
	}
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setKontonr(int kontonr) {
		this.kontonr = kontonr;
	}

	public int getKontonr() {
		return kontonr;
	}

	public void setTransaktioner(Collection<Transaktion> transaktioner) {
		this.transaktioner = transaktioner;
	}

	public Collection<Transaktion> getTransaktioner() {
		return transaktioner;
	}

	public int getSaldo() {
		if (transaktioner != null) {
			int saldo = 0;

			for (Transaktion trans : transaktioner) {
				saldo += trans.getDebit() - trans.getKredit();
			}

			return saldo;
		} else {
			return 0;
		}
	}

	public void laggTillTransaktion(Transaktion trans) {
		transaktioner.add(trans);
	}

	/**
	 * Hämtar alla transaktioner för kontot. De sorteteras och saldo beräknas
	 * för varje transaktion.
	 */
	public List<Transaktion> getSummeratransaktion() {
		// 1. Sortera transaktioner.
		transaktioner.toArray();
		ArrayList<Transaktion> sorted = new ArrayList<Transaktion>();
		sorted.addAll(transaktioner);
		Collections.sort(sorted, new TransaktionComparator());

		// 2. Beräkna saldo för varje transaktion, börjar bakifrån.
		int antalTransaktioner = sorted.size();
		int saldo = 0;
		
		for (int i = antalTransaktioner - 1; i >= 0; i--) {
			Transaktion trans = sorted.get(i);
			saldo += trans.getDebit() - trans.getKredit();
			trans.setSaldo(saldo);
		}
		
		return sorted;
	}
}
