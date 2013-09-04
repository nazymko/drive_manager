package rt.threads;


import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import rt.core.Session;
import rt.util.IO;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: Andrew.Nazymko
 */
public class Login implements Runnable {
    private final String login;
    private final String passwordText;

    public Login(String login, String passwordText) {
        this.login = login;
        this.passwordText = passwordText;
    }

    @Override
    public void run() {
        try {

            System.out.println("Started");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://teamfinding.com/");

            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("User[login]", "nazymko"));
            params.add(new BasicNameValuePair("User[password]", "19902105"));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));


            HttpResponse response = httpclient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();

            Header[] headers = response.getHeaders("Set-Cookie");

            for (Header header : headers) {
                HeaderElement[] elements1 = header.getElements();
                for (HeaderElement headerElement : elements1) {
                    Session.session().put(headerElement.getName(), headerElement.getValue());
                }
            }
            EntityUtils.consume(response.getEntity());


            HttpGet httpGet = new HttpGet("http://teamfinding.com/ru/message/inbox");
            {
                httpGet.addHeader("PHPSESSID", Session.session().get("PHPSESSID"));
                httpGet.addHeader("5c1b665641afa7d9460437aaa09ee86f", Session.session().get("5c1b665641afa7d9460437aaa09ee86f"));
            }

            HttpResponse execute = httpclient.execute(httpGet);
            InputStream content = execute.getEntity().getContent();
            String contentData = IO.readStream(content);
            System.out.println("contentData = " + contentData);

            System.out.println("execute = " + execute);

            System.out.println("Ended");

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
