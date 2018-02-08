package com.talktoki.client.controller;

import com.talktoki.client.controller.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        /* <-------------------------Bodour's code -------------------------> */        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ClientIpPort.fxml"));        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        /* <------------------------- Bodour's code -------------------------> */        
        
        /* <------------------------- Mahrous's code -------------------------> */        
//        // Create an FXML Loader
//        FXMLLoader myloader = new FXMLLoader();
//        
//        //Create new mainUI controller instance 
//        MainUIController myMainUIController = new MainUIController();
//        
//        // Attach mainUI contorller to the loader
//        myloader.setController(myMainUIController);
//        
//        // Load the FXML file and get root node       
//        Parent root = myloader.load(getClass().getResource("/fxml/mainUI.fxml"));
//        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainUI.fxml"));
//
//        // Create a scene and attach root node to it
//        Scene scene = new Scene(root);
//        
//        // Attach css file to the scene
//        scene.getStylesheets().add("/styles/mainui.css");                  
        /* <------------------------- Mahrous's code -------------------------> */        
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
       
    }

    public static void main(String[] args) {
        launch(args);
    }

}
