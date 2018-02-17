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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

    @Override
    public void reciveFile(String filename, byte[] data, int dataLength) {
        try {
            String pathDefault = "C:\\Users\\Public\\Downloads\\";
            File f = new File(pathDefault + filename);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f, true);
            out.write(data, 0, dataLength);
            out.flush();
            out.close();
            System.out.println("Done writing data...");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
