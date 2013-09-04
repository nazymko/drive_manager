package rt.util;

import javafx.application.Platform;

/**
 * User: Andrew.Nazymko
 */
public class UI {

    public static void runIt(Runnable taskToUi) {
        Platform.runLater(taskToUi);
    }
}
