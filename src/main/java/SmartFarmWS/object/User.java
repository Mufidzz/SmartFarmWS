package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class User {
    public int user_id;

    public String username;
    public String password;
    public String device_id;
    
    public abstract ResultSet readUser (int user_id);
    public abstract ResultSet readUser (String device_id);
    public abstract ResultSet updateUser (int user_id);
    public abstract ResultSet deleteUser (int user_id);
    public abstract int loginUser (String username, String password);
}
