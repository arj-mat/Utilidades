package github.arjmat.httputils;

/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public abstract class HttpUtils {
    public static HttpResponseEntity<LinkedTreeMap<String, Object>> get(String url) {
        return HttpUtils.request( LinkedTreeMap.class, HttpMethod.GET, url, null, null );
    }

    public static <T> HttpResponseEntity<T> get(Type resultType, String url) {
        return HttpUtils.request( resultType, HttpMethod.GET, url, null, null );
    }

    public static <T> HttpResponseEntity<T> get(Type resultType, String url, Map<String, String> headers) {
        return HttpUtils.request( resultType, HttpMethod.GET, url, headers, null );
    }

    public static HttpResponseEntity<LinkedTreeMap<String, Object>> get(String url, Map<String, String> headers) {
        return HttpUtils.request( LinkedTreeMap.class, HttpMethod.GET, url, headers, null );
    }

    public static <T> HttpResponseEntity<T> put(String url, Map<String, String> headers) {
        return HttpUtils.request( null, HttpMethod.PUT, url, headers, null );
    }

    public static <T> HttpResponseEntity<T> request(Type resultType, HttpMethod method, String url,
                                                    Map<String, String> headers, HttpRequestContent requestContent) {
        HttpClient client = HttpClient.newBuilder().build();

        Optional<HttpRequestContent> requestBody = Optional.ofNullable( requestContent );

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                                                        .version( HttpClient.Version.HTTP_1_1 )
                                                        .method(
                                                                method.name(),
                                                                requestBody.isPresent()
                                                                ?
                                                                requestBody.get().getBodyPublisher()
                                                                :
                                                                HttpRequest.BodyPublishers.noBody()
                                                        )
                                                        .uri( URI.create( url ) );

        if ( requestBody.isPresent() ) {
            requestBuilder.setHeader( "content-type", requestBody.get().getContentType() );
            requestBuilder.setHeader( "accepts", "application/json" );
        }

        if ( headers != null ) {
            for ( var entry : headers.entrySet() ) {
                requestBuilder.setHeader( entry.getKey().toLowerCase(), entry.getValue() );
            }
        }

        String rawResponseData = "";
        int responseStatusCode = -1;

        try {
            HttpResponse<String> httpResponse = client.send( requestBuilder.build(), HttpResponse.BodyHandlers.ofString() );

            responseStatusCode = httpResponse.statusCode();

            HttpResponseEntity<T> responseEntity = new HttpResponseEntity<>( responseStatusCode );

            var headersMap = httpResponse.headers().map();

            for ( var header : headersMap.entrySet() ) {
                responseEntity.headers.put( header.getKey(), header.getValue().get( 0 ) );
            }

            rawResponseData = httpResponse.body();

            if ( resultType != null && ( httpResponse.statusCode() == 200 || httpResponse.statusCode() == 204 ) && !rawResponseData.isEmpty() ) {
                Gson gson = new GsonBuilder().create();

                responseEntity.data = Optional.ofNullable( gson.fromJson( rawResponseData, resultType ) );
            } else {
                if ( httpResponse.statusCode() != 204 ) {
                    System.err.printf( "[%s] [%d]: %s\n", url, httpResponse.statusCode(), rawResponseData );
                }

                return new HttpResponseEntity<>( responseStatusCode );
            }

            return responseEntity;
        } catch ( IOException | InterruptedException e ) {
            e.printStackTrace();
            System.err.printf( "[%s] [%d]: %s\n", url, responseStatusCode, rawResponseData );

            return new HttpResponseEntity<>( -1 );
        }
    }

    public static <T> HttpResponseEntity<T> requestWithEncodedFormBody(HttpMethod method, Type resultType, String url,
                                                                       Map<String, String> body,
                                                                       HttpHeadersMap headers) {
        return HttpUtils.request(
                resultType,
                method,
                url,
                headers,
                new HttpRequestContent().encodedFormData( body )
        );
    }

    public static <T> HttpResponseEntity<T> requestWithJSONBody(HttpMethod method, Type resultType, String url,
                                                                Object body,
                                                                Map<String, String> headers) {
        return HttpUtils.request(
                resultType,
                method,
                url,
                headers,
                new HttpRequestContent().json( body )
        );
    }

    // POST ENCODED FORM

    public static HttpResponseEntity<LinkedTreeMap<Object, Object>> postEncodedForm(String url, Map<String, String> body) {
        return HttpUtils.requestWithEncodedFormBody( HttpMethod.POST, LinkedTreeMap.class, url, body, null );
    }

    public static HttpResponseEntity<LinkedTreeMap<Object, Object>> postEncodedForm(String url, Map<String, String> body,
                                                                                    HttpHeadersMap headers) {
        return HttpUtils.requestWithEncodedFormBody( HttpMethod.POST, LinkedTreeMap.class, url, body, headers );
    }

    public static <T> HttpResponseEntity<T> postEncodedForm(Class<T> resultType, String url, Map<String, String> body) {
        return HttpUtils.requestWithEncodedFormBody( HttpMethod.POST, resultType, url, body, null );
    }

    public static <T> HttpResponseEntity<T> postEncodedForm(Class<T> resultType, String url, Map<String, String> body,
                                                            HttpHeadersMap headers) {
        return HttpUtils.requestWithEncodedFormBody( HttpMethod.POST, resultType, url, body, headers );
    }

    // PUT ENCODED FORM

    public static HttpResponseEntity<?> putEncodedForm(String url, Map<String, String> body) {
        return HttpUtils.requestWithEncodedFormBody( HttpMethod.PUT, null, url, body, null );
    }

    public static HttpResponseEntity<?> putEncodedForm(String url, Map<String, String> body, HttpHeadersMap headers) {
        return HttpUtils.requestWithEncodedFormBody( HttpMethod.PUT, null, url, body, headers );
    }

    // POST JSON

    public static HttpResponseEntity<LinkedTreeMap<String, Object>> postJSON(String url, Object body) {
        return HttpUtils.requestWithJSONBody( HttpMethod.POST, LinkedTreeMap.class, url, body, null );
    }

    public static HttpResponseEntity<LinkedTreeMap<String, Object>> postJSON(String url, Object body, Map<String, String> headers) {
        return HttpUtils.requestWithJSONBody( HttpMethod.POST, LinkedTreeMap.class, url, body, headers );
    }

    public static <T> HttpResponseEntity<T> postJSON(Type resultType, String url, Object body) {
        return HttpUtils.requestWithJSONBody( HttpMethod.POST, resultType, url, body, null );
    }

    public static <T> HttpResponseEntity<T> postJSON(Type resultType, String url,
                                                     Object body,
                                                     HttpHeadersMap headers) {
        return HttpUtils.requestWithJSONBody( HttpMethod.POST, resultType, url, body, headers );
    }

    // PUT JSON

    public static HttpResponseEntity<?> putJSON(String url, Object body) {
        return HttpUtils.requestWithJSONBody( HttpMethod.PUT, Object.class, url, body, null );
    }

    public static HttpResponseEntity<?> putJSON(String url, Object body, Map<String, String> headers) {
        return HttpUtils.requestWithJSONBody( HttpMethod.PUT, null, url, body, headers );
    }
}
