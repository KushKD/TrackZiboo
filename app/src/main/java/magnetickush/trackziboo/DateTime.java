package magnetickush.trackziboo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kuush on 2/21/2016.
 */
public class DateTime {

    public String date_time_day () throws ParseException {

        String dateStr = "04/05/2010";

        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = curFormater.parse(dateStr);
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

        String DaywithDate = postFormater.format(dateObj);

        return DaywithDate;
    }
}
