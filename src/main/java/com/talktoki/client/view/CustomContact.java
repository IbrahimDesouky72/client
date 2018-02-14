/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.view;

import com.talktoki.chatinterfaces.commans.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author Mohamed Mahrou
 */
public class CustomContact implements Initializable {

    private User myUser;

    public CustomContact(User myuser) {
        this.myUser = myuser;
    }

    @FXML
    private FontAwesomeIconView userIcon;

    @FXML
    private Label userName;

    @FXML
    private FontAwesomeIconView userStatus;

    
    public CustomContact() {

    }

    public void setStatus(Color color) {
        userStatus.setFill(color);
    }

    public void setUsername(String username) {
        userName.setText(username);
    }

    public void setIconGlyphName(String glyphName) {
        userIcon.setGlyphName(glyphName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUsername(myUser.getUserName());
        if (myUser.getStatus().equals("online")) {
            setStatus(Color.GREEN);
        } else // TODO SET COLOR UPON ALL STATES
        {
            setStatus(Color.RED);
        }
        setIconGlyphName("USER");
    }

}
