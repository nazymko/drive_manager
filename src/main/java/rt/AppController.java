package rt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rt.controllers.ChatController;
import rt.core.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppController extends Application {

    private static AppController instance;
    Map<String, Parent> preloadedStore = new HashMap<String, Parent>();
    private Stage stage;

    public AppController() {
        instance = this;
    }

    public static AppController getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../layouts/login.fxml"));
        stage.setTitle("TeamFinding");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Session.setShutdown(true);
            }
        });


    }

    public void setupStageChat() {
        try {
            replaceSceneContent("../layouts/chat.fxml");
            ChatController.getInstance().updateUserList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUsers() {
        ChatController.getInstance();
    }

    private Parent replaceSceneContent(String fxml) {
        Parent page = preloadedStore.get(fxml);
        if (page == null) {
            try {
                page = FXMLLoader.load(AppController.class.getResource(fxml), null, new JavaFXBuilderFactory());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(page);
                stage.setScene(scene);
            }
        }
        stage.getScene().setRoot(page);
        return page;
    }
}
