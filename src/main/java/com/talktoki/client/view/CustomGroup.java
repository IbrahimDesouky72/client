/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.view;

import com.talktoki.client.controller.MainUIController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Mohamed Mahrou
 */
public class CustomGroup implements Initializable {

    @FXML
    private Label groupName;
    @FXML
    private Label dateCreated;

    private MainUIController mainControlller;
    private String group_id;

    public CustomGroup(String group_id, MainUIController mainControlller) {
        this.mainControlller = mainControlller;
        this.group_id = group_id;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupName.setText(group_id.split("\\$")[0]);
        dateCreated.setText("Date created : " + group_id.split("\\$")[1].split("EET")[0]);
        Parent root = dateCreated.getParent();
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                mainControlller.openGroupChatWindow(group_id);
            }
        });
    }

}
