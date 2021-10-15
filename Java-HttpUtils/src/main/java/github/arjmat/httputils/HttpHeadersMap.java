package github.arjmat.httputils;

/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import java.util.HashMap;

public class HttpHeadersMap extends HashMap<String, String> {
    public HttpHeadersMap chainPut(String name, String value) {
        this.put( name, value );

        return this;
    }
}