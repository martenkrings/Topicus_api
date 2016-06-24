package myjson;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Deze klasse zorgt voor het vertalen van een object naar json.
 */
public class JsonUtil {
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
