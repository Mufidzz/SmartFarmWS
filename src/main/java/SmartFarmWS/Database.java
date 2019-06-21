package SmartFarmWS;

import java.sql.*;

public class Database {
    public static Connection conn;

    public static Connection dbConnect(){
      try {
          Class.forName(Driver.proops.getProperty("db.driver"));
          conn = DriverManager.getConnection(Driver.proops.getProperty("db.url"),Driver.proops.getProperty("db.user"),Driver.proops.getProperty("db.password"));
          System.out.println("Database Connection Success");
      } catch (Exception e){
          e.printStackTrace();
      }
      return conn;
    }

    public static void dbClose() throws SQLException {
        conn.close();
    }
}
