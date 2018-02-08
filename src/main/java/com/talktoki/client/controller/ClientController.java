/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author future
 */
public class ClientController implements Initializable {
  @FXML
    private TextField PortNum;

    @FXML
    private TextField IpAdd;
    Registry ConnectReg;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
      @FXML
    void Connect(ActionEvent event) {
      try {
          ConnectReg=LocateRegistry.getRegistry(IpAdd.getText(),Integer.parseInt(PortNum.getText()));
          
      } catch (RemoteException ex) {
          Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
}
