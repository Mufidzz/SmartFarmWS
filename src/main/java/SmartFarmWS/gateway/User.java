package SmartFarmWS.gateway;

import SmartFarmWS.Database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User extends SmartFarmWS.object.User {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private int affectedRows;

    User(){
        this.conn = Database.dbConnect();
    }

    @Override
    public ResultSet readUser(int user_id) {
        this.user_id = user_id;
        try {
            String query = "SELECT * FROM `user` WHERE `user_id` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setInt(1, this.user_id);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

    @Override
    public ResultSet readUser(String username) {
        this.username = username;
        try {
            String query = "SELECT * FROM `user` WHERE `username` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, this.username);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

    @Override
    public int addUser(String name, String email, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;

        try {
            String query = "INSERT INTO `user` (`user_id`, `email`, `name`, `username`, `password`) VALUES (NULL, ?, ?, ?, ?)";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, this.email);
            this.stmt.setString(2, this.name);
            this.stmt.setString(3, this.username);
            this.stmt.setString(4, this.password);

            this.affectedRows = stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return affectedRows;
    }

    @Override
    public int updateUser(String update_type, String value) {
        try {
            String query;
            switch (update_type){
                case "USER.EMAIL":
                    query = "UPDATE `user` SET `email` = ? WHERE `username` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setString(1, value);
                    break;
                case "USER.NAME":
                    query = "UPDATE `user` SET `name` = ? WHERE `username` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setString(1, value);
                    break;
                default:
                    throw new IllegalArgumentException("Update Type Not Listed");
            }
            this.stmt.setString(2,this.username);
            return stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updatePassword(String old_password, String new_password) {
        try {
            String query = "UPDATE `user` SET `password` = ? WHERE `username` = ? AND `password` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, new_password);
            this.stmt.setString(2, this.username);
            this.stmt.setString(2, this.password);
            return stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteUser(String username) {
        this.username = username;

        try {
            String query = "DELETE FROM `user` WHERE `user`.`username` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1,this.username);
            return stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean login(String username, String password) {
        this.username = username;
        this.password = password;
        try {
            String query = "SELECT * FROM `user` WHERE `username` = ? AND `password` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, this.username);
            this.stmt.setString(1, this.password);
            this.rs = stmt.executeQuery();
            if (this.rs.getFetchSize()>0) return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
