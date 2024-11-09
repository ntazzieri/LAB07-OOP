package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                return Integer.compare(
                    Month.monthFromString(o1).getNumberOfDays(), 
                    Month.monthFromString(o2).getNumberOfDays());
            }
        };
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                return Month.monthFromString(o1).compareTo(Month.monthFromString(o2));
            }
            
        };
    }

    /**
     * A representation of Months
     */
    public enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31), 
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        final private int numberOfDays;

        private Month(final int numberOfDays) {
            this.numberOfDays = numberOfDays;
        }
        
        public int getNumberOfDays() {
            return this.numberOfDays;
        }

        /**
         * Given a string, the function returns the {@link Month} associated with it. 
         * It is not case sensitive and it also recognizes partial names 
         * 
         * @param monthName the string representing the month
         * @return the month represented by the string
         * @throws IllegalArgumentException the string matches multiple months or none of them
         */
        public static Month monthFromString(final String monthName) throws IllegalArgumentException {
            Month selectedMonth = null;
            for (final Month month : Month.values()) {
                if(month.toString().toLowerCase().contains(monthName.toLowerCase())) {
                    if(Objects.nonNull(selectedMonth)) {
                        throw new IllegalArgumentException("monthName " + monthName + " is ambiguous: it matches multiple months");
                    }
                    selectedMonth = month;
                }
            }
            if(Objects.isNull(selectedMonth)) {
                throw new IllegalArgumentException("monthName " + monthName + " does not match any month");
            } else {
                return selectedMonth;
            }
        }
    }
}
