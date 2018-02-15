/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.talktoki.chatinterfaces.commans.Message;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.model.Client;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author mahrous
 */
public class ChatWindowController implements Initializable {
    private Client myclient ;
    private ServerInterface myserver;
    private User otherUser;
    
    @FXML
    private VBox messageVBox;

    @FXML
    private TextField messageTextField;

    @FXML
    private FontAwesomeIconView fileAttachment;

    @FXML
    private FontAwesomeIconView sendMessage;

    @FXML
    private Circle profileImage;

    @FXML
    private ImageView onlineImage;

    @FXML
    private FontAwesomeIconView phoneCall;

    @FXML
    private FontAwesomeIconView addGroup;

    @FXML
    private JFXComboBox<?> fontFamily;

    @FXML
    private JFXComboBox<?> fontSize;

    @FXML
    private JFXColorPicker colorPallet;
    
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        myclient = Client.getInstance();
        myserver = myclient.getMyServer();
    }    

    public ChatWindowController(User otherUser) {
        this.otherUser = otherUser;
    }
    
    public void receiveFromOne(String sender_email,Message message){
           
    }

    public void send(){
    
        try {
            //
            Message msg = new Message();
            // FILL MSG OBJ

            myserver.sendToOne(myclient.getUser().getEmail(),otherUser.getEmail() , msg);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
