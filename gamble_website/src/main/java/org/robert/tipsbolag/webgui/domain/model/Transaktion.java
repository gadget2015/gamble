package org.robert.tipsbolag.webgui.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaktion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	private int debit;
	private int kredit;
	private String beskrivning;
	@Temporal(TemporalType.TIMESTAMP)
	private Date transaktionsTid;
	private transient int saldo;

	public Transaktion() {
	}

	public Transaktion(int debit, int kredit, String beskrivning, Date spelDatum) {
		this.debit = debit;
		this.kredit = kredit;
		this.transaktionsTid = spelDatum;
		this.beskrivning = beskrivning;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setDebit(int debit) {
		this.debit = debit;
	}

	public int getDebit() {
		return debit;
	}

	public void setKredit(int kredit) {
		this.kredit = kredit;
	}

	public int getKredit() {
		return kredit;
	}

	public void setBeskrivning(String beskrivning) {
		this.beskrivning = beskrivning;
	}

	public String getBeskrivning() {
		return beskrivning;
	}

	public void setTransaktionsTid(Date transaktionsTid) {
		this.transaktionsTid = transaktionsTid;
	}

	public Date getTransaktionsTid() {
		return transaktionsTid;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public int getSaldo() {
		return saldo;
	}

}
