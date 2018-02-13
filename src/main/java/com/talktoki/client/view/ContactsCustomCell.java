/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.view;

import com.talktoki.chatinterfaces.commans.User;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

/**
 *
 * @author Mohamed Mahrou
 */
public class ContactsCustomCell extends ListCell<User> {

    @Override
    protected void updateItem(User myuser, boolean empty) {
        super.updateItem(myuser, empty);
        if (myuser == null || empty) {
            setGraphic(null);
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contact.fxml"));
                CustomContact contact = new CustomContact();
                fxmlLoader.setController(contact);
                Parent node = fxmlLoader.load();
                contact.setUsername(myuser.getUserName());

                if (myuser.getStatus().equals("online")) {
                    contact.setStatus(Color.GREEN);
                } else // TODO SET COLOR UPON ALL STATES
                {
                    contact.setStatus(Color.RED);
                }
                // TODO CHOOSE Icon upon gender
                contact.setIconGlyphName("USER");
                setGraphic(node);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
