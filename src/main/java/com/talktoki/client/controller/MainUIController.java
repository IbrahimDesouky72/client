/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.talktoki.chatinterfaces.commans.Message;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.model.Client;
import com.talktoki.client.model.HandleConnection;
import com.talktoki.client.view.CustomContact;
import com.talktoki.client.view.CustomRequest;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author mahrous
 */
public class MainUIController implements Initializable {

    // Panes
    @FXML
    private BorderPane main;
    @FXML
    private VBox contentPane;
    @FXML
    private TabPane chatWindows;

    // Icons
    @FXML
    private FontAwesomeIconView userIcon;
    @FXML
    private FontAwesomeIconView statusIcon;

    // Labels
    @FXML
    private Label username;
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
    private JFXButton groupCreationBtn;

    @FXML
    private JFXComboBox statusBox;

    private JFXButton addContactBtn;

    // Lists
    private VBox contactsList = new VBox();
    private VBox requestsList = new VBox();
    private VBox groupsList = new VBox();
    private Parent createGroupUI;

    // Models
    private volatile ArrayList<User> myfriends;
    private ServerInterface myServer;
    private Client myclient;

    // Controllers
    private HashMap<String, ChatWindowController> chatWindowsControllers = new HashMap<>();
    private CreatGroupController createGroupController;

