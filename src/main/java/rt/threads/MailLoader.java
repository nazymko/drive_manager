package rt.threads;

import javafx.concurrent.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rt.controllers.ChatController;
import rt.core.Session;
import rt.core.Urls;
import rt.models.Message;
import rt.util.IO;
import rt.util.UI;

import java.io.InputStream;
import java.util.List;

/**
 * User: Andrew.Nazymko
 */
public class MailLoader extends Task {

    private Integer id;

    public MailLoader(Integer id) {
        this.id = id;
    }

    @Override
    public Object call() throws Exception {
        try {
            HttpClient httpclient = Session.getClient();
            System.out.println("MailLoader started");
            HttpGet httpGet = new HttpGet(Urls.inboxMessages + id);

            HttpResponse response = null;

            response = httpclient.execute(httpGet);
            InputStream content = response.getEntity().getContent();
            String contentData = IO.readStream(content);
            /*Important block*/
            EntityUtils.consume(response.getEntity());
            /*Important block*/

            Document parse = Jsoup.parse(contentData);
            {
                Elements select = parse.select(".message-in-chain");

                List<Message> history = Session.getHistory(id);

                for (Element element : select) {
                    String name = element.select("a").first().text();
                    String text = element.ownText().substring(2); //Magic!
                    Message message = new Message(text, name);
                    if (!history.contains(message)) {
                        history.add(message);
                    }
                }


                List<Message> history1 = Session.getHistory(id);
                for (Message message : history1) {
                    System.out.println("message = " + message);
                }

            }
            UI.runIt(new Runnable() {
                @Override
                public void run() {
                    ChatController.getInstance().updateChatTable(id);
                    ChatController.getInstance().updateContactListUnreadMessages(id);
                }
            });
            System.out.println("MailLoader ended");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
