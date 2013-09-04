package rt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import rt.core.Session;
import rt.models.Human;
import rt.models.Message;
import rt.threads.MailLoader;
import rt.threads.Sender;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrew.Nazymko
 */
public class ChatController {
    static ChatController instance;
    public TextField sendField;
    public ListView userList;
    public TextArea chattingField;
    public Tab contactName;
    public TextArea chatTable;
    private List<Message> chatTableMessageList = new ArrayList<Message>();
    private int currentUserId = 0;

    public ChatController() {
        instance = this;
    }

    public static ChatController getInstance() {
        return instance;
    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        updateUserList();
        int id = 861;
        new Thread(new Sender(sendField.getText(), id)).start();
    }

    public void updateUserList() {
        List<Human> senders = Session.getSenders();
        userList.getItems().clear();
        for (Human sender : senders) {
//            if (!userList.getItems().contains(sender)) {
            userList.getItems().add(sender);
//            } else {
//                System.out.println("sender = " + sender + " exist in list");
//            }
        }
//        userList.getItems().retainAll(senders);
//        userList.getItems().
    }

    @FXML
    public void userInfoRequesr(ContextMenuEvent arg0) {
    }

    @FXML
    public void mouseClicked(MouseEvent arg0) {

        if (arg0.getClickCount() > 1) {
            Human humanName = (Human) userList.getSelectionModel().getSelectedItem();
            setupChatWihId(humanName.getId());
            contactName.setText(humanName.getName());
        }
    }

    private void setupChatWihId(Integer id) {
        Session.getSenders();
        if (!Session.activeChats.contains(id)) {
            if (currentUserId != id) {
                new Thread(new MailLoader(id)).start();
                chatTable.clear();
                currentUserId = id;

            } else {
                System.out.println("Stop! You already talk with him!");
            }
        } else {

            //Change active tab and refresh
        }

    }

    public void updateChatTable(int id) {

        List<Message> history = Session.getHistory(id);
        for (Message message : history) {
            if (!chatTableMessageList.contains(message)) {
                chatTable.appendText(message.getFrom() + ": " + message.getMessage() + "\n\n");
                chatTableMessageList.add(message);
            }
        }

    }

    public void updateContactListUnreadMessages(Integer id) {
        Session.getContactById(id).setUnread(0);
        updateUserList();

    }
}
