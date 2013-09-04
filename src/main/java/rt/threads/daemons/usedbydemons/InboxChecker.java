package rt.threads.daemons.usedbydemons;

import javafx.concurrent.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rt.AppController;
import rt.core.Session;
import rt.core.Urls;
import rt.models.Human;
import rt.util.IO;
import rt.util.UI;

import java.io.InputStream;

/**
 * User: Andrew.Nazymko
 */
public class InboxChecker extends Task {
    @Override
    protected Object call() throws Exception {
        HttpClient httpclient = Session.getClient();
        System.out.println("Inbox started");
        HttpGet httpGet = new HttpGet(Urls.inbox);

        HttpResponse response = httpclient.execute(httpGet);
        InputStream content = response.getEntity().getContent();
        String contentData = IO.readStream(content);
        Document parse = Jsoup.parse(contentData);
        {
            Elements select = parse.select(".pm-message-box");


            try {
                for (Element element : select) {
                    String name = element.select("td.left-box.pull-left div.pull-left a").first().text();

                    String href = element.select("td").get(1).select("a").first().attr("href");
                    String[] split = href.split("/");
                    String userStringId = split[split.length - 1];
                    Integer id = Integer.valueOf(userStringId);

                    int isUnreaded;
                    Element mailNotifier = element.select("td").get(1).select("span").first();
                    if (mailNotifier != null) {
                        isUnreaded = 1;
                    } else {
                        isUnreaded = 0;
                    }

                    Human human = new Human(name, id, isUnreaded);
                    Session.addUniqueSender(human);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        EntityUtils.consume(response.getEntity());
        UI.runIt(new Runnable() {
            @Override
            public void run() {
                AppController.getInstance().stageChat();
            }
        });
        System.out.println("Inbox ended");
        return true;
    }
}
