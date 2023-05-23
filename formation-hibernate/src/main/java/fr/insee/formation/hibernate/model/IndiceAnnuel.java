package fr.insee.formation.hibernate.model;

import java.time.Year;

public class IndiceAnnuel extends Indice {

	/**
	 * On peut utiliser un Converter vers le format Date Petite aide, code pour
	 * obtenir le Year à partir d'une Date :
	 * Year.from(declaration.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
	 */
	private Year year;

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

}
