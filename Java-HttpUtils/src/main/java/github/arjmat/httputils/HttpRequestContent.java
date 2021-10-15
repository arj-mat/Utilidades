package github.arjmat.httputils;

/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class HttpRequestContent {
    private String contentType;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String content;

    public HttpRequestContent json(Object source) {
        this.content = new GsonBuilder().create().toJson( source );
        this.contentType = "application/json";

        return this;
    }

    public HttpRequestContent encodedFormData(Map<String, String> source) {
        List<String> formLines = new ArrayList<>();

        source.forEach( (key, value) -> {
            formLines.add(
                    String.format(
                            "%s=%s",
                            URLEncoder.encode( key, StandardCharsets.UTF_8 ),
                            URLEncoder.encode( value, StandardCharsets.UTF_8 )
                    )
            );
        } );

        this.content = String.join( "&", formLines );
        this.contentType = "application/x-www-form-urlencoded";

        return this;
    }

    public HttpRequest.BodyPublisher getBodyPublisher() {
        return HttpRequest.BodyPublishers.ofString( this.content );
    }
}
