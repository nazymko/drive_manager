package rt;

import javafx.scene.control.TextArea;

/**
 * User: Andrew.Nazymko
 */
public class UILogger {
    private TextArea area;

    public UILogger(TextArea area) {

        this.area = area;
    }

    public void write(String s) {
        area.appendText(s);
        area.appendText("\n");
    }
}
