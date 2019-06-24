package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Plant {
    public int plant_temp_min;
    public int plant_temp_max;
    public int plant_light_red;
    public int plant_light_green;
    public int plant_light_blue;
    public int plant_flow_min;
    public int plant_flow_max;

    public String plant_name;
    public String plant_signature;
    public String user_uploader;

    public abstract int addPlant (String user_uploader, String plant_name, String plant_signature, int plant_temp_min, int plant_temp_max, int plant_light_red, int plant_light_green, int plant_light_blue, int plant_flow_min, int plant_flow_max);
    public abstract int updatePlant (String update_type, String user_uploader, String plant_signature, int value);

    public abstract ResultSet readPlant ();
    public abstract ResultSet readPlant (String plant_signature);
    public abstract ResultSet deletePlant (String user_uploader, String plant_signature);
}
