package github.arjmat.lambda.handling;
/*
 * handling - 15/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LambdaEvent<B, Q, P> {
    public String method;
    public String resourcePath;
    public String remoteIp;
    public B body;
    public Q queryParams;
    public P pathParams;
    public Map<String, String> headers;
}