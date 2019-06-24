package SmartFarmWS.object;

import java.sql.ResultSet;

public abstract class User {
    public int user_id;

    public static String name;
    public static String email;
    public static String username;
    public static String password;

    public abstract ResultSet readUser (int user_id);
    public abstract ResultSet readUser (String username);

    public abstract int addUser (String name, String email, String username, String password);
    public abstract int updateUser (String update_type, String value);
    public abstract int updatePassword (String old_password, String new_password);
    public abstract int deleteUser (String username);
    public abstract boolean login (String username, String password);
}
