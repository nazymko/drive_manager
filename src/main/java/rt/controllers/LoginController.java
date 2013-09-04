package rt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rt.UILogger;
import rt.threads.Login;


public class LoginController {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField password;
    @FXML
    public TextField login;
    UILogger LOG;
    @FXML
    private CheckBox checkbox = new CheckBox();

    @FXML
    public void changeState(ActionEvent actionEvent) {

    }

    @FXML
    public void auth2(ActionEvent actionEvent) {
        new Thread(new Login(login.getText(), password.getText())).start();
    }
}
