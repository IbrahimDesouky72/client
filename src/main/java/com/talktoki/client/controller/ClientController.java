/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import com.talktoki.client.model.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author future
 */
public class ClientController implements Initializable {

   
    
     @FXML
    private JFXTextField IpAdd;

    @FXML
    private JFXPasswordField PortNum;

     @FXML
    private FontAwesomeIconView IpCheck;

    @FXML
    private FontAwesomeIconView portCheck;

    @FXML
    private Label refuseCheck;
    
    private double xOffset,yOffset;

    //Regular Expression of IP Address
    private static final String IPADDRESS_PATTERN
            = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    //Regular Expression of port Address
    private static final String PORT_PATTERN
            = "[0-9]+";
    boolean ipFlagCheck = false;
    boolean portFlagCheck = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refuseCheck.setText(" ");
    }

    @FXML
    public void Connect(ActionEvent event) {
        refuseCheck.setText(" ");
        //check valud format Ip >>if false there is a circle red point appear
        if (checkIp(IpAdd.getText())) {
            ipFlagCheck = true;
            IpCheck.setVisible(false);
        } else {
            ipFlagCheck = false;
            IpCheck.setVisible(true);
        }
        //check valud format port >>if false there is a circle red point appear
        if (checkPort(PortNum.getText())) {
            portFlagCheck = true;
            portCheck.setVisible(false);
        } else {
            ipFlagCheck = false;
            portCheck.setVisible(true);
        }
        if (ipFlagCheck && portFlagCheck) {
            HandleConnection handleConnection = new HandleConnection();
            boolean checkrResult = handleConnection.checkRegistry(IpAdd.getText(), Integer.parseInt(PortNum.getText()));
            if (!checkrResult) {
                refuseCheck.setTextFill(Paint.valueOf("#f8f5f6"));
                refuseCheck.setText("Coonection Refuse");
            } else {
                refuseCheck.setTextFill(Paint.valueOf("#6ddd0d"));
                refuseCheck.setText("True connection");
                 //Load the sign in Page
                 Stage stage = (Stage) refuseCheck.getScene().getWindow();
                try {
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/ClientSignin.fxml"));
                    ClientSigninController signin=new ClientSigninController(handleConnection);
                    loader.setController(signin);
                    Parent root = loader.load();
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
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Load false");
                }
            }

        }
    }
    @FXML
    void closebuttton(MouseEvent event) {
         Platform.exit();
    }
// This function for check IP input as Right Ip format >>xxxx.xxxx.xxxx.xxxx such 127.0.0.1 
    //Return True if coorect formate, False Others

    public boolean checkIp(String Ip) {
        Pattern ipPatern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(Ip);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }
// This function for check port number as Right port format
    public boolean checkPort(String Port) {
        Pattern ipPatern = Pattern.compile(PORT_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(Port);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }
}
