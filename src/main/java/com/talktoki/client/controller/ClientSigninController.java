package com.talktoki.client.controller;

import com.talktoki.client.model.HandleConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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

    public ClientSigninController(HandleConnection h) {
        handle = h;
    }
    //Regular Expression of username Address
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
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

        if (checkUserName(user.getText())) {
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
        if (true && passwordFlagCheck) {
            boolean resultSignIn = handle.signin(user.getText(), pass.getText());
            System.out.println("login success ");
            Stage stage = (Stage) user.getScene().getWindow();
            if (resultSignIn) { try {
                System.out.println("load gui");
                // Create an FXML Loader
                FXMLLoader myloader = new FXMLLoader(getClass().getResource("/fxml/mainUI.fxml"));
                
                //Create new mainUI controller instance 
                MainUIController myMainUIController = new MainUIController();

                // Attach mainUI contorller to the loader
                myloader.setController(myMainUIController);
                
                // Load the FXML file and get root node       
                Parent root = myloader.load();
                root.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

                // Create a scene and attach root node to it
                Scene scene = new Scene(root);
                
                } catch (IOException ex) {
                    Logger.getLogger(ClientSigninController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientSignUp.fxml"));
                ClientSignUpController signin = new ClientSignUpController(handle);
                loader.setController(signin);
                Parent root;
                try {
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ClientSigninController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public boolean checkUserName(String username) {
        Pattern ipPatern = Pattern.compile(USERNAME_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(username);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }

    public boolean checkpassword(String password) {
        Pattern ipPatern = Pattern.compile(USERNAME_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(password);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
    }
}
