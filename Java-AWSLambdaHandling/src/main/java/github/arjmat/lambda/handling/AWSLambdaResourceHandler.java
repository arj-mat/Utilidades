package github.arjmat.lambda.handling;
/*
 * handling - 15/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AWSLambdaResourceHandler {
    String value(); // Exemplo de resource path: /nome-função-lambda/nome-resource
}
