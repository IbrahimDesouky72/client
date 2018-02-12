/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.talktoki.chatinterfaces.commans.Message;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.model.Client;
import com.talktoki.client.model.HandleConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 *
 * @author mahrous
 */
public class MainUIController implements Initializable {

    @FXML
    private AnchorPane main;
    @FXML
    private FontAwesomeIconView userIcon;
    @FXML
    private Label username;
    @FXML
    private Label status;
    @FXML
    private FontAwesomeIconView statusIcon;
    @FXML
    private Button closeBtn;
    @FXML
    private Button minBtn;
    @FXML
    private JFXButton contactsBtn;
    @FXML
    private JFXButton groupsBtn;
    @FXML
    private JFXButton logOut;
    @FXML
    private JFXButton addContactBtn;
    @FXML
    private JFXListView contacts;
    private ServerInterface myServer;
    private Client myclient;

    private HashMap<String, Integer> mycontacts;

    public MainUIController(HandleConnection myHandler, User myUser) {
        try {
            this.myServer = myHandler.getMyServerAuthInt();
            myclient = Client.getInstance(this, myUser, myServer);
            myServer.addClient(myclient);
        } catch (RemoteException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            main.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
            username.setText(myclient.getUser().getUserName());
            status.setText(myclient.getUser().getStatus());
            statusIcon.setFill(Color.GREEN);

            // Initialize contact list
            /*mycontacts = myServer.getContactList(myclient.getUser().getEmail());
             contacts.getItems().addAll(mycontacts);*/
        } catch (RemoteException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        JFXButton accept = new JFXButton("Accept");
        accept.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                friendshipRequestResponse(sender_email, true);
            }
        });

        JFXButton refuse = new JFXButton("Refuse");
        accept.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                friendshipRequestResponse(sender_email, false);
            }
        });
        HBox mybox = new HBox(accept, refuse);
        mybox.setSpacing(5);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Notifications.create()
                        .title("Friendship Request!")
                        .text(sender_name + " Sent you a friendship request!")
                        .darkStyle()
                        .graphic(mybox)
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
        // TODO CHECK SERVER THAT THE USER EXISTS
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                myServer.sendFriendshipRequest(myclient.getUser().getEmail(), result.get());
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

}
