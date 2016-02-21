package magnetickush.trackziboo;

import java.io.Serializable;

/**
 * Created by kuush on 2/21/2016.
 */
public class POJO_ZIBO_LOCATION implements Serializable {

    String Date ,Latitude , Longitude,Time;
    String FlagSync ;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getFlagSync() {
        return FlagSync;
    }

    public void setFlagSync(String flagSync) {
        FlagSync = flagSync;
    }


}
