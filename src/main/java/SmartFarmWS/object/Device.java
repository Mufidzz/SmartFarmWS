package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Device {
    public int device_id;
    public int user_id = 1;
    public int plant_id;
    public int device_temp;
    public int device_light;
    public int device_flow;

    public String device_type;
    public String device_signature;

    public abstract int addDevice(int user_id, int plant_id, String device_type, String device_signature);
    public abstract ResultSet readDevice();
    public abstract ResultSet readDevice(int user_id);
    public abstract ResultSet readDevice(String device_signature);
    public abstract int updateDevice(String update_type, int value, String device_signature);
    public abstract int deleteDevice(int user_id, String device_signature);
}
