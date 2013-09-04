package rt.threads;


import javafx.concurrent.Task;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import rt.core.Session;
import rt.threads.daemons.DInboxChecker;
import rt.threads.daemons.usedbydemons.InboxChecker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrew.Nazymko
 */
public class Login extends Task {
    private final String login;
    private final String passwordText;

    public Login(String login, String passwordText) {
        this.login = login;
        this.passwordText = passwordText;
    }

    @Override
    protected Object call() throws Exception {
        try {
            synchronized (Session.getClient()) {
                HttpClient httpclient = Session.getClient();
                HttpPost httppost = new HttpPost("http://teamfinding.com/");

                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("User[login]", login));
                params.add(new BasicNameValuePair("User[password]", passwordText));
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));


                HttpResponse response = httpclient.execute(httppost);
                int code = response.getStatusLine().getStatusCode();
                if (code == 302) {
                    System.out.println("Good credentials! We are IN");
                    new Thread(new DInboxChecker()).start();
                } else {
                    System.out.println("WTF, Who are you - GET OF!!");
                }
                Header[] headers = response.getHeaders("Set-Cookie");

                for (Header header : headers) {
                    HeaderElement[] elements1 = header.getElements();
                    for (HeaderElement headerElement : elements1) {
                        Session.session().put(headerElement.getName(), headerElement.getValue());
                    }
                }
                     /*Important block*/
                EntityUtils.consume(response.getEntity());
                    /*Important block*/
            }
            new Thread(new InboxChecker()).start();
            Session.setAuthorized(true);
            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }
}