    public MainUIController(HandleConnection myHandler, User myUser) {
        this.myServer = myHandler.getMyServerAuthInt();
        myclient = Client.getInstance(this, myUser, myServer);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TEST statusBox 
            statusBox.getItems().add("online");
            statusBox.getItems().add("offline");
            statusBox.getItems().add("away");
            statusBox.getItems().add("busy");
            statusBox.getSelectionModel().selectFirst();
            statusBox.setCellFactory(
                    new Callback<ListView<String>, ListCell<String>>() {
                        @Override
                        public ListCell<String> call(ListView<String> param) {
                            final ListCell<String> cell = new ListCell<String>() {
                                @Override
                                public void updateItem(String item,
                                        boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null && empty) {
                                        setGraphic(null);
                                    } else {
                                        Label mytext = new Label(item);
                                        mytext.setStyle("-fx-background-color: #52aafb");
                                        mytext.setAlignment(Pos.CENTER);
                                        mytext.setTextFill(Color.WHITE);
                                        mytext.setPadding(new Insets(2, 2, 2, 2));
                                        setGraphic(mytext);
                                    }
                                }
                            };
                            return cell;
                        }
                    });
            statusBox.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    new Thread() {

                        @Override
                        public void run() {
                            statusChanged();
                        }
                    }.start();
                }
            });

            myServer.addClient(myclient);
            // Initialize Friends list
            myfriends = myServer.getContactList(myclient.getUser().getEmail());

            // Start a thread that refreshes contact list in background 
            Thread contactsRefresher = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            myfriends = myServer.getContactList(myclient.getUser().getEmail());
                            Thread.sleep(3000);
                        } catch (RemoteException ex) {
                            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            contactsRefresher.start();

            //main.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
            username.setText(myclient.getUser().getUserName());
            statusIcon.setFill(Color.GREEN);

            // Initialize Contact list 
            contactsList.setSpacing(5);
            contactsList.setAlignment(Pos.CENTER);
            //Add contact button
            addContactBtn = new JFXButton("Add");
            FontIcon addFriend = new FontIcon("mdi-account-plus");
            addFriend.setIconSize(30);
            addContactBtn.setGraphic(addFriend);
            addContactBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showAddContactDialog();
                }
            });
            contactsList.getChildren().add(addContactBtn);
            // Initialize contacts
            myfriends.forEach((friend) -> {
                contactsList.getChildren().add(getNewContact(friend));
            });

            //Initialize Requests 
            // Set content pane as contact list
            contentPane.getChildren().setAll(contactsList);
            createGroupUI = initCreateGroup();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<User> getMyfriends() {
        return myfriends;
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
        User myuser = new User();
        myuser.setEmail(sender_email);
        myuser.setUserName(sender_name);
        requestsList.getChildren().add(getNewRequest(myuser));
        requestsBtn.fire();
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
            // RELOAD FRIENDS AND GOTO IT
        } catch (RemoteException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printToChatWindow(String sender_email, Message message) {
        ChatWindowController mycontroller = chatWindowsControllers.get(sender_email);
        if (mycontroller == null) {
            User myuser = myfriends.stream().filter((user) -> {
                return user.getEmail().equals(sender_email);
            }).findFirst().get();
            mycontroller = openChatWindow(sender_email, myuser.getUserName());
        }

        mycontroller.receiveFromOne(sender_email, message);
    }

    public ChatWindowController openChatWindow(String friendMail, String userName) {
        ChatWindowController myController = chatWindowsControllers.get(friendMail);

        // ChatWindow needs to be created
        if (myController == null) {
            try {

                // Load new Chat Window with its controller
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chatWindow.fxml"));

                // Properly call controller with needed objs
                User myuser = myfriends.stream().filter((user) -> {
                    return user.getEmail().equals(friendMail);
                }).findFirst().get();
                myController = new ChatWindowController(myuser);
                // Add controller to hashmap
                chatWindowsControllers.put(friendMail, myController);

                fxmlLoader.setController(myController);
                Parent node = fxmlLoader.load();

                // create a new tab and add it to the window
                Tab mytab = new Tab(userName, node);
                mytab.setId(friendMail);

                // Add the new tab to the tab pane
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        chatWindows.getTabs().add(mytab);
                    }
                });

            } catch (IOException ex) {
                Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return myController;
    }

    public void logoutConfirmation() {
        Alert myalert = new Alert(Alert.AlertType.CONFIRMATION);
        myalert.setTitle("Signout confirmation");
        myalert.setContentText("Are you sure you want to log out?");
        Optional result = myalert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                myServer.signOut(myclient);
            } catch (RemoteException ex) {
                Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                System.exit(0);
            }
        }

    }

    public void removeRequestFromPending(Parent node) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                requestsList.getChildren().remove(node);
            }
        });
    }

    public void minimize() {
        Stage mystage = (Stage) main.getScene().getWindow();
        mystage.setIconified(true);

    }

    public boolean checkEmail(String Email) {
        String Email_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern ipPatern = Pattern.compile(Email_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(Email);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }

    public void showAddContactDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add friend");
        dialog.setHeaderText("Add new contact");
        dialog.setContentText("Please enter an email:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String email = result.get().trim();
            if (checkEmail(email)) {
                try {
                    int requestCode = myServer.sendFriendshipRequest(myclient.getUser().getEmail(), result.get());

                    // USER ALREADY EXIST
                    if (requestCode == 1) {
                        Alert myAlert = new Alert(Alert.AlertType.ERROR);
                        myAlert.setTitle("ERROR!");
                        myAlert.setHeaderText("Error in adding a friend");
                        myAlert.setContentText("User does not exist!");
                        myAlert.showAndWait();
                    } else if (requestCode == 2) {
                        Alert myAlert = new Alert(Alert.AlertType.ERROR);
                        myAlert.setTitle("ERROR!");
                        myAlert.setHeaderText("Error in adding a friend");
                        myAlert.setContentText("User is already in your friends list!");
                        myAlert.showAndWait();
                    } else if (requestCode == 3) {
                        Alert myAlert = new Alert(Alert.AlertType.ERROR);
                        myAlert.setTitle("ERROR!");
                        myAlert.setHeaderText("Error in adding a friend");
                        myAlert.setContentText("A previous request was sent to this user");
                        myAlert.showAndWait();
                    } else if (requestCode == 4) {
                        Alert myAlert = new Alert(Alert.AlertType.ERROR);
                        myAlert.setTitle("ERROR!");
                        myAlert.setHeaderText("Error in adding a friend");
                        myAlert.setContentText("FATAL ERROR OCCURED! PLEASE TRY AGAIN LATER.");
                        myAlert.showAndWait();
                    } else if (requestCode == 5) {
                        Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                        myAlert.setTitle("SUCCESS");
                        myAlert.setHeaderText("SUCCESSFUL!");
                        myAlert.setContentText("Successfully sent a friendship request!");
                        myAlert.showAndWait();
                    }
                } catch (RemoteException ex) {
                    Alert myAlert = new Alert(Alert.AlertType.ERROR);
                    myAlert.setTitle("ERROR!");
                    myAlert.setHeaderText("Error in adding a friend");
                    myAlert.setContentText("FATAL ERROR OCCURED! PLEASE TRY AGAIN LATER.");
                    myAlert.showAndWait();
                }
            } else {
                Alert myAlert = new Alert(Alert.AlertType.ERROR);
                myAlert.setTitle("ERROR!");
                myAlert.setHeaderText("EMAIL FORMAT IS NOT VALID!");
                myAlert.setContentText("Please retry with a valid email!");
                myAlert.showAndWait();
            }

        }
    }

    public Parent getNewContact(User myUser) {
        Parent node = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contact.fxml"));
            CustomContact contact = new CustomContact(myUser, this);
            fxmlLoader.setController(contact);
            node = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return node;
    }

    public Parent getNewRequest(User myUser) {
        Parent node = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/request.fxml"));
            CustomRequest request = new CustomRequest(myUser, this);
            fxmlLoader.setController(request);
            node = fxmlLoader.load();
            request.setMyview(node);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return node;
    }

    public void setContactsAsContent() {
        contactsList.getChildren().setAll(addContactBtn);
        myfriends.forEach((friend) -> {
            contactsList.getChildren().add(getNewContact(friend));
        });
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                contentPane.getChildren().setAll(contactsList);

            }
        });

    }

    public void setRequestsAsContent() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                contentPane.getChildren().setAll(requestsList);
            }
        });

    }

    public void setGroupsAsContent() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                contentPane.getChildren().setAll(groupsList);
            }
        });
    }

    public Parent initCreateGroup() {

        Parent node = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreatGroup.fxml"));
            createGroupController = new CreatGroupController(myServer, myfriends, this);
            fxmlLoader.setController(createGroupController);
            node = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return node;
    }

    public void openCreateGroup() {
        createGroupController.notifyChange();
    }

    public void statusChanged() {
        try {
            // (0) offline <br> (1) Online <br> (2) Away </b> (3) Busy
            String statusStr = statusBox.getValue().toString();
            int statusNum = 0;
            if (statusStr.equals("offline")) {
                statusNum = 0;
                statusIcon.setFill(Color.GREY);
            } else if (statusStr.equals("online")) {
                statusNum = 1;
                statusIcon.setFill(Color.GREEN);
            } else if (statusStr.equals("away")) {
                statusNum = 2;
                statusIcon.setFill(Color.YELLOW);
            } else if (statusStr.equals("busy")) {
                statusNum = 3;
                statusIcon.setFill(Color.RED);
            }
            myServer.notifyStatus(myclient.getUser().getEmail(), statusNum);
        } catch (RemoteException ex) {
            Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void friendStatusChanged(User friend, int status) {
        //(0) offline <br> (1) Online <br> (2) Away </b> (3) Busy
        String tempStrStatus = null;
        if (status == 0) {
            tempStrStatus = "offline";
        } else if (status == 1) {
            tempStrStatus = "online";
        } else if (status == 2) {
            tempStrStatus = "away";
        } else if (status == 3) {
            tempStrStatus = "busy";
        }
        final String strStatus = tempStrStatus;
        // Show status notification
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Notifications.create()
                        .title("Friendship Request!")
                        .text(friend.getUserName() + " Status changed to " + strStatus)
                        .darkStyle()
                        .showInformation();
            }
        });

        // TODO if chat window is opend if so then pass to it the new status
    }
}
