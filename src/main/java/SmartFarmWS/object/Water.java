package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Water {
    public int user_id;
    public int flow;

    public double F;
    public double pH;
    public double cF;
    public double PPM;

    public String device_id;

    public abstract ResultSet readWater (int user_id);
    public abstract ResultSet readWater (String device_id);
    public abstract ResultSet updateWater (String device_id);
    public abstract ResultSet deleteWater (String device_id);
}
