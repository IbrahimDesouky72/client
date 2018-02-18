package com.talktoki.client.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.client.model.HandleConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientSigninController implements Initializable {

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private Label refuseCheck;

    @FXML
    private FontAwesomeIconView userCircle;

    @FXML
    private FontAwesomeIconView passcircle;

    HandleConnection handle;

    private double xOffset, yOffset;

    public ClientSigninController(HandleConnection h) {
        handle = h;
    }
    //Regular Expression of username Address
    private static final String Email_PATTERN = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    //Regular Expression of password Address
    //Password expression : Password must be between 4 and 8 digits long and include at least one numeric digit.
    //Matches	1234 | asdf1234 | asp123
    //Non-Matches	asdf | asdf12345 | password
    //this regular rated as 5 star
    //private static final String PASSWORD_PATTERN = "^(?=.*\\d).{4,8}$";
    boolean usernameFlagCheck = false;
    boolean passwordFlagCheck = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void login(ActionEvent event) {
       refuseCheck.setText(" ");
        if (checkEmail(user.getText())) {
            usernameFlagCheck = true;
            userCircle.setVisible(false);
        } else {
            usernameFlagCheck = false;
            userCircle.setVisible(true);
        }
        if(pass.getText().trim().isEmpty())
        {
          passwordFlagCheck = false;
            passcircle.setVisible(true);
        }
        else
        {
           passwordFlagCheck = true;
            passcircle.setVisible(false);
        }
            
        //check valud format port >>if false there is a circle red point appear
        /*if (checkpassword(pass.getText())) {
            passwordFlagCheck = true;
            passcircle.setVisible(false);
        } else {
            passwordFlagCheck = false;
            passcircle.setVisible(true);
        }*/
        if (usernameFlagCheck && passwordFlagCheck) {

            User resultSignIn = handle.signin(user.getText(), pass.getText());
            Stage stage = (Stage) user.getScene().getWindow();
            if (resultSignIn != null) {
                refuseCheck.setText(" ");
                 System.out.println("login success ");
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

                refuseCheck.setText("Email OR Password is incorrect");

            }
        }
    }

    public boolean checkEmail(String Email) {
        Pattern ipPatern = Pattern.compile(Email_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(Email);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }

    /*public boolean checkpassword(String password) {
        Pattern ipPatern = Pattern.compile(PASSWORD_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(password);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }*/
      @FXML
    void closeButton(MouseEvent event) {
       Platform.exit();
    }

    @FXML
    void signUp(ActionEvent event) {
        Stage stage = (Stage) user.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientSignUp.fxml"));
        ClientSignUpController signin = new ClientSignUpController(handle);
        loader.setController(signin);
        Parent root;
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
}
