/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import com.google.gson.internal.LinkedTreeMap;
import github.arjmat.httputils.HttpUtils;
import org.junit.jupiter.api.Test;

public class NonModelledSample {
    @Test
    public void test() {
        System.out.println( "Executing GET to https://raw.githubusercontent.com/arj-mat/meeting-room-frontend/master/package.json..." );

        var result = HttpUtils.get( "https://raw.githubusercontent.com/arj-mat/meeting-room-frontend/master/package.json" );

        System.out.printf( "Response status: %d.\n", result.status );

        if ( result.data.isPresent() ) {
            System.out.printf( "Package name: %s\n", result.data.get().get( "name" ) );

            LinkedTreeMap<String, String> packageDependencies = (LinkedTreeMap<String, String>) result.data.get().get( "dependencies" );

            for ( var entry : packageDependencies.entrySet() ) {
                System.out.printf( "Dependency: %s\n\tVersion: %s\n", entry.getKey(), entry.getValue() );
            }
        } else {
            System.out.println( "Failed to load data." );
        }
    }
}
