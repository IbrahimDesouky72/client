/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;
import com.jfoenix.controls.JFXCheckBox;
import com.talktoki.chatinterfaces.commans.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author gR
 */
public class UserCheckController implements Initializable {

    @FXML
    private FontAwesomeIconView userIcon;
    @FXML
    private Label userName;
    @FXML
    private  JFXCheckBox UserCheck;
    User myfriend;
    public   UserCheckController(User user)
    {
           myfriend=user;
           
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      userName.setText(myfriend.getUserName());  
    } 
    public boolean CheckUserAdded()
    {
        if(UserCheck.isSelected())
           return true;
        else
           return false;
    }

    public User getMyfriend() {
        return myfriend;
    }
    
    
}
