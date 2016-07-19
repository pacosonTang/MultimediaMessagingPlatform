package com.springmvc.component;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springmvc.controller.LoginController;

// chat manager listener.
public class MyChatManagerListener implements ChatManagerListener{
	
	private LoginController client;
	private MyChatMassageListener listener;
	
	@Override
	public void chatCreated(Chat chat, boolean createdLocally) {
		if (!createdLocally) {
			chat.addMessageListener(listener);
		}
	}
}