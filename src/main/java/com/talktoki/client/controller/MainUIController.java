/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author mahrous
 */
public class MainUIController implements Initializable {

    /*
    
    ------ View nodes ------
    
     */
    // Profile pictures of the user
    @FXML
    ImageView profilePic;

    //User name label
    @FXML
    Label username;

    //User status
    @FXML
    Label status;

    // User's contact list
    @FXML
    VBox contacts;

    //User's group chat    
    @FXML
    VBox groups;

    // Tabbed pane where user chat sessions are added
    @FXML
    TabPane tabs;

    /*
    
    ----- Control nodes ------
    
     */
    // Add new contact button
    @FXML
    Button addContactBtn;

    // Add new group chat button
    @FXML
    Button addGroupBtn;

    public MainUIController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /* 
        
    TODO:
        Initialization Methods called in initialize        
     */
}
