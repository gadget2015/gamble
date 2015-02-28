package org.robert.tipsbolag.webgui.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.robert.tipsbolag.webgui.domain.infrastructure.InfrastructureNamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = InfrastructureNamedQuery.HAMTA_ALLA_SPELBOLAG, query = "SELECT bolag FROM Spelbolag bolag") })
public class Spelbolag implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	private int insatsPerOmgang;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Collection<Spelare> allaBolagetsSpelare;
	private String namn;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Konto konto;

	public Spelbolag() {
		this.allaBolagetsSpelare = new ArrayList<Spelare>();
		this.konto = new Konto();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setInsatsPerOmgang(int insatsPerOmgang) {
		this.insatsPerOmgang = insatsPerOmgang;
	}

	public int getInsatsPerOmgang() {
		return insatsPerOmgang;
	}

	public void setSpelare(Collection<Spelare> spelare) {
		this.allaBolagetsSpelare = spelare;
	}

	public Collection<Spelare> getSpelare() {
		return allaBolagetsSpelare;
	}

	public void laggTillSpelare(Spelare nySpelare) {
		if (getSpelare() == null) {
			Collection<Spelare> spelare = new ArrayList<Spelare>();
			setSpelare(spelare);
		}

		this.allaBolagetsSpelare.add(nySpelare);
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public String getNamn() {
		return namn;
	}

	public void setKonto(Konto konto) {
		this.konto = konto;
	}

	public Konto getKonto() {
		return konto;
	}

	public void taBetaltAvAllaSeplareForEnOmgang(Date spelDatum) {
		// Ta betalt av alla spelare.
		for (Spelare spelare : allaBolagetsSpelare) {
			spelare.kreditera(insatsPerOmgang, getNamn(), spelDatum);
			debitera(spelare.getUserId(), spelDatum);
		}
	}

	private void debitera(UserId userId, Date spelDatum) {
		Transaktion kredit = new Transaktion(insatsPerOmgang, 0,
				"Insättning av '" + userId.getUserId() + "'.", spelDatum);
		konto.laggTillTransaktion(kredit);
	}

	public int getAntalSpelare() {
		return allaBolagetsSpelare.size();
	}

}