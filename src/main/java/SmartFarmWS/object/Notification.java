package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Notification {
    public int user_id;
    public int status;
    public int notification_id;

    public String notification_title;
    public String notification_content;
    public String device_signature;

    public abstract int sendNotification (int user_id, String device_signature, String notification_title, String notification_content);
    public abstract int updateNotification (String update_type, int value, int notification_id, String device_signature);
    public abstract int deleteNotification (int user_id, int notification_id, String device_signature);
    public abstract ResultSet readNotification (int user_id, String device_signature);
    public abstract ResultSet readNotification (int user_id, int notification_id, String device_signature);
}
