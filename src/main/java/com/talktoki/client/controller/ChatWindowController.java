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
import com.talktoki.chatinterfaces.commans.XmlFont;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.model.Client;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mahrous
 */
public class ChatWindowController implements Initializable {
    private Client myclient ;
    private ServerInterface myserver;
    private User otherUser;
    ArrayList<Message>messages;
    
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
    
    @FXML
    private JFXComboBox<?> fontType;
    
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        myclient = Client.getInstance();
        myserver = myclient.getMyServer();
        messages=new ArrayList<Message>();
    }    

    public ChatWindowController(User otherUser) {
        this.otherUser = otherUser;
    }
    
    public void receiveFromOne(String sender_email,Message message){
        //draw messsage
        Text text=new Text();
        text.setText(message.getText());
        messageVBox.getChildren().add(text);
           
    }
    
    @FXML
    void attachFile(MouseEvent event) {
        
    }

    @FXML
    void saveMessages(ActionEvent event) {

    }

    @FXML
    void sendMessage(MouseEvent event) {

         try {
            //
            Message msg = new Message();
            
            if(!(messageTextField.getText().trim().equals("")||messageTextField==null)){
                msg.setText(messageTextField.getText());
                XmlFont xmlFont=new XmlFont();
                //fontFamily.getSelectionModel().getSelectedItem().toString()
                xmlFont.setFontFamily("hell");
                //fontSize.getSelectionModel().getSelectedItem().toString()
                xmlFont.setFontSize("d");
                //fontType.getSelectionModel().getSelectedItem().toString()
                xmlFont.setFontType("ff");
                msg.setFont(xmlFont);
//                String hex1 = Integer.toHexString(colorPallet.getValue().hashCode());
//                msg.setTextColor(hex1);
                messages.add(msg);
                myserver.sendToOne(myclient.getUser().getEmail(),otherUser.getEmail() , msg);
                //draw message
                Text text=new Text();
                text.setText(msg.getText());
                 messageVBox.getChildren().add(text);
            }
            

            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
