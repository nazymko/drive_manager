package rt.core;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Andrew.Nazymko
 */
public class Session {
//    static Map<Integer, Map<String, String>> damp = new HashMap<Integer, Map<String, String>>();

    static Map<String, String> session = new HashMap<String, String>();

    public static Map<String, String> session() {
        return session;
    }

    public String get(String key) {
        return session.get(key);
    }

    public void put(String key, String value) {
        session.put(key, value);
    }


}
