package SmartFarmWS.gateway;

import SmartFarmWS.Database;

import java.lang.reflect.MalformedParametersException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Notification extends SmartFarmWS.object.Notification {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private int affectedRows;

    public static final String SET_READED = "NOTIFICATION.SET_READED";
    Notification(){
        this.conn = Database.dbConnect();
    }

    @Override
    public int sendNotification(String username, String device_signature, String notification_title, String notification_content) {
        this.username = username;
        this.device_signature = device_signature;
        this.notification_title = notification_title;
        this.notification_content = notification_content;

        try {
            String query = "INSERT INTO `notification` " +
                    "(`notification_id`, `username`, `device_signature`, `notification_title`, `notification_content`, `notification_status`) VALUES " +
                    "(NULL, ?, ?, ?, ?, '0')";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1,this.username);
            this.stmt.setString(2,this.device_signature);
            this.stmt.setString(3,this.notification_title);
            this.stmt.setString(4,this.notification_content);

            this.affectedRows = stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return affectedRows;
    }

    @Override
    public ResultSet readNotification(String username, String device_signature) {
        this.username = username;
        this.device_signature = device_signature;
        try {
            String query = "SELECT * FROM `notification` WHERE `username` = ? AND `device_signature` = ?";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, this.username);
            this.stmt.setString(2, this.device_signature);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

    @Override
    public ResultSet readNotification(String username, int notification_id, String device_signature) {
        this.username = username;
        this.device_signature = device_signature;
        try {
            String query = "SELECT * FROM `notification` WHERE `username` = ? AND `device_signature` = ? AND notification_id = ?";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, this.username);
            this.stmt.setString(2, this.device_signature);
            this.stmt.setInt(3, this.notification_id);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

    @Override
    public int updateNotification(String update_type, int value, int notification_id, String device_signature) {
        try {
            readNotification(this.username, notification_id, device_signature);
            while (this.rs.next()) {
                String ds = this.rs.getString("device_signature");
                String nid = this.rs.getString("notification_id");
                if (ds.equals(device_signature) && nid.equals(notification_id)){
                    this.notification_id = notification_id;
                    break;
                } else if (this.rs.isLast())
                    throw new MalformedParametersException("Device Signature not Found");
            }
            String query;

            switch (update_type){
                case "NOTIFICATION.SET_READED":
                    query = "UPDATE `notification` SET `notification_id` = ? WHERE `notification_status` = 1";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, notification_id);
                    break;
                default:
                    throw new IllegalArgumentException("Update Type Not Listed");
            }
            return stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteNotification(String username, int notification_id, String device_signature) {
        this.username = username;
        this.notification_id = notification_id;
        this.device_signature = device_signature;
        try {
            String query = "DELETE FROM `notification` WHERE `notification`.`device_signature` = ? AND `notification`.`notification_id` = ? AND `notification`.`username` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1,this.device_signature);
            this.stmt.setInt(2,this.notification_id);
            this.stmt.setString(3,this.username);
            return stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
