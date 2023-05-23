package fr.insee.formation.hibernate.model;

import java.time.YearMonth;

public class IndiceMensuel extends Indice {

	/**
	 * On peut utiliser un Converter vers le format Date Petite aide, code pour
	 * obtenir le YearMonth à partir d'une Date :
	 * YearMonth.from(declaration.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
	 */
	private YearMonth month;

	public YearMonth getMonth() {
		return month;
	}

	public void setMonth(YearMonth month) {
		this.month = month;
	}

}
