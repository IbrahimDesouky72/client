package com.talktoki.client.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author IbrahimDesouky
 */
public class MessageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private HBox hboxId;

    @FXML
    private FontAwesomeIconView userIcon;

    @FXML
    private TextFlow textId;
    
    TextFlow textFlow;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textId=textFlow;
    }

    public void setMessageController(TextFlow textId1) {
        textFlow=textId1 ;
        
    }


      
    
}
