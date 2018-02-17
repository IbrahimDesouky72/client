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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.FontWeight;
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
    private JFXComboBox<String> fontFamily;

    @FXML
    private JFXComboBox<String> fontSize;

    @FXML
    private JFXColorPicker colorPallet;
    
    @FXML
    private JFXComboBox<FontWeight> fontWeight;
    
    
    Message message = new Message();
    
    String fontSizeValue;
    String fontFamilyValue;
    FontWeight fontWeghtValue;
    Font font;
    Color messageColor;
    String hex1="";
    
    
     ObservableList<String> fontFamilyStrings = FXCollections.observableArrayList("serif","calibri","antiqua","architect","arial","calibri",
     "cursive","courier");
     
     ObservableList<String> fontSizeStrings = FXCollections.observableArrayList("10","12","14","16","18","20","22","24");
     
     ObservableList<FontWeight> fontWeightStrings= FXCollections.observableArrayList(FontWeight.BOLD,
             FontWeight.NORMAL);
    
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
        fontFamily.getItems().addAll(fontFamilyStrings);
        fontFamily.setValue("serif");
        fontSize.getItems().addAll(fontSizeStrings);
        fontSize.setValue("12");
        fontWeight.getItems().addAll(fontWeightStrings);
        fontWeight.setValue(FontWeight.LIGHT);
        fontSizeValue=fontSize.getValue();
        fontFamilyValue=fontFamily.getValue();
        fontWeghtValue=fontWeight.getValue();
         font=Font.font(fontFamilyValue, fontWeghtValue, Double.parseDouble(fontSizeValue));
         colorPallet.setValue(Color.BLACK);
         messageColor=colorPallet.getValue();
        message.setFontSize(fontSizeValue);
        message.setFontFamily(fontFamilyValue);
        message.setFontWeight(fontWeghtValue.toString());
        hex1 = Integer.toHexString(messageColor.hashCode()); 
        message.setTextColor("#"+hex1);
        
        fontSize.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fontSizeValue=fontSize.getValue();
                font=Font.font(fontFamilyValue, fontWeghtValue, Double.parseDouble(fontSizeValue));
                messageTextField.setFont(font);
                message.setFontSize(fontSizeValue);
                //messageTextField.setStyle("-fx-text-fill: green");
                System.out.println(font.getSize());
                
            
            }
        
        });
        
        fontFamily.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fontFamilyValue=fontFamily.getValue();
                font=Font.font(fontFamilyValue, fontWeghtValue, Double.parseDouble(fontSizeValue));
                messageTextField.setFont(font);
                message.setFontFamily(fontFamilyValue);
        
                //messageTextField.setStyle("-fx-text-fill: green");
                System.out.println(font.getFamily());
            
            }
        
        });
        
        fontWeight.valueProperty().addListener(new ChangeListener<FontWeight>() {
            @Override
            public void changed(ObservableValue<? extends FontWeight> observable, FontWeight oldValue, FontWeight newValue) {
                fontWeghtValue=fontWeight.getValue();
                font=Font.font(fontFamilyValue, fontWeghtValue, Double.parseDouble(fontSizeValue));
                messageTextField.setFont(font);
                message.setFontWeight(fontWeghtValue.toString());
                //messageTextField.setStyle("-fx-text-fill: green");
                System.out.println(fontWeghtValue.toString());
            }
        } );
        
        colorPallet.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                messageColor=colorPallet.getValue();
                 hex1 = Integer.toHexString(messageColor.hashCode()); 
                System.out.println(hex1);
                messageTextField.setStyle("-fx-text-fill:#"+hex1);
            }
        } );
        
        
    }    

    public ChatWindowController(User otherUser) {
        this.otherUser = otherUser;
    }
    
    public void receiveFromOne(String sender_email,Message message){
        //draw messsage
        Text text=new Text();
        
        
        text.setText(message.getText());
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                
                messageVBox.getChildren().add(text);
            }
        });
        
           
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
                    
            if(!(messageTextField.getText().trim().equals("")||messageTextField==null)){
                message.setText(messageTextField.getText());
               // message.setFont(new XmlFont());
                //message.set
                messages.add(message);
                myserver.sendToOne(myclient.getUser().getEmail(),otherUser.getEmail() , message);
                //draw message
                
                
                
                
                
                Text text=new Text();
                text.setText(message.getText());
                text.setFont(font);
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        
                        
                        messageVBox.getChildren().add(text);
                    }
                });
                 
            }
            

            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
