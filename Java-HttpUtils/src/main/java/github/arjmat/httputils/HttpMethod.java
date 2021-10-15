package github.arjmat.httputils;

/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HttpMethod {
    GET( "GET" ),
    POST( "POST" ),
    PUT( "PUT" ),
    DELETE( "DELETE" );

    private final String value;
}