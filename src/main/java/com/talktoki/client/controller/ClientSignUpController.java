package com.talktoki.client.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.talktoki.chatinterfaces.commans.*;
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
    private JFXTextField FnameValue;

    @FXML
    private JFXPasswordField passwordValue;

    @FXML
    private JFXTextField EmailValue;

    @FXML
    private JFXTextField lnameValue;

    @FXML
    private JFXRadioButton male;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton female;
    
     @FXML
    private FontAwesomeIconView fnameCircle;

    @FXML
    private FontAwesomeIconView lastCheck;
    
    @FXML
    private FontAwesomeIconView passcheck;

    @FXML
    private FontAwesomeIconView Emailcheck;

    @FXML
    private JFXComboBox<String> Mycombo;
     @FXML
    private Label excist;

    private double xOffset,yOffset;

    String userGender = "";
    String Country = "";
    //Regular Expression of username Address
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
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
        Mycombo.setValue("Cairo");
    }

    @FXML
    void signup(ActionEvent event) {
        excist.setText(" ");
        if (checkUserName(FnameValue.getText())) {
            fnameFlagCheck = true;
           fnameCircle.setVisible(false);
        } else {
            fnameFlagCheck = false;
            fnameCircle.setVisible(true);
        }
        //check valud format port >>if false there is a circle red point appear
        if (checkUserName(lnameValue.getText())) {
            lnameFlagCheck = true;
           lastCheck.setVisible(false);
        } else {
            lnameFlagCheck = false;
          lastCheck.setVisible(true);
        }
        if (checkEmail(EmailValue.getText())) {
            lnameFlagCheck = true;
            Emailcheck.setVisible(false);
        } else {
            lnameFlagCheck = false;
            Emailcheck.setVisible(true);
        }
        if (checkpassword(passwordValue.getText())) {
            lnameFlagCheck = true;
           passcheck.setVisible(false);
        } else {
            lnameFlagCheck = false;
           passcheck.setVisible(true);
        }
        if(fnameFlagCheck&&lnameFlagCheck&&passwordFlagCheck&&EmailFlagCheck)
        {
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
            excist.setText("sign up success");
                        //Load the sign in Page
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/ClientSignin.fxml"));
                    ClientSigninController signin=new ClientSigninController(handle);
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
                Logger.getLogger(ClientSignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
                   
            
        } else {
           excist.setText("This Email used");
        }
        }
    }

    public boolean checkUserName(String username) {
        Pattern ipPatern = Pattern.compile(USERNAME_PATTERN);
        Matcher resultMatcher = ipPatern.matcher(username);
        boolean resultFlagCheck = resultMatcher.matches();
        return resultFlagCheck;
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
    void closeButton(MouseEvent event) {
           Platform.exit();
    }

}
