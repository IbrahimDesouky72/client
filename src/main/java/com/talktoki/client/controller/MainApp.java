package com.talktoki.client.controller;

import com.talktoki.client.controller.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    double xOffset, yOffset;

    @Override
    public void start(Stage stage) throws Exception {

        /* <-------------------------Bodour's code -------------------------> */
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ClientIpPort.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        /* <------------------------- Bodour's code -------------------------> */

 /* <------------------------- Mahrous's code -------------------------> */
        // Create an FXML Loader
        FXMLLoader myloader = new FXMLLoader();

        //Create new mainUI controller instance 
        MainUIController myMainUIController = new MainUIController();

        // Attach mainUI contorller to the loader
        myloader.setController(myMainUIController);

        // Load the FXML file and get root node       
        Parent root = myloader.load(getClass().getResource("/fxml/mainUI.fxml"));
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

        // Create a scene and attach root node to it
        Scene scene = new Scene(root);
        // Remove the default Window decoration 
        scene.setFill(null);

        stage.initStyle(StageStyle.TRANSPARENT);

        // Add listener to move window with mouse press and hold
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        /* <------------------------- Mahrous's code -------------------------> */

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
