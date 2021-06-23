package fr.insee.formation.hibernate.model;

import java.time.Instant;

import javax.persistence.Transient;

public abstract class Indice {

	private int id;

	@Transient
	private Secteur secteur;

	private Double valeur;
	
	private Instant derniereMaj;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Secteur getSecteur() {
		return secteur;
	}

	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}

	public Double getValeur() {
		return valeur;
	}

	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}

	public Instant getDerniereMaj() {
		return derniereMaj;
	}

	public void setDerniereMaj(Instant derniereMaj) {
		this.derniereMaj = derniereMaj;
	}

}
