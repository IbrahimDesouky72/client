/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author mahrous
 */
public class MainUIController implements Initializable {

    @FXML
    AnchorPane main;

    public MainUIController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        main.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
    }

    /* 
        
    TODO:
        Initialization Methods called in initialize        
     */
}
