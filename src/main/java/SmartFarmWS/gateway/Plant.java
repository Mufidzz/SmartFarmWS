package SmartFarmWS.gateway;

import SmartFarmWS.Database;

import java.lang.reflect.MalformedParametersException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Plant extends SmartFarmWS.object.Plant {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private int affectedRows;

    public static final String SET_TEMP_MIN = "PLANT.SET_TEMP_MIN";
    public static final String SET_TEMP_MAX = "PLANT.SET_TEMP_MAX";
    public static final String SET_LIGHT_RED = "PLANT.SET_LIGHT_RED";
    public static final String SET_LIGHT_GREEN = "PLANT.SET_LIGHT_GREEN";
    public static final String SET_LIGHT_BLUE = "PLANT.SET_LIGHT_BLUE";
    public static final String SET_FLOW_MIN = "PLANT.SET_FLOW_MIN";
    public static final String SET_FLOW_MAX = "PLANT.SET_FLOW_MAX";
    public static final String SET_NAME = "PLANT.SET_NAME";

    Plant(){
        this.conn = Database.dbConnect();
    }

    @Override
    public int addPlant(String user_uploader, String plant_name, String plant_signature, int plant_temp_min, int plant_temp_max, int plant_light_red, int plant_light_green, int plant_light_blue, int plant_flow_min, int plant_flow_max) {
        this.user_uploader = user_uploader;
        this.plant_name = plant_name;
        this.plant_signature = plant_signature;
        this.plant_temp_min = plant_temp_min;
        this.plant_temp_max = plant_temp_max;
        this.plant_light_red = plant_light_red;
        this.plant_light_blue = plant_light_blue;
        this.plant_light_green = plant_light_green;
        this.plant_flow_min = plant_flow_min;
        this.plant_flow_max = plant_flow_max;

        try {
            String query = "INSERT INTO `plant` " +
                    "(`plant_id`, `plant_name`, `plant_signature`, `plant_temp_min`, `plant_temp_max`, `plant_light_red`, `plant_light_green`, `plant_light_blue`, `plant_flow_min`, `plant_flow_max`, `user_uploader`) VALUES " +
                    "(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, plant_name);
            this.stmt.setString(2, plant_signature);
            this.stmt.setInt(3, plant_temp_min);
            this.stmt.setInt(4, plant_temp_max);
            this.stmt.setInt(5, plant_light_red);
            this.stmt.setInt(6, plant_light_green);
            this.stmt.setInt(7, plant_light_blue);
            this.stmt.setInt(8, plant_flow_min);
            this.stmt.setInt(9, plant_flow_max);

            this.affectedRows = stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return affectedRows;
    }

    @Override
    public int updatePlant(String update_type, String user_uploader, String plant_signature, int value) {
        try {
            String query;
            readPlant(plant_signature);

            switch (update_type){
                case "PLANT.SET_TEMP_MIN":
                    query = "UPDATE `plant` SET `plant_temp_min` = ? WHERE `plant_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, value);
                    break;
                case "PLANT.SET_TEMP_MAX":
                    query = "UPDATE `plant` SET `plant_temp_max` = ? WHERE `plant_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, value);
                    break;
                case "PLANT.SET_LIGHT_RED":
                    query = "UPDATE `plant` SET `plant_light_red` = ? WHERE `plant_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, value);
                    break;
                case "PLANT.SET_LIGHT_BLUE":
                    query = "UPDATE `plant` SET `plant_light_blue` = ? WHERE `plant_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, value);
                    break;
                case "PLANT.SET_LIGHT_GREEN":
                    query = "UPDATE `plant` SET `plant_light_green` = ? WHERE `plant_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, value);
                    break;
                case "PLANT.SET_FLOW_MIN":
                    query = "UPDATE `plant` SET `plant_flow_min` = ? WHERE `plant_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, value);
                    break;
                case "PLANT.SET_FLOW_MAX":
                    query = "UPDATE `plant` SET `plant_flow_max` = ? WHERE `plant_signature` = ?";
                    this.stmt = conn.prepareStatement(query);
                    this.stmt.setInt(1, value);
                    break;
                default:
                    throw new IllegalArgumentException("Update Type Not Listed");
            }
            this.stmt.setString(2,this.plant_signature);
            return stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ResultSet readPlant(){
        try {
            String query = "SELECT * FROM `plant`";
            this.stmt = conn.prepareStatement(query);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }
    @Override
    public ResultSet readPlant(String plant_signature) {
        this.plant_signature = plant_signature;
        try {
            String query = "SELECT * FROM `plant` WHERE `plant_signature` = ?";
            this.stmt = conn.prepareStatement(query);
            this.stmt.setString(1, this.plant_signature);
            this.rs = stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }


    @Override
    public ResultSet deletePlant(String user_uploader, String plant_signature) {
        return null;
    }
}
