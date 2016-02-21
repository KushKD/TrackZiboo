package magnetickush.trackziboo;

import java.io.Serializable;

/**
 * Created by kuush on 2/21/2016.
 */
public class POJO_ZIBOO_IS_ZIBO implements Serializable {

    String Username;
    String IMEI;
    String Start;

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }



}
