package it.alessandra.community;

import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 11/12/2017.
 */

public class DataSet {

    public static Date formatToDate(String dateString){ // trasformo la data da stringa a Date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
        return format.parse(dateString,new ParsePosition(0));
    }

    public static String formatToString(Date date){
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
        return format.format(date);
    }
}
