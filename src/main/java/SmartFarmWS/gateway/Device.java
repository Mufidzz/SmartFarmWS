package SmartFarmWS.gateway;

import SmartFarmWS.Database;

import java.lang.reflect.MalformedParametersException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Device extends SmartFarmWS.object.Device {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private int affectedRows;

    public static String PLANT = "DEVICE.PLANT";
    public static String TEMPERATURE = "DEVICE.TEMPERATURE";
    public static String LIGHT = "DEVICE.LIGHT";
    public static String FLOW = "DEVICE.FLOW";

    public Device(){
        conn = Database.dbConnect();
    }

    @Override
    public int addDevice(int user_id, int plant_id, String device_type, String device_signature) {
        this.user_id = user_id;
        this.plant_id = plant_id;
        this.device_type = device_type;
        this.device_signature = device_signature;

        try {
            String query = "INSERT INTO `device` " +
                    "(`device_id`, `user_id`, `device_type`, `device_signature`, `plant_id`, `device_temp`, `device_light`, `device_flow`) VALUES " +
                    "(NULL, ?,?,?,?, '0', '0', '0')";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setInt(1,this.user_id);
            this.stmt.setString(2,this.device_type);
            this.stmt.setString(3,this.device_signature);
            this.stmt.setInt(4,this.plant_id);

            this.affectedRows = stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return affectedRows;
    }

    @Override
    public ResultSet readDevice() {
        try {
            String query = "SELECT * FROM `device`";

            this.stmt = conn.prepareStatement(query);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

    @Override
    public ResultSet readDevice(int user_id) {
        this.user_id = user_id;
        try {
            String query = "SELECT * FROM `device` WHERE `user_id` = ?";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, Integer.toString(this.user_id));
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

    @Override
    public ResultSet readDevice(String device_signature) {
        this.device_signature = device_signature;
        try {
            String query = "SELECT * FROM `device` WHERE `device_signature` = ?";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1,this.device_signature);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

    @Override
    public int updateDevice(String update_type, int value, String device_signature) {
        try {
            readDevice(this.user_id);
            while (this.rs.next()) {
                String ds = this.rs.getString("device_signature");
                if (ds.equals(device_signature)){
                    this.device_signature = device_signature;
                    break;
                } else if (this.rs.isLast())
                    throw new MalformedParametersException("Device Signature not Found");
            }
            String query;

            switch (update_type){
                case "DEVICE.PLANT":
                    query = "UPDATE `device` SET `plant_id` = ? WHERE `device_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setString(2, this.device_signature);
                    this.stmt.setInt(1, value);
                    break;
                case "DEVICE.TEMPERATURE":
                    query = "UPDATE `device` SET `device_temp` = ? WHERE `device_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setString(2, this.device_signature);
                    this.stmt.setInt(1, value);
                    break;
                case "DEVICE.LIGHT":
                    query = "UPDATE `device` SET `device_light` = ? WHERE `device_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setString(2, this.device_signature);
                    this.stmt.setInt(1, value);
                    break;
                case "DEVICE.FLOW":
                    query = "UPDATE `device` SET `device_flow` = ? WHERE `device_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setString(2, this.device_signature);
                    this.stmt.setInt(1, value);
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
    public int deleteDevice(int user_id, String device_signature) {
        this.user_id = user_id;
        this.device_signature = device_signature;
        try {
            String query = "DELETE FROM `device` WHERE `device`.`device_signature` = ? AND `device`.`user_id` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1,this.device_signature);
            this.stmt.setInt(2,this.user_id);
            return stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
