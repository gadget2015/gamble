package org.robert.tipsbolag.webgui.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.robert.tipsbolag.webgui.domain.infrastructure.InfrastructureNamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = InfrastructureNamedQuery.HAMTA_EN_SPELARE_MED_ANVANDARID, query = "SELECT spelare FROM Spelare spelare WHERE spelare.userId.userId = :anvandarId") })
public class Spelare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	private UserId userId;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Konto konto;

	@OneToOne
	private Spelbolag administratorForSpelbolag;

	public Spelare() {
		this.konto = new Konto();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public UserId getUserId() {
		return userId;
	}

	public void setKonto(Konto konto) {
		this.konto = konto;
	}

	public Konto getKonto() {
		return konto;
	}

	public void kreditera(int insatsPerOmgang, String spelbolagsNamn,
			Date spelDatum) {
		Transaktion trans = new Transaktion(0, insatsPerOmgang,
				"Betala till tipsbolag '" + spelbolagsNamn + "'", spelDatum);
		konto.laggTillTransaktion(trans);
	}

	public void setAdministratorForSpelbolag(Spelbolag administratorForSpelbolag) {
		this.administratorForSpelbolag = administratorForSpelbolag;
	}

	public Spelbolag getAdministratorForSpelbolag() {
		return administratorForSpelbolag;
	}

	public boolean isAdminUser() {
		if (administratorForSpelbolag != null) {
			return true;
		} else {
			return false;
		}
	}

}
