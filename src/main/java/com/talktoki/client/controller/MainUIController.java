/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.talktoki.chatinterfaces.commans.Message;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.model.Client;
import com.talktoki.client.model.HandleConnection;
import com.talktoki.client.view.ContactsCellFactory;
import com.talktoki.client.view.CustomContact;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

/**
 *
 * @author mahrous
 */
public class MainUIController implements Initializable {

    // Main container
    @FXML
    private AnchorPane main;

    // Icons
    @FXML
    private FontAwesomeIconView userIcon;
    @FXML
    private FontAwesomeIconView statusIcon;

    // Labels
    @FXML
    private Label username;
    @FXML
    private Label status;

    // Buttons
    @FXML
    private Button closeBtn;
    @FXML
    private Button minBtn;
    @FXML
    private JFXButton contactsBtn;
    @FXML
    private JFXButton groupsBtn;
    @FXML
    private JFXButton requestsBtn;
    @FXML
    private JFXButton logOut;
    @FXML
    private JFXButton addContactBtn;

    // Lists
    @FXML
    private ListView<User> contentList;

    private JFXListView<User> contactsList = new JFXListView();

    private JFXListView requestsList = new JFXListView();

    private JFXListView groupsList = new JFXListView();

    private ServerInterface myServer;
    private Client myclient;

    public MainUIController(HandleConnection myHandler, User myUser) {
        try {
            this.myServer = myHandler.getMyServerAuthInt();
            myclient = Client.getInstance(this, myUser, myServer);
            myServer.addClient(myclient);
        } catch (RemoteException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MainUIController() {
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            main.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
//            username.setText(myclient.getUser().getUserName());
//            status.setText(myclient.getUser().getStatus());
//            statusIcon.setFill(Color.GREEN);

            //Testing data
            ObservableList<User> contacts = FXCollections.observableArrayList(
                    new User("Bodour", "Bodour@mail.com", null, "female", "Egypt", "online"),
                    new User("Ibrahim", "hema@mail.com", null, "male", "UK", "offline"),
                    new User("Bassem", "Bassem@gmail.cm", null, "female", "Egypt", "online")
            );
            contentList.getItems().setAll(contacts);
            contentList.setCellFactory(new ContactsCellFactory());

//            //contentList = contactsList;
//            //Make requests list cell custom cell
//            requestsList.setCellFactory(new Callback() {
//                @Override
//                public Object call(Object param) {
//                    return new RequestCell();
//                }
//            });
//        } catch (RemoteException ex) {
//            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void exit() {
        Alert mylert = new Alert(Alert.AlertType.ERROR);
        mylert.setTitle("ERROR");
        mylert.setHeaderText("Unexcpected ERROR!");
        mylert.setContentText("SERVER FAILED UNEXPECTEDLY, EXITING.....");
        mylert.showAndWait();
        System.exit(0);
    }

    public void showRequestNotification(String sender_name, String sender_email) {
        System.out.println("Received from:" + sender_email + " Friendship request");
        requestsList.getItems().add(sender_email);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notifications.create()
                        .title("Friendship Request!")
                        .text(sender_name + " Sent you a friendship request!")
                        .darkStyle()
                        .showInformation();
            }

        });

    }

    public void friendshipRequestResponse(String sender_email, boolean accepted) {
        try {
            myServer.friendshipRequestResponse(myclient.getUser().getEmail(), sender_email, accepted);
        } catch (RemoteException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void openOrAppendToSingleChat(String sender_email, Message message) {
        System.out.println("Received from:" + sender_email + " Message:" + message.getText());
    }

    public void logoutConfirmation() {
        Alert myalert = new Alert(Alert.AlertType.CONFIRMATION);
        myalert.setTitle("Signout confirmation");
        myalert.setContentText("Are you sure you want to log out?");
        Optional result = myalert.showAndWait();
        if (result.get() == ButtonType.OK) {
            logout();
        }

    }

    public void removeRequestFromPending(String sender_email) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                requestsList.getItems().remove(sender_email);
            }
        });
    }

    public void loadRequests() {
        contentList = requestsList;
    }

    public void minimize() {
        Stage mystage = (Stage) main.getScene().getWindow();
        mystage.setIconified(true);

    }

    public void logout() {
        try {
            myServer.signOut(myclient);
            System.exit(0);
        } catch (RemoteException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showAddContactDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add friend");
        dialog.setHeaderText("Add new contact");
        dialog.setContentText("Please enter an email:");

        // TODO VALIDATE EMAIL AND RESHOW ANOTHER DIALOG IF NOT CORRECT
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                myServer.sendFriendshipRequest(myclient.getUser().getEmail(), result.get());
                // TODO CHECK SERVER THAT THE USER EXISTS
            } catch (RemoteException ex) {
                Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void testSend() {

        try {
            Message mymsg = new Message();
            mymsg.setText("HI FROM" + myclient.getUser().getEmail());
            myServer.sendToOne(myclient.getUser().getEmail(), "bassemgawesh@gmail.com", mymsg);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

//    class RequestCell extends ListCell<String> {
//
//        @Override
//        protected void updateItem(String sender_email, boolean empty) {
//            super.updateItem(sender_email, empty);
//            if (sender_email == null || empty) {
//                setGraphic(null);
//            } else {
//                VBox request = new VBox(5);
//                request.setId(sender_email);
//                request.getChildren().add(new Label("From " + sender_email));
//
//                JFXButton accept = new JFXButton("Accept");
//                accept.setOnAction(new EventHandler<ActionEvent>() {
//
//                    @Override
//                    public void handle(ActionEvent event) {
//                        friendshipRequestResponse(sender_email, true);
//                        removeRequestFromPending(sender_email);
//                    }
//                });
//
//                JFXButton refuse = new JFXButton("Refuse");
//                accept.setOnAction(new EventHandler<ActionEvent>() {
//
//                    @Override
//                    public void handle(ActionEvent event) {
//                        friendshipRequestResponse(sender_email, false);
//                        removeRequestFromPending(sender_email);
//
//                    }
//                });
//                HBox responseButtons = new HBox(accept, refuse);
//                responseButtons.setSpacing(5);
//                request.getChildren().add(responseButtons);
//                setGraphic(request);
//
//            }
//        }
//
//    }
}
