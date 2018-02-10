package com.talktoki.client.model;

import com.talktoki.chatinterfaces.commans.*;
import com.talktoki.chatinterfaces.server.*;
import com.talktoki.client.controller.ClientSigninController;
import com.talktoki.client.controller.MainUIController;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HandleConnection {

    Registry ConnectReg;
    ServerInterface myServerAuthInt;
    String test = "No";
    boolean userFoundFlag=false;
//class for handle connection to server and send data for server

    public boolean checkRegistry(String IP, int port) {
        try {
            ConnectReg = LocateRegistry.getRegistry(IP, port);
          // myServerAuthInt = (ServerInterface) ConnectReg.lookup("serverInstance");
            if (ConnectReg.equals(null)) {
                // if (myServerAuthInt.equals(null)) {
                return false;
            } else {
                return true;
            }
        } catch (RemoteException ex) {
            return false;
        }
    }

    public boolean signin(String user, String password) {
//       User activeUser  = myServerAuthInt.signIn(user, password); 
     //  userFoundFlag=activeUser.getFirstName().trim().equals("");
        if (userFoundFlag) {
             return false;
        } else {
            
          return true;
        }
    }
    public boolean signUp(User user) { 
        int result = 0;
        try {
            result = myServerAuthInt.signUp(user);
        } catch (RemoteException ex) {
            Logger.getLogger(HandleConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result==2||result==3) {
             return false;
        } else {
            
          return true;
        }
    }
}
