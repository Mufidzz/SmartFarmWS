package SmartFarmWS;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Driver {
    public static Properties proops;

    public static void main(String[] args) {
        proops = new Properties();

        if (args.length > 0) {
            proopsLoad(proops,args[0]);
        } else {
            System.out.println("Main Param is Empty");
            System.exit(0);
        }

        int port = Integer.parseInt(proops.getProperty("server.port","1234"));
        int maxThreads = Integer.parseInt(proops.getProperty("server.maxthreads","200"));
        int minThreads = Integer.parseInt(proops.getProperty("server.minthreads","30"));
        int timeOut = Integer.parseInt(proops.getProperty("server.timeout","20000"));

        new Server(port,minThreads,maxThreads,timeOut);

    }
    
    public static void proopsLoad(Properties proop, String filename){
        InputStream input = null;

        try {
            input = new FileInputStream(filename);
            proop.load(input);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (input != null){
                try {
                    input.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
