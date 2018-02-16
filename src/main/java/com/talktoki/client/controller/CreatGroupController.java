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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author gR
 */

public class CreatGroupController implements Initializable {
       @FXML
    private VBox myFriendListVBox;
       
    private ServerInterface Server ;
    private ArrayList<User> myFriends;
public CreatGroupController(ServerInterface myServer,ArrayList friends)
{
  this.Server=myServer; 
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }
    public Parent getNewContact(User myUser) {
        Parent node = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contact.fxml"));
            //CustomContact contact = new CustomContact(myUser, this);
            //fxmlLoader.setController(contact);
            node = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return node;
    }    
    @FXML
    void closeWindow(ActionEvent event) {
       
    } 
}

