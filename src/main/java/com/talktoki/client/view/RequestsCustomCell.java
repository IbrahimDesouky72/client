/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.view;

import com.talktoki.chatinterfaces.commans.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

/**
 *
 * @author Mohamed Mahrou
 */
public class RequestsCustomCell extends ListCell<User> {

    @Override
    protected void updateItem(User myuser, boolean empty) {
        super.updateItem(myuser, empty);
        if (myuser == null || empty) {
            setGraphic(null);
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/request.fxml"));
                CustomRequest request = new CustomRequest();
                fxmlLoader.setController(request);
                Parent node = fxmlLoader.load();
                request.setUsername(myuser.getUserName());
                request.setIconGlyphName("USER");
                setGraphic(node);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void updateSelected(boolean selected) {
    }

}
