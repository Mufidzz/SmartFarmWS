package SmartFarmWS.object;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Device {
    public int device_id;
    public int plant_id;
    public int device_temp;
    public int device_flow;

    public String device_light;
    public String username;
    public String device_type;
    public String device_signature;

    public abstract int addDevice(String username, int plant_id, String device_type, String device_signature);
    public abstract int updateDeviceLight(String value, String device_signature, String username) throws SQLException;
    public abstract ResultSet readDevice();
    public abstract ResultSet readDevice(String username);
    public abstract ResultSet readDevice(String username, String device_signature);
    public abstract int updateDevice(String update_type, int value, String device_signature, String username);
    public abstract int updateDeviceLight(String value,String device_signature, String username) throws SQLException;
    public abstract int deleteDevice(String username, String device_signature);
}
