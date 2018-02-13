/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.view;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author Mohamed Mahrou
 */
public class CustomRequest {

    @FXML
    private FontAwesomeIconView userIcon;
    @FXML
    private Label userName;
    @FXML
    private FontAwesomeIconView accept;
    @FXML
    private FontAwesomeIconView refuse;

    public void setUsername(String username) {
        userName.setText(username);
    }

    public void setIconGlyphName(String glyphName) {
        userIcon.setGlyphName(glyphName);
    }
}
