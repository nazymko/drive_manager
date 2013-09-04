package rt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rt.controllers.ChatController;

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

    }

    public void stageChat() {
        try {
            replaceSceneContent("../layouts/chat.fxml");
            ChatController.getInstance().updateUserList();
//            ChatController.getInstance().userList.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    if (mouseEvent.getClickCount() > 1) {
//                        Node node = ((ListCellSkin) mouseEvent.getTarget());
//                        System.out.println("node = " + node);
//                    }
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUsers() {
        ChatController.getInstance();
    }

    private Parent replaceSceneContent(String fxml) {
        Parent page = preloadedStore.get(fxml);
        System.out.println("command to change stage on " + fxml);
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
