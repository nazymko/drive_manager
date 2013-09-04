package rt.core;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import rt.models.Human;
import rt.models.Message;

import java.util.*;

/**
 * User: Andrew.Nazymko
 */
public class Session {
    public static List activeChats = new ArrayList();
    static Map<String, String> sessionStorage = new HashMap<String, String>();
    static HttpClient client = new DefaultHttpClient();
    static List<Human> senders = new ArrayList<Human>();
    static Map<Integer, List<Message>> history = new HashMap<Integer, List<Message>>();
    static boolean authorized = false;
    static WeakHashMap<Integer, Human> weakSenders = new WeakHashMap<Integer, Human>();

    public static List<Message> getHistory(Integer id) {
        List<Message> messages;
        if (history.containsKey(id)) {
            messages = history.get(id);
        } else {
            messages = new ArrayList<Message>();
            history.put(id, messages);
        }
        return messages;
    }

    public static List<Human> getSenders() {
        return senders;
    }

    public static Map<String, String> session() {
        return sessionStorage;
    }

    public static HttpClient getClient() {
        return client;
    }

    public static boolean isAuthorized() {
        return authorized;
    }

    public static void setAuthorized(boolean authorized) {
        Session.authorized = authorized;
    }

    public static void addUniqueSender(Human human) {
        if (!senders.contains(human)) {
            senders.add(human);
            weakSenders.put(human.getId(), human);
        }

    }

//    public static WeakHashMap<Integer, Human> getWeakSenders() {
//        return weakSenders;
//    }

    public static String get(String key) {
        return sessionStorage.get(key);
    }

    public static void put(String key, String value) {
        sessionStorage.put(key, value);
    }

    public static Human getContactById(int id) {
        return weakSenders.get(id);
    }
}
