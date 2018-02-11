package com.talktoki.client.controller;

import com.talktoki.chatinterfaces.commans.*;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ClientSignUpController implements Initializable {

    HandleConnection handle;
    User user;
    @FXML
    private TextField lnameValue;

    @FXML
    private TextField FnameValue;

    @FXML
    private TextField EmailValue;

    @FXML
    private PasswordField passwordValue;

    /*@FXML
    private ToggleGroup gender;*/
    @FXML
    private RadioButton male;

    @FXML
    private ComboBox<String> Mycombo;

    @FXML
    private Circle fCircle;

    @FXML
    private Circle Lcircle;

    @FXML
    private Circle Ecircle;

    @FXML
    private Circle Pcircle;
    @FXML
    private Label excist;
    private double xOffset,yOffset;

    String userGender = "";
    String Country = "";
    //Regular Expression of username Address
     private static final String Email_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    //Regular Expression of password Address
    //Password expression : Password must be between 4 and 8 digits long and include at least one numeric digit.
    //Matches	1234 | asdf1234 | asp123
    //Non-Matches	asdf | asdf12345 | password
    //this regular rated as 5 star
    private static final String PASSWORD_PATTERN = "^(?=.*\\d).{4,8}$";
    boolean fnameFlagCheck = false;
    boolean lnameFlagCheck = false;
    boolean EmailFlagCheck = false;
    boolean passwordFlagCheck = false;

    public ClientSignUpController(HandleConnection handleConnection) {
        handle = handleConnection;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Mycombo.getItems().addAll("Cairo", "Alexandria", "Tanta", "sharm");
    }

    @FXML
    void signup(ActionEvent event) {
        if (checkUserName(FnameValue.getText())) {
            fnameFlagCheck = true;
            fCircle.setVisible(false);
        } else {
            fnameFlagCheck = false;
            fCircle.setVisible(true);
        }
        //check valud format port >>if false there is a circle red point appear
        if (checkpassword(lnameValue.getText())) {
            lnameFlagCheck = true;
            Ecircle.setVisible(false);
        } else {
            lnameFlagCheck = false;
            Ecircle.setVisible(true);
        }
        if (checkpassword(EmailValue.getText())) {
            lnameFlagCheck = true;
            Ecircle.setVisible(false);
        } else {
            lnameFlagCheck = false;
            Ecircle.setVisible(true);
        }
        if (checkpassword(passwordValue.getText())) {
            lnameFlagCheck = true;
            Ecircle.setVisible(false);
        } else {
            lnameFlagCheck = false;
            Ecircle.setVisible(true);
        }
        //if(fnameFlagCheck&&lnameFlagCheck&&passwordFlagCheck&&EmailFlagCheck)
        // {
        ToggleGroup gender = male.getToggleGroup();
        String gen = "";
        if (gender.getSelectedToggle() != null) {

            RadioButton name = (RadioButton) gender.getSelectedToggle();
            gen = name.getText();
            // TODO CHECK GENDER

        }
        Country = Mycombo.getValue();
        System.out.println("gender : " + userGender + "Country : " + Country);

        user = new User(FnameValue.getText() + lnameValue.getText(), EmailValue.getText(), FnameValue.getText(), lnameValue.getText(), passwordValue.getText(), gen, Country, "offline");
        boolean signUpResult = handle.signUp(user);
        Stage stage = (Stage) lnameValue.getScene().getWindow();
        if (signUpResult) {
            // Create an FXML Loader
            FXMLLoader myloader = new FXMLLoader(getClass().getResource("/fxml/mainUI.fxml"));
            //Create new mainUI controller instance 
            MainUIController myMainUIController = new MainUIController(handle, user);
            // Attach mainUI contorller to the loader
            myloader.setController(myMainUIController);
            // Load the FXML file and get root node       
            Parent root;
            try {
                root = myloader.load();
                //Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainUI.fxml"));
                // Create a scene and attach root node to it
                Scene scene = new Scene(root);
                // Attach css file to the scene
                scene.getStylesheets().add("/styles/mainui.css");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(ClientSigninController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            excist.setText("This Email used");
        }
        // }
    }

    public boolean checkUserName(String username) {
        Pattern ipPatern = Pattern.compile(Email_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(username);
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
    void close(ActionEvent event) {
         Platform.exit();
    }

    @FXML
    void closebutton(MouseEvent event) {
           Platform.exit();
    }

}
