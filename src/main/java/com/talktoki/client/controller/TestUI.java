/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.talktoki.client.model.HandleConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mohamed Mahrou
 */
public class TestUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/fxml/mainUI.fxml"));
        MainUIController myController = new MainUIController();

        myLoader.setController(myController);
        System.out.println("FOUND IT " + getClass().getResource("."));
        System.out.println("FILE " + getClass().getResource("/fxml/mainUI.fxml"));
        Parent root = myLoader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
