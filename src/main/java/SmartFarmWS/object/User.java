package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class User {
    public int user_id;

    public String name;
    public String email;
    public String username;
    public String password;
    public abstract ResultSet readUser (int user_id);

    public abstract int addUser (String name, String email, String username, String password);
    public abstract int updateUser (String update_type, String value);
    public abstract int updatePassword (String old_password, String new_password);
    public abstract ResultSet readUser (String username);
    public abstract int deleteUser (String username);
    public abstract boolean login (String username, String password);
}
