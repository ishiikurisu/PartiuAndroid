package rocks.crisjr.partiusketch.controller;

/**
 * Class for general help with time and dates.
 * @author Cris Joe Jr. (cristianoalvesjr@gmail.com)
 */
public class TimeController {
    public String getDate(int year, int month, int day) {
        return "" + year + "." + (month+1) + "." + day;
    }

    public String getTime(int hourOfDay, int minute) {
        return "" + hourOfDay + "h" + minute;
    }
}
