package fr.insee.formation.hibernate.model;

import java.time.YearMonth;

import jakarta.persistence.Entity;

@Entity
public class IndiceMensuel extends Indice {

	private YearMonth mois;

	public YearMonth getMois() {
		return mois;
	}

	public void setMois(YearMonth month) {
		this.mois = month;
	}

}
