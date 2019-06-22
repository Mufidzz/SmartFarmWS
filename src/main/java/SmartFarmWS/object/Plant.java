package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class Plant {
    public int user_id;

    public String type;
    public String name;

    public double levelPhMin;
    public double levelPhMax;
    public double levelCfMin;
    public double levelCFMax;
    public double levelPpmMin;
    public double levelPpmMax;

    public abstract ResultSet readPlant (int user_id);
    public abstract ResultSet readPlant (String type, String name);
    public abstract ResultSet updatePlant (int user_id);
    public abstract ResultSet deletePlant (int user_id);



}
