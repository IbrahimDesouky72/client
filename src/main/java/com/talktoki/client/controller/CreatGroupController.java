/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.view.CustomContact;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author gR
 */
public class CreatGroupController implements Initializable {
    @FXML
    private VBox GroupsVBOx;
    private ServerInterface Server;
    private ArrayList<User> myFriends;
    private MainUIController ChatController;
    private ArrayList<UserCheckController> listOfUsers = new ArrayList<>();
    private ArrayList<User> GroupMember = new ArrayList<>();
    private HashMap<String, ArrayList<User>> myHash = new HashMap<>();
    @FXML
    private Label CheckKLabel;

    @FXML
    private TextField GroupName;
    public CreatGroupController(ServerInterface myServer, MainUIController chatController) {
        this.Server = myServer;
        this.ChatController = chatController;
        this.myFriends = ChatController.getMyfriends();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        for (int i = 0; i < myFriends.size(); i++) {
            if (myFriends.get(i) != null) {
                System.out.println("User " + i + "already Exist");
                Parent node = getNode(myFriends.get(i));
                if (node != null) {
                    System.out.println("Add Succees");
                    GroupsVBOx.getChildren().add(node);
                }
            } else {
                System.out.println("Not Access Friend");
            }
        }
    }

    public Parent getNode(User myUser) {
        Parent node = null;
        try {
            FXMLLoader GrounpLoder = new FXMLLoader(getClass().getResource("/fxml/UserCheck.fxml"));
            UserCheckController check = new UserCheckController(myUser);
            listOfUsers.add(check);
            GrounpLoder.setController(check);
            node = GrounpLoder.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return node;
    }

    public void notifyChange() {
        myFriends = ChatController.getMyfriends();
    }

    @FXML
    void AddGroup(ActionEvent event) {
        for (int i = 0; i < listOfUsers.size(); i++) {
            boolean checked = listOfUsers.get(i).CheckUserAdded();
            if (checked) {
                GroupMember.add(listOfUsers.get(i).getMyfriend());
            }
        }
        //Check if group more than 2 person so it’s agroup
        if (GroupMember.size() > 1) {
            try {
                String groupNam = GroupName.getText();
                if (groupNam.trim().equals("")) {
                    //groupNam="My Group";
                    CheckKLabel.setText("Enter Group Name");
                } else {
                    Date currentDate = new Date();
                    System.out.println("Date : " + currentDate.toString());
                    int Result = Server.createGroup(groupNam, GroupMember);
                    if (Result == 1) {
                        CheckKLabel.setText("The Group is Added ");
                    } else if (Result == 2) {
                        CheckKLabel.setText("This Group alreay Exist");
                    } else {
                        CheckKLabel.setText("Fatel Error Tray Again Later");
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(CreatGroupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
           CheckKLabel.setText("Choose More Than 1"); 
        }

    }

}
