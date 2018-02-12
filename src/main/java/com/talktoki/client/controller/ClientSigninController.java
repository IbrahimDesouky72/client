package com.talktoki.client.controller;

import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.client.model.HandleConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientSigninController implements Initializable {

    @FXML
    private PasswordField pass;

    @FXML
    private TextField user;

    @FXML
    private Circle passcircle;

    @FXML
    private Circle userCircle;
    HandleConnection handle;

    private double xOffset, yOffset;

    public ClientSigninController(HandleConnection h) {
        handle = h;
    }
    //Regular Expression of username Address
    private static final String Email_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    //Regular Expression of password Address
    //Password expression : Password must be between 4 and 8 digits long and include at least one numeric digit.
    //Matches	1234 | asdf1234 | asp123
    //Non-Matches	asdf | asdf12345 | password
    //this regular rated as 5 star
    private static final String PASSWORD_PATTERN = "^(?=.*\\d).{4,8}$";
    boolean usernameFlagCheck = false;
    boolean passwordFlagCheck = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void login(ActionEvent event) {

        if (checkEmail(user.getText())) {
            usernameFlagCheck = true;
            userCircle.setVisible(false);
        } else {
            usernameFlagCheck = false;
            userCircle.setVisible(true);
        }
        //check valud format port >>if false there is a circle red point appear
        if (checkpassword(pass.getText())) {
            passwordFlagCheck = true;
            passcircle.setVisible(false);
        } else {
            passwordFlagCheck = false;
            passcircle.setVisible(true);
        }
        //   if (usernameFlagCheck && passwordFlagCheck) {

        User resultSignIn = handle.signin(user.getText(), pass.getText());
        System.out.println("login success ");
        Stage stage = (Stage) user.getScene().getWindow();
        if (resultSignIn != null) {
            try {

                FXMLLoader myloader = new FXMLLoader(getClass().getResource("/fxml/mainUI.fxml"));

                //Create new mainUI controller instance 
                MainUIController myMainUIController = new MainUIController(handle, resultSignIn);

                // Attach mainUI contorller to the loader
                myloader.setController(myMainUIController);

                // Load the FXML file and getx   root node       
                Parent root = myloader.load();

                // Create a scene and attach root node to it
                Scene scene = new Scene(root);

                // Remove the default Window decoration 
                scene.setFill(null);

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

                stage.setScene(scene);

                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(ClientSigninController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("heloooo");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientSignUp.fxml"));
            ClientSignUpController signin = new ClientSignUpController(handle);
            loader.setController(signin);
            Parent root;
            System.out.println("heloooo");
            try {
                root = loader.load();
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
                Logger.getLogger(ClientSigninController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //}

    }

    public boolean checkEmail(String Email) {
        Pattern ipPatern = Pattern.compile(Email_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(Email);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }

    public boolean checkpassword(String password) {
        Pattern ipPatern = Pattern.compile(PASSWORD_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(password);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }
     @FXML
    void closeButton(ActionEvent event) {
         Platform.exit();
    }

    @FXML
    void closeicon(MouseEvent event) {
       Platform.exit();
    }
}
