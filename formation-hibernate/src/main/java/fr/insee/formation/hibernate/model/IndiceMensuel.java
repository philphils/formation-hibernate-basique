package fr.insee.formation.hibernate.model;

import java.time.YearMonth;

public class IndiceMensuel extends Indice {

    /**
     * On peut utiliser un Converter vers le format Date
     */
    private YearMonth month;

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

}
