package rt.threads;

import javafx.concurrent.Task;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import rt.core.Session;
import rt.core.Urls;
import rt.threads.daemons.usedbydemons.InboxChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrew.Nazymko
 */
public class Sender extends Task {
    private final String text;
    private final int id;

    public Sender(String messageToSend, int toId) {
        this.text = messageToSend;
        this.id = toId;
    }

    @Override
    protected Object call() throws Exception {
        System.out.println("Sender start");
        HttpPost httpPost = new HttpPost(Urls.replyTo + id);

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("PersonalMessage[text]", text));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        System.out.println("Before execute");
        try {
            HttpResponse response = Session.getClient().execute(httpPost);
            EntityUtils.consume(response.getEntity());

            new Thread(new InboxChecker()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sender end");

        return true;
    }
}
