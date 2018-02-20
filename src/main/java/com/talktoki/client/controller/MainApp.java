package com.talktoki.client.controller;

import com.talktoki.client.controller.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
    private double xOffset,yOffset;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ClientIpPort.fxml"));
        
        Scene scene = new Scene(root);
                                  //Add listener to move window with mouse press and hold
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
        scene.getStylesheets().add("/styles/style.css");
        stage.setTitle("TalkToki Messenger");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
