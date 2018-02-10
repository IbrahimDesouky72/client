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
        myController.openOrAppendToSingleChat(sender_email, message);
        return true;
    }

    @Override
    public boolean receiveInGroup(String group_id, String sender_email, Message message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void terminate() throws RemoteException {
        myController.exit();
    }

    @Override
    public User getUser() throws RemoteException {
        return myuser;
    }

    @Override
    public void receiveFriendshipRequest(String sender_name, String sender_email) throws RemoteException {
        boolean accepted = myController.showRequestNotification(sender_name, sender_email);
        myServer.friendshipRequestResponse(myuser.getEmail(), sender_email, accepted);
    }

}
