package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Notification {
    public int status;
    public int notification_id;

    public String username;
    public String notification_title;
    public String notification_content;
    public String device_signature;

    public abstract int sendNotification (String username, String device_signature, String notification_title, String notification_content);
    public abstract int updateNotification (String update_type, int value, int notification_id, String device_signature);
    public abstract int deleteNotification (String username, int notification_id, String device_signature);
    public abstract ResultSet readNotification (String username, String device_signature);
    public abstract ResultSet readNotification (String username, int notification_id, String device_signature);
}
