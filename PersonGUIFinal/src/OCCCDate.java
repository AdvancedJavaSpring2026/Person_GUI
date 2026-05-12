// Makayla Wood
// Advanced Java
// Spring 2026
// Class that acts as a wrapper to the GregorianCalendar class

import java.io.Serializable;
import java.util.GregorianCalendar;

public class OCCCDate implements Serializable, Comparable<OCCCDate>
{
    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private GregorianCalendar gc;
    private boolean dateFormat = FORMAT_US;
    private boolean dateStyle = STYLE_NUMBERS;
    private boolean dateDayName = SHOW_DAY_NAME;
    
    public static final boolean FORMAT_US = true;
    public static final boolean FORMAT_EURO = false;
    public static final boolean STYLE_NUMBERS = true;
    public static final boolean STYLE_NAMES = false;
    public static final boolean SHOW_DAY_NAME = true;
    public static final boolean HIDE_DAY_NAME = false;
    
    // This default constructor instantiates the date using the current date
    public OCCCDate()
    {
        this(new GregorianCalendar());
    }
    
    // This constructor instantiates the date using the provided int values for
    // day, month and year
    // Throws an InvalidOCCCDateException if the provided values do not produce
    // a valid date
    public OCCCDate(int day, int month, int year)
    {
        this(createValidCalendar(day, month, year));
    }
    
    // This constructor creates a copy from another OCCCDate
    public OCCCDate(OCCCDate od)
    {
        this(od.getDayofMonth(), od.getMonthNumber(), od.getYear());
    }
    
    // This constructor instantiates the date using a GregorianCalendar object
    // from which the day, month and year are taken
    public OCCCDate(GregorianCalendar gc)
    {
        this.gc = (GregorianCalendar)gc.clone();
        dayOfMonth = gc.get(GregorianCalendar.DAY_OF_MONTH);
        monthOfYear = gc.get(GregorianCalendar.MONTH) + 1;
        year = gc.get(GregorianCalendar.YEAR);
    }
    
    private static GregorianCalendar createValidCalendar(int day, int month, int year) throws InvalidOCCCDateException
    {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        if (calendar.get(GregorianCalendar.YEAR) != year ||
                calendar.get(GregorianCalendar.MONTH) + 1 != month ||
                calendar.get(GregorianCalendar.DAY_OF_MONTH) != day)
            throw new InvalidOCCCDateException();
        else
        {
            return calendar;
        }
    }
    
    // Returns the integer value of the day of the month
    public int getDayofMonth()
    {
        return this.dayOfMonth;
    }
    
    // Returns the name of the current day of the week
    public String getDayName()
    {
        String dayName = "";
        switch (gc.get(GregorianCalendar.DAY_OF_WEEK))
        {
            case 1 -> dayName = "Sunday";
            case 2 -> dayName = "Monday";
            case 3 -> dayName = "Tuesday";
            case 4 -> dayName = "Wednesday";
            case 5 -> dayName = "Thursday";
            case 6 -> dayName = "Friday";
            case 7 -> dayName = "Saturday";
        }
        return dayName;
    }
    
    // Returns the number of the current month
    public int getMonthNumber()
    {
        return this.monthOfYear;
    }
    
    // Returns the name of the current month
    public String getMonthName()
    {
        String monthName = "";
        switch (gc.get(GregorianCalendar.MONTH))
        {
            case 0 -> monthName = "January";
            case 1 -> monthName = "February";
            case 2 -> monthName = "March";
            case 3 -> monthName = "April";
            case 4 -> monthName = "May";
            case 5 -> monthName = "June";
            case 6 -> monthName = "July";
            case 7 -> monthName = "August";
            case 8 -> monthName = "September";
            case 9 -> monthName = "October";
            case 10 -> monthName = "November";
            case 11 -> monthName = "December";
        }
        return monthName;
    }
    
    // Returns the current year
    public int getYear()
    {
        return this.year;
    }
    
    // Sets the current date format
    // true = US, false = EURO
    public void setDateFormat(boolean df)
    {
        this.dateFormat = df;
    }
    
    // Sets the current date style format
    // true = numbers, false = names
    public void setStyleFormat(boolean sf)
    {
        this.dateStyle = sf;
    }
    
    // Sets whether to show day names
    // true = show, false = hide
    public void setDayName(boolean nf)
    {
        this.dateDayName = nf;
    }
    
    // Returns the number of years between this instance of OCCCDate and the
    // time of the method call
    public int getDifferenceInYears()
    {
        return getDifferenceInYears(new OCCCDate());
    }
    
    // Returns the number of years between this instance of OCCCDate and the
    // provided instance of OCCCDate
    public int getDifferenceInYears(OCCCDate d)
    {
        int diff = d.getYear() - year;
        return Math.abs(diff);
    }
    
    // Compares day, month, and year of the current and provided instances of
    // OCCCDate. Returns true if all match and false if not
    public boolean equals(OCCCDate dob)
    {
        return (dob.getDayofMonth() == this.dayOfMonth) &&
                (dob.getMonthNumber() == this.monthOfYear) &&
                (dob.getYear() == this.year);
    }
    
    // Returns a string value with the OCCCDate in the currently assigned format
    @Override
    public String toString()
    {
        String date;
        
        if (dateFormat == FORMAT_US)
        {
            if (dateStyle == STYLE_NUMBERS)
            {
                date = this.monthOfYear + "/" + this.dayOfMonth + "/" + this.year;
            }
            else // STYLE_NAMES
            {
                date = this.getMonthName() + " " + this.dayOfMonth + ", " + this.year;
            }
        }
        else // FORMAT_EURO
        {
            if (dateStyle == STYLE_NUMBERS)
            {
                date = this.dayOfMonth + "/" + this.monthOfYear + "/" + this.year;
            }
            else // STYLE_NAMES
            {
                date = this.dayOfMonth + " " + this.getMonthName() + " " + this.year;
            }
        }
        
        if (dateDayName == SHOW_DAY_NAME)
            date = this.getDayName() + ", " + date;
        
        return date;
    }
    
    // Compares OCCCDates by year, month, and then day
    @Override
    public int compareTo(OCCCDate d)
    {
        if (this.year == d.getYear())
        {
            if (this.monthOfYear == d.getMonthNumber())
            {
                if (this.dayOfMonth == d.getDayofMonth())
                    return 0;
                else if (this.dayOfMonth > d.getDayofMonth())
                    return 1;
                else
                    return -1;
            }
            else if (this.monthOfYear > d.getMonthNumber())
                return 1;
            else
                return -1;
        }
        else if (this.year > d.getYear())
            return 1;
        else
            return -1;
    }
}
