package SmartFarmWS.gateway;

import SmartFarmWS.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Device extends SmartFarmWS.object.Device {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private int affectedRows;

    Device(){
        conn = Database.dbConnect();
    }

    @Override
    public int addDevice(int user_id, int plant_id, String device_type, String device_signature) {
        try {
            this.user_id = user_id;
            this.plant_id = plant_id;
            this.device_type = device_type;
            this.device_signature = device_signature;

            String query = "INSERT INTO `device` " +
                    "(`device_id`, `user_id`, `device_type`, `device_signature`, `plant_id`, `device_temp`, `device_light`, `device_flow`) VALUES " +
                    "(NULL, '"+ this.user_id +"', '"+ this.device_type +"', '"+ this.device_signature +"', '"+ this.plant_id +"', '0', '0', '0')";

            this.stmt = conn.prepareStatement(query);
            this.affectedRows = stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return affectedRows;
    }

    @Override
    public ResultSet readDevice(int user_id) {
        return null;
    }

    @Override
    public ResultSet readDevice(String device_signature) {
        return null;
    }

    @Override
    public ResultSet updateDevice(int device_id) {
        return null;
    }

    @Override
    public ResultSet updateDevice(String device_signature) {
        return null;
    }

    @Override
    public ResultSet deleteDevice(int device_id, String device_signature) {
        return null;
    }
}
