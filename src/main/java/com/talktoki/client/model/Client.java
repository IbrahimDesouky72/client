/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.model;

import com.talktoki.chatinterfaces.client.ClientInterface;
import com.talktoki.chatinterfaces.commans.Message;
import com.talktoki.chatinterfaces.commans.User;
import com.talktoki.chatinterfaces.server.ServerInterface;
import com.talktoki.client.controller.MainUIController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author mahrous
 */
public class Client extends UnicastRemoteObject implements ClientInterface {

    private static Client instance;

    public static Client getInstance(MainUIController mycontroller, User myuser, ServerInterface myserver) {
        if (instance == null) {
            try {
                instance = new Client(mycontroller, myuser, myserver);
            } catch (RemoteException ex) {
                instance = null;
            }
        }
        return instance;
    }

    public static Client getInstance() {
        return instance;
    }

    private final MainUIController myController;
    private User myuser;
    private final ServerInterface myServer;

    private Client(MainUIController myController, User myUser, ServerInterface myserver) throws RemoteException {
        this.myController = myController;
        this.myuser = myUser;
        this.myServer = myserver;
    }

    @Override
    public boolean receiveFromOne(String sender_email, Message message) throws RemoteException {
        myController.printToChatWindow(sender_email, message);
        return true;
    }

    @Override
    public boolean receiveInGroup(String group_id, String sender_email, Message message) throws RemoteException {
        myController.printToGroupChatWindow(group_id, sender_email, message);
        return true;
    }

    @Override
    public void terminate() throws RemoteException {
        myController.exit();
    }

    @Override
    public User getUser() throws RemoteException {
        return myuser;
    }

    public MainUIController getMyController() {
        return myController;
    }

    public ServerInterface getMyServer() {
        return myServer;
    }

    @Override
    public void receiveFriendshipRequest(String sender_name, String sender_email) throws RemoteException {
        myController.showRequestNotification(sender_name, sender_email);
    }

    @Override
    public void notifyFriendStatusChanged(User friend, int status) {
        myController.friendStatusChanged(friend, status);
    }
    /**********Mahrous*********/   
    @Override
    public void receiveServerAnnouncement(String announcement) throws RemoteException{
        myController.appendToAnnouncements(announcement);
    }
    @Override
    public void refreshContacts() throws RemoteException
    {
        myController.updateFriendsList();
        myController.setContactsAsContent();
    }
    @Override
    public void serverExit() throws RemoteException {
    myController.exit();
    }
    /**********Mahrous*********/
    
/************Bodour*////////////
    @Override
    public boolean reciveFile(String Senderusername,String filename, byte[] data, int dataLength,boolean firstSend) throws RemoteException{
        try {
            if(firstSend)
            {
                System.out.println("firsrTimeeeeeeeee");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                     Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("confirm send file");
                alert.setContentText(myuser.getUserName()+" : Do you want recieve file : "+filename+"From : "+Senderusername);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                         try {
                             // ... user chose OK
                             String pathDefault = "C:\\Users\\Public\\Downloads\\";
                             File f = new File(pathDefault + filename);
                             f.createNewFile();
                             FileOutputStream out = new FileOutputStream(f, true);
                             out.write(data, 0, dataLength);
                             out.flush();
                             out.close();
        
                         } catch (IOException ex) {
                             System.out.println("Error in file Write");
                         }
                } else {
                }
                    
                    }
                });
               
            }
            else
            {
                    String pathDefault = "C:\\Users\\Public\\Downloads\\";
                    File f = new File(pathDefault + filename);
                    f.createNewFile();
                    FileOutputStream out = new FileOutputStream(f, true);
                    out.write(data, 0, dataLength);
                    out.flush();
                    out.close();
                    System.out.println("Done writing data...");
               return true;
            }
                
            

        } catch (Exception e) {
           return false;
        }
  return true;
    }
    /************Bodour*////////////

}
