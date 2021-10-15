package github.arjmat.httputils;

/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpResponseEntity<T> {
    public Optional<T> data = Optional.empty();
    public int status = -1;
    public Map<String, String> headers = new HashMap<>();

    public HttpResponseEntity(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return "HttpResponse{\n" +
                "data=" + ( data.isPresent() ? data.get().getClass().getSimpleName() + gson.toJson( data.get() ) :
                            "null" ) +
                ",\nstatus=" + status +
                ",\nheaders=" + gson.toJson( headers ) +
                "\n}";
    }
}
