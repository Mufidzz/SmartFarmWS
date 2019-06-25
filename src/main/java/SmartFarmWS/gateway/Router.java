package SmartFarmWS.gateway;

import SmartFarmWS.Server;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Spark;

import java.sql.ResultSet;

public class Router {
    private JSONObject reqJSON;

    public Router(){

        Spark.path("/user", () -> {
            Spark.post("/", (request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new User().addUser(reqJSON.getString("name"),reqJSON.getString("email"),reqJSON.getString("username"), reqJSON.getString("password"));
            });

            Spark.put("/email", (request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new User().updateUser(U,this.reqJSON.getString("email"));
            });

            Spark.put("/name", (request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new User().updateUser(User.NAME,this.reqJSON.getString("name"));
            });

            Spark.put("/password", (request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return "Not yet available";
            });

            Spark.get("/username/:username", (request, response) -> {
                ResultSet users = new User().readUser(request.params(":username"));
                JSONArray jsonArray = new JSONArray();

                while (users.next()){
                    JSONObject o = new JSONObject(request.body());
                    o.put("name",users.getString("name"));
                    o.put("email", users.getString("email"));
                    o.put("username", users.getString("username"));
                    jsonArray.put(o);
                }

                return jsonArray;
            });

            Spark.get("/userid/:userid", (request, response) -> {
                ResultSet users = new User().readUser(request.params(":userid"));
                JSONArray jsonArray = new JSONArray();

                while (users.next()){
                    JSONObject o = new JSONObject(request.body());
                    o.put("name",users.getString("name"));
                    o.put("email", users.getString("email"));
                    o.put("username", users.getString("username"));
                    jsonArray.put(o);
                }

                return jsonArray;
            });

            Spark.delete("/", (request, response) -> {
                this.reqJSON =new JSONObject(request.body());
                return new User().deleteUser(reqJSON.getString("username"));
            });

            Spark.post("/login", (request, response) -> {
                this.reqJSON =new JSONObject(request.body());
                if(new User().login(reqJSON.getString("username"), "password")){
                    Server.session.put("user", reqJSON.getString("user"));
                    return 1;
                }
                return 0;
            });
        });

        Spark.path("/plant",() ->{
            Spark.post("/", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Plant().addPlant(
                        Server.session.get("user"),
                        reqJSON.getString("plant_name"),
                        reqJSON.getString("plant_signature"),
                        Integer.parseInt(reqJSON.getString("temp_min")),
                        Integer.parseInt(reqJSON.getString("temp_max")),
                        Integer.parseInt(reqJSON.getString("light_red")),
                        Integer.parseInt(reqJSON.getString("light_green")),
                        Integer.parseInt(reqJSON.getString("light_blue")),
                        Integer.parseInt(reqJSON.getString("flow_min")),
                        Integer.parseInt(reqJSON.getString("flow_max"))
                );
            }));

            Spark.put("/temp", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                new Plant().updatePlant(Plant.SET_TEMP_MIN,Server.session.get("user"),reqJSON.getString("plant_signature"),Integer.parseInt(reqJSON.getString("min")));
                new Plant().updatePlant(Plant.SET_TEMP_MIN,Server.session.get("user"),reqJSON.getString("plant_signature"),Integer.parseInt(reqJSON.getString("max")));
                return 1;
            }));

            Spark.put("/flow", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                new Plant().updatePlant(Plant.SET_FLOW_MIN,Server.session.get("user"),reqJSON.getString("plant_signature"),Integer.parseInt(reqJSON.getString("min")));
                new Plant().updatePlant(Plant.SET_FLOW_MAX,Server.session.get("user"),reqJSON.getString("plant_signature"),Integer.parseInt(reqJSON.getString("max")));
                return 1;
            }));

            Spark.put("/light", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                new Plant().updatePlant(Plant.SET_LIGHT_RED,Server.session.get("user"),reqJSON.getString("plant_signature"),Integer.parseInt(reqJSON.getString("light_red")));
                new Plant().updatePlant(Plant.SET_LIGHT_GREEN,Server.session.get("user"),reqJSON.getString("plant_signature"),Integer.parseInt(reqJSON.getString("light_green")));
                new Plant().updatePlant(Plant.SET_LIGHT_BLUE,Server.session.get("user"),reqJSON.getString("plant_signature"),Integer.parseInt(reqJSON.getString("light_blue")));
                return 1;
            }));

            Spark.get("/", ((request, response) -> {
                JSONArray jsonArray = new JSONArray();

                ResultSet plants = new Plant().readPlant();

                while (plants.next()){
                    JSONObject o = new JSONObject(request.body());
                    o.put("plant_name", plants.getString("plant_name"));
                    o.put("plant_uploader", plants.getString("user_uploader"));
                    o.put("plant_signature", plants.getString("user_uploader"));
                    jsonArray.put(o);
                }

                return jsonArray;
            }));

            Spark.get("/:signature", ((request, response) -> {
                String s = request.params(":signature");
                JSONArray jsonArray = new JSONArray();

                ResultSet plants = new Plant().readPlant(s);

                while (plants.next()){
                    JSONObject o = new JSONObject(request.body());
                    o.put("plant_name", plants.getString("plant_name"));
                    o.put("plant_uploader", plants.getString("user_uploader"));
                    o.put("plant_signature", plants.getString("user_uploader"));
                    jsonArray.put(o);
                }

                return jsonArray;
            }));

            Spark.delete("/", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Plant().deletePlant(Server.session.get("user"),reqJSON.getString("plant_signature"));
            }));
        });

        Spark.path("/notification", () -> {
            Spark.post("/", ((request, response) -> "Not yet Available"));
            Spark.delete("/", ((request, response) -> "Not yet Available"));

            Spark.get("/:username/:device_signature", (request, response) -> {
                JSONArray jsonArray = new JSONArray();

                ResultSet notifications = new Notification().readNotification(request.params(":username"), request.params(":device_signature"));

                while (notifications.next()){
                    JSONObject o = new JSONObject();
                    o.put("notification_id",notifications.getString("notification_id"));
                    o.put("title",notifications.getString("notification_title"));
                    o.put("status",notifications.getString("notification_status"));
                    jsonArray.put(o);
                }
                return jsonArray;
            });

            Spark.get("/:username/:device_signature/:notification_id", (request, response) -> {
                JSONArray jsonArray = new JSONArray();

                ResultSet notifications = new Notification().readNotification(request.params(":username"), Integer.parseInt(request.params("notification_id")), request.params(":device_signature"));

                while (notifications.next()){
                    JSONObject o = new JSONObject();
                    o.put("notification_id",notifications.getString("notification_id"));
                    o.put("title",notifications.getString("notification_title"));
                    o.put("content",notifications.getString("notification_content"));
                    o.put("status",notifications.getString("notification_status"));
                    jsonArray.put(o);
                }
                return jsonArray;
            });

            Spark.put("/", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                new Notification().updateNotification(Notification.SET_READED, 1, reqJSON.getInt("notification_id"),reqJSON.getString("device_signature"));
                return 1;
            }));
        });

        Spark.path("/device", () -> {
            Spark.post("/", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Device().addDevice(Server.session.get("user"), 0, reqJSON.getString("device_type"), reqJSON.getString("device_signature"));
            }));

            Spark.delete("/", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Device().deleteDevice(Server.session.get("user"), reqJSON.getString("device_signature"));
            }));

            Spark.put("/temp", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Device().updateDevice(Device.TEMPERATURE, reqJSON.getInt("temperature"), reqJSON.getString("device_signature"), Server.session.get("user"));
            }));

            Spark.put("/plant", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Device().updateDevice(Device.PLANT, reqJSON.getInt("plant_id"), reqJSON.getString("device_signature"), Server.session.get("user"));
            }));

            Spark.put("/flow", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Device().updateDevice(Device.FLOW, reqJSON.getInt("flow"), reqJSON.getString("device_signature"), Server.session.get("user"));
            }));

            Spark.put("/light", ((request, response) -> {
                this.reqJSON = new JSONObject(request.body());
                return new Device().updateDeviceLight(reqJSON.getString("light"), reqJSON.getString("device_signature"), Server.session.get("user"));
            }));

            Spark.get("/", (request, response) -> {
                JSONArray jsonArray = new JSONArray();
                ResultSet device = new Device().readDevice();

                while (device.next()){
                    JSONObject o = new JSONObject();
                    o.put("device_type",device.getString("device_type"));
                    o.put("username",device.getString("username"));
                    jsonArray.put(o);
                }
                return jsonArray;
            });

            Spark.get("/:username", (request, response) -> {
                JSONArray jsonArray = new JSONArray();
                ResultSet device = new Device().readDevice(request.params(":username"));

                while (device.next()){
                    JSONObject o = new JSONObject();
                    o.put("device_type",device.getString("device_type"));
                    o.put("username",device.getString("username"));
                    o.put("device_signature",device.getString("device_signature"));
                    jsonArray.put(o);
                }
                return jsonArray;
            });

            Spark.get("/:username/:device_signature", (request, response) -> {
                JSONArray jsonArray = new JSONArray();
                ResultSet device = new Device().readDevice(request.params(":username"),request.params(":device_signature"));

                while (device.next()){
                    JSONObject o = new JSONObject();
                    o.put("device_type",device.getString("device_type"));
                    o.put("username",device.getString("username"));
                    o.put("plant_id",device.getString("plant_id"));
                    o.put("temp",device.getString("device_temp"));
                    o.put("light",device.getString("device_light"));
                    o.put("flow",device.getString("device_flow"));
                    jsonArray.put(o);
                }
                return jsonArray;
            });
        });
    }
}
