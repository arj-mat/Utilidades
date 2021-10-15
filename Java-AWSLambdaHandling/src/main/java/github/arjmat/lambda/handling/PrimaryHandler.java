package github.arjmat.lambda.handling;
/*
 * handling - 15/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.GsonBuilder;
import github.arjmat.lambda.handling.utils.ChainMap;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PrimaryHandler implements RequestHandler<PrimaryHandler.Event, Object> {
    public static class Event {
        public String method;
        public String resourcePath;
        public String remoteIp;
        public Object body;
        public Map<String, String> queryParams;
        public Map<String, String> pathParams;
        public Map<String, String> headers;
    }

    private Map<String, LambdaHandler> secondaryHandlers = new HashMap<>();

    public PrimaryHandler() {
        String targetPackage = System.getenv( "LAMBDA_HANDLER_PACKAGE" );

        if ( targetPackage == null ) {
            throw new Error( "A variável de ambiente LAMBDA_HANDLER_PACKAGE não foi definida. Defina nesta variável o caminho do pacote " +
                                     "contendo os LambdaHandlers a serem utilizados." );
        }

        var annotatedTypes = new Reflections( targetPackage )
                .getTypesAnnotatedWith( AWSLambdaResourceHandler.class );

        if ( annotatedTypes.size() == 0 ) {
            throw new Error( "Nenhum tipo anotado com @AWSLambdaResourceHandler foi encontrado no pacote " + targetPackage );
        }

        annotatedTypes.forEach( this::registerResourceHandler );
    }


    @Override
    public Object handleRequest(PrimaryHandler.Event event, Context context) {
        if ( this.secondaryHandlers.containsKey( event.resourcePath ) ) {
            Object parsedBody = null;

            if ( event.body instanceof String && !( (String) event.body ).isEmpty() && this.secondaryHandlers.get(
                    event.resourcePath )
                                                                                                             .requestBodySupplier() != null ) {
                parsedBody = new GsonBuilder().create()
                                              .fromJson( (String) event.body,
                                                         this.secondaryHandlers
                                                                 .get( event.resourcePath )
                                                                 .requestBodySupplier()
                                                                 .getClass()
                                              );
            }

            if ( event.body != null && !( event.body instanceof String ) ) {
                return new ChainMap<String, Object>()
                        .chainPut( "error",
                                   "Tipo inválido do body recebido da integração: " + ( event.body == null ? "null" :
                                                                                        event.body.getClass().getSimpleName() ) +
                                           " " +
                                           ". O tipo esperado deveria ser String. Verificar o template de integração da API Gateway." );
            }

            return this.secondaryHandlers.get( event.resourcePath ).handleRequest(
                    LambdaEvent.builder()
                               .queryParams( event.queryParams )
                               .pathParams( event.pathParams )
                               .headers( event.headers )
                               .method( event.method )
                               .remoteIp( event.remoteIp )
                               .body( parsedBody )
                               .build()
            );
        } else {
            Map<String, String> result = new HashMap<>();

            result.put( "error", "Recurso " + event.resourcePath + " não implementado com nenhum LambdaHandler." );

            return result;
        }
    }

    private void registerResourceHandler(Class handlerClass) {
        if ( handlerClass.getSuperclass().equals( LambdaHandler.class ) ) {
            String resourcePath = LambdaHandler.getAnnotatedResourcePath( handlerClass );

            if ( resourcePath == null || resourcePath.isEmpty() ) {
                throw new Error( "A classe " + handlerClass.getName() + " deve ter seu resource path na anotação " +
                                         "@AWSLambdaResourceHandler." );
            }

            try {
                this.secondaryHandlers.put(
                        resourcePath,
                        (LambdaHandler) handlerClass.getConstructors()[0].newInstance()
                );
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            } catch ( InvocationTargetException e ) {
                e.printStackTrace();
            }
        } else {
            throw new Error( "A classe " + handlerClass.getSimpleName() + " anotada com @AWSLambdaResourceHandler deve extender de " + LambdaHandler.class
                    .getName() + "." );
        }
    }
}
