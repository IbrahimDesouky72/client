/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.controller;

import com.talktoki.chatinterfaces.commans.Message;

/**
 *
 * @author Mohamed Mahrou
 */
public class GroupChatWindowController {
    private String group_Id;

    public GroupChatWindowController(String group_Id) {
        this.group_Id = group_Id;
    }
    public void receiveFromGroup(String sender_email,Message message){}
}
