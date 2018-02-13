/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.view;

import com.jfoenix.controls.JFXButton;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.client.controller.MainUIController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 *
 * @author Mohamed Mahrou
 */
public class CustomRequest implements Initializable {

    private final User myUser;
    private MainUIController mainControlller;

    public CustomRequest(User myUser, MainUIController mainControlller) {
        this.myUser = myUser;
        this.mainControlller = mainControlller;
    }

    @FXML
    private FontAwesomeIconView userIcon;
    @FXML
    private Label userName;
    @FXML
    private JFXButton accept;
    @FXML
    private JFXButton refuse;

    private Parent myview;

    public void setUsername(String username) {
        userName.setText(username);
    }

    public void setIconGlyphName(String glyphName) {
        userIcon.setGlyphName(glyphName);
    }

    public void setMyview(Parent myview) {
        this.myview = myview;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUsername(myUser.getUserName());
        setIconGlyphName("USER");
        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainControlller.friendshipRequestResponse(myUser.getEmail(), true);
                mainControlller.removeRequestFromPending(myview);
            }
        });

        refuse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainControlller.friendshipRequestResponse(myUser.getEmail(), false);
                mainControlller.removeRequestFromPending(myview);
            }
        });
    }
}
