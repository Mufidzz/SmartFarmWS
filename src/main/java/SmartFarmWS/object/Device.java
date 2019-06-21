package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Device {
    public int device_id;
    public int user_id;
    public int plant_id;
    public int device_temp;
    public int device_light;
    public int device_flow;

    public String device_type;
    public String device_signature;

    public abstract int addDevice(int user_id, int plant_id, String device_type, String device_signature);
    public abstract ResultSet readDevice(int user_id);
    public abstract ResultSet readDevice(String device_signature);
    public abstract ResultSet updateDevice(int device_id);
    public abstract ResultSet updateDevice(String device_signature);
    public abstract ResultSet deleteDevice(int device_id, String device_signature);
}
