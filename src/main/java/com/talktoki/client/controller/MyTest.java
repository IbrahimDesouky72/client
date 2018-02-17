/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.server.ServerInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author gR
 */
public class MyTest extends Application {
     private ServerInterface Server ;
    private ArrayList<User> myFriends=new ArrayList<>();
    private MainUIController ChatController;
    @Override
    public void start(Stage primaryStage) {
         for(int i=0;i<5;i++)
         {
            User u= new User("bodour"+i,"bodour@yahoo.com","1234","female","egypt","offline");
             myFriends.add(u);
         }
            try {
                FXMLLoader GrounpLoder = new FXMLLoader(getClass().getResource("/fxml/CreatGroup.fxml"));
                //CreatGroupController GroupShat=new CreatGroupController(Server, myFriends, ChatController);
                //GrounpLoder.setController(GroupShat);
                Parent root=GrounpLoder.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/mainui.css");
                primaryStage.setTitle("JavaFX and Maven");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MyTest.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
