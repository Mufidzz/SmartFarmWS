package SmartFarmWS;

import spark.Filter;
import spark.Spark;

public class Server {
    public Server(int port, int minThreads, int maxThreads, int timeOut){
        Spark.port(port);
        Spark.threadPool(maxThreads, minThreads, timeOut);

        Spark.get("/", (req,res) -> "Coba");
        apply();

    }

    public final static void apply(){
        Filter f = (request, response) -> response.type("application/json");
    }
}
