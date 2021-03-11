
package Entities;

import javafx.beans.property.SimpleStringProperty;

public class Calendar {
    
        private final int id;
        private final String name;
        private final String startYear;
        private final String endYear;

        private final String startDate;
         
        public Calendar(int id,String name, String startYear, String endYear, String startingDate) {
            this.id = id;
            this.name = name;
            this.startYear = startYear;
            this.endYear = endYear;
            this.startDate = startingDate;

        }
         
        public String getName() {
            return name;
        }

        public String getStartYear() {
            return startYear;
        }

        public String getEndYear() {
            return endYear;
        }
        
        public String getStartDate() {
            return startDate;
        }

        public int getId() {
            return id;
        }

    @Override
    public String toString() {
        return "Calendar{" + "id=" + id + ", name=" + name + ", startYear=" + startYear + ", endYear=" + endYear + ", startDate=" + startDate + '}';
    }
        
        

    }
