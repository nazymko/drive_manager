package rt.threads.daemons;

import rt.core.Session;
import rt.threads.daemons.usedbydemons.InboxChecker;

/**
 * User: Andrew.Nazymko
 */
public class DInboxChecker extends Thread {

    @Override
    public void run() {
        setDaemon(true);
        while (true) {
            if (Session.isAuthorized()) {
                new Thread(new InboxChecker()).start();
            }
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
