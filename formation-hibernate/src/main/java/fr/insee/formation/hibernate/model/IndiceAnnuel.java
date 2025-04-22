package fr.insee.formation.hibernate.model;

import java.time.Year;

import jakarta.persistence.Entity;

@Entity
public class IndiceAnnuel extends Indice {

	private Year annee;

	public Year getAnnee() {
		return annee;
	}

	public void setAnnee(Year year) {
		this.annee = year;
	}

}
