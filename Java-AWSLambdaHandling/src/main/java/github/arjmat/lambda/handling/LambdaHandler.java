package github.arjmat.lambda.handling;
/*
 * handling - 15/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

// LambdaHandler<Request Body, Query params, Path params, Response Body>

public abstract class LambdaHandler<ReqB, Q, P, ResB> {
 public static String getAnnotatedResourcePath(Class<LambdaHandler> sourceClass) {
  return sourceClass.getAnnotation( AWSLambdaResourceHandler.class ).value();
 }

 public abstract ResB handleRequest(LambdaEvent<ReqB, Q, P> event);

 public abstract ReqB requestBodySupplier();
}
