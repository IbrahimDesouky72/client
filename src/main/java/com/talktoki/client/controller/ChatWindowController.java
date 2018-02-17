/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.sun.javafx.css.converters.FontConverter;
import com.talktoki.chatinterfaces.commans.Message;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.commans.XmlFont;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.model.Client;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

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
    TextFlow messageTextFlow;
    
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
    //private FontAwesomeIconView userIcon=new FontAwesomeIconView();
    
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
                message.setTextColor(hex1);
                messageTextField.setStyle("-fx-text-fill:#"+hex1);
            }
        } );
        
        
    }    

    public ChatWindowController(User otherUser) {
        this.otherUser = otherUser;
    }
    
    public void receiveFromOne(String sender_email,Message receivedMessage){
        //draw messsage
        Text text=new Text();
        FontWeight fw=FontWeight.findByName(receivedMessage.getFontWeight());
        Font receivedMessageFont=Font.font(receivedMessage.getFontFamily(), fw, Double.parseDouble(receivedMessage.getFontSize()));
        
        text.setText(receivedMessage.getText());
        text.setFont(receivedMessageFont);
        text.setStyle("-fx-fill:#"+receivedMessage.getTextColor());
        
        FontAwesomeIconView userIcon=new FontAwesomeIconView();
          
        HBox hBox=new HBox();
        
        //Text sentMessage=new Text(message.getText());
        TextFlow textFlow=new TextFlow(text);
        messageTextFlow=textFlow;
       userIcon.setGlyphName("USER");
        userIcon.setSize("25");
        textFlow.setStyle("-fx-background-color:#f4f2e2;-fx-background-radius:20;-fx-padding-right:30px;-fx-padding-top:30px");
        textFlow.setPadding(new Insets(5, 5, 5, 5));
        if(messageVBox.getChildren().size()>0){
           ((HBox) messageVBox.getChildren().get(messageVBox.getChildren().size()-1)).getChildren().get(0).setVisible(false);
            hBox.getChildren().addAll(userIcon,textFlow);
            hBox.setAlignment(Pos.BASELINE_LEFT);
        }else{
            hBox.getChildren().addAll(userIcon,textFlow);
            hBox.setAlignment(Pos.BASELINE_LEFT);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                
                messageVBox.getChildren().addAll(hBox);
            }
        });
        
           
    }
    
    @FXML
    void attachFile(MouseEvent event) {
        
        try {
            FileChooser fileChooser = new FileChooser();
             File file = fileChooser.showOpenDialog(null);
             if(file!=null)
             {
               myserver.SendFile(myclient.getUser().getEmail(),otherUser.getEmail(),file);
             }
             else
             {
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("SendFile Dialog");
               alert.setHeaderText("Confirm on send File");
               alert.setContentText("the file is null\nPlease choose afile!");
               alert.showAndWait();
             }
        } catch (RemoteException ex) {
            System.out.println("Remote Exception");    
        }
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
                messages.add(message);
                myserver.sendToOne(myclient.getUser().getEmail(),otherUser.getEmail() , message);
                //draw message
                
                
                
                
                
                Text text=new Text();
                text.setText(message.getText());
                text.setFont(font);
                
                text.setFill(messageColor);
                System.out.println(message.getTextColor());
                
                
                HBox hBox=new HBox();
        
        //Text sentMessage=new Text(message.getText());
        TextFlow textFlow=new TextFlow(text);
        messageTextFlow=textFlow;
//        Circle picCircle=new Circle();
//        userIcon.setGlyphName("USER");
//        userIcon.setSize("25");
//        picCircle.setId("pcId");
//        picCircle.setRadius(10);
//        picCircle.setStroke(Color.LIGHTCYAN);
        
//        FileInputStream is=new FileInputStream("C:/Users/IbrahimDesouky/Documents/NetBeansProjects/ChatWithFx3/src/Male.png");
//        Image im=new Image(is);
        textFlow.setStyle("-fx-background-color:#005b96;-fx-background-radius:20;-fx-padding-right:30px;-fx-padding-top:30px");
        textFlow.setPadding(new Insets(5, 5, 5, 5));
        //hBox.getChildren().add(im);
       
        
       
        
        hBox.getChildren().addAll(textFlow);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
                //Parent node=getMessageController();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        
                        
                        messageVBox.getChildren().add(hBox);
                    }
                });
                 
            }
            

            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public Parent getMessageController(){
        Parent node = null;
         try {
            
           FXMLLoader loader = new FXMLLoader();
                node = loader.load(getClass().getResource("/fxml/message.fxml").openStream());
                MessageController listController = loader.getController();
            listController.setMessageController(messageTextFlow);
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return node;
        }
        
    }
    
    
    
}
