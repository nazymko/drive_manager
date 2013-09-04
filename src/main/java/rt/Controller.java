package rt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rt.threads.Login;


public class Controller {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField password;
    @FXML
    public TextField login;
    UILogger LOG;
    @FXML
    private CheckBox checkbox = new CheckBox();

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    @FXML
    public void changeState(ActionEvent actionEvent) {

    }

    @FXML
    public void auth2(ActionEvent actionEvent) {
        if (LOG == null) {
            LOG = new UILogger(textArea);
        }
        LOG.write("Try to login with login '" + login.getText() + "'");
        new Thread(new Login(login.getText(), password.getText())).start();
    }
}
