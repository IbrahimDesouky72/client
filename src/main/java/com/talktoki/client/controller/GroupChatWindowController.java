/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.talktoki.chatinterfaces.commans.Message;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mohamed Mahrou
 */
public class GroupChatWindowController implements Initializable{
    private String group_Id;
   @FXML
    private VBox messageVBox;

    @FXML
    private TextField messageTextField;

    @FXML
    private FontAwesomeIconView fileAttachment;

    @FXML
    private FontAwesomeIconView sendMessage;

    @FXML
    private FontAwesomeIconView phoneCall;

    @FXML
    private JFXComboBox<?> fontFamily;

    @FXML
    private JFXComboBox<?> fontSize;

    @FXML
    private JFXColorPicker colorPallet;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TextFlow userName;

    @FXML
    private JFXComboBox<?> fontWeight;

    @FXML
    private Text groupName;


    

    @FXML
    public void attachFile(MouseEvent event) {

    }

    @FXML
    void saveMessages(ActionEvent event) {

    }

    @FXML
    void sendMessage(MouseEvent event) {

    }

    public GroupChatWindowController(String group_Id) {
        this.group_Id = group_Id;
    }
    public void receiveFromGroup(String sender_email,Message message){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }
}
