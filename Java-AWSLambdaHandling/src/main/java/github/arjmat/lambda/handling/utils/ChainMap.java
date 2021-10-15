package github.arjmat.lambda.handling.utils;
/*
 * handling - 15/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import java.util.HashMap;

public class ChainMap<K, V> extends HashMap<K, V> {
    public ChainMap<K, V> chainPut(K key, V value) {
        this.put( key, value );
        return this;
    }
}
