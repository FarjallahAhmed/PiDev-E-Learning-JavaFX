
package academiccalendar.ui.main;

import javafx.beans.property.SimpleStringProperty;

public class Term {
    private final SimpleStringProperty termName;
    private final SimpleStringProperty termDate;

    public Term(String termName, String termDate) {
        this.termName = new SimpleStringProperty(termName);
        this.termDate = new SimpleStringProperty(termDate);
    }

    public String getTermName() {
        return termName.get();
    }

    public String getTermDate() {
        return termDate.get();
    }
}
