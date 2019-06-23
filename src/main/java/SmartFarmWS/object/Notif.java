package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Notif {
    public int user_id;
    public int notification_type;
    public int status;

    public String notificationID;
    public String notificationTitle;
    public String notificationContent;

    public abstract ResultSet readNotif (int user_id);
    public abstract ResultSet updateNotif (int user_id);
    public abstract ResultSet deleteNotif (int user_id);
    public abstract ResultSet setReaded (int user_id);
    public abstract ResultSet setToDo (int user_id);
    public abstract ResultSet setNormal (int user_id);
}
