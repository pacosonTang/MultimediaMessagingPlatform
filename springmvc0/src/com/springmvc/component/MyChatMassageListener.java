package com.springmvc.component;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springmvc.controller.ChatLogController;

public class MyChatMassageListener implements ChatMessageListener{
	private ChatLogController chatLogController;
	
	@Override
	public void processMessage(Chat chat, Message message) {
		//String from = message.getFrom();
		String body = message.getBody();
		//String timestamp = client.getChatTimestamp(message);
		
		chatLogController.handleSubscription(body);
		
		/*if(timestamp != null) {
			System.out.println(timestamp);
		}
		System.out.println(body);*/
	}
}
