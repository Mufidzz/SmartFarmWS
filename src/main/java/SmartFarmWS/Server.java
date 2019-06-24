package SmartFarmWS;

import SmartFarmWS.gateway.Router;
import spark.Filter;
import spark.Spark;
import java.util.ArrayList;
import java.util.Map;

public class Server {
    public static Map<String ,String> session;

    public Server(int port, int minThreads, int maxThreads, int timeOut){
        Spark.port(port);
        Spark.threadPool(maxThreads, minThreads, timeOut);

        new Router();
        apply();
    }

    public final static void apply(){
        Filter f = (request, response) -> response.type("application/json");
    }
}
