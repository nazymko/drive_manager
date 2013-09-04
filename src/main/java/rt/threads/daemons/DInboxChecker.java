package rt.threads.daemons;

import rt.core.Session;
import rt.threads.daemons.usedbydemons.InboxChecker;

/**
 * User: Andrew.Nazymko
 */
public class DInboxChecker extends Thread {
    public DInboxChecker() {
        setDaemon(true);
    }

    public static final int UPDATE_TIME = 30 * 1000;

    @Override
    public void run() {
        while (!Session.isShutdown()) {
            try {
                Thread.sleep(UPDATE_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Session.isAuthorized() && !Session.isShutdown()) {
                new Thread(new InboxChecker()).start();
            }
        }
    }
}
