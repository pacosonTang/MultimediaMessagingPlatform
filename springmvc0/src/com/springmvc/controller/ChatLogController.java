package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.springmvc.pojo.MessageBody;
import com.springmvc.pojo.MyTextMessage;
import com.springmvc.repo.ChatRepository;

@Controller
public class ChatLogController {
	private ChatRepository repository;
	
	@Autowired
	public ChatLogController(ChatRepository repository) {
		this.repository = repository;
	}
	
	// @MessageMapping defines the sending addr for client.
	// 消息发送地址: /server/app/hello
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public MessageBody greeting(MyTextMessage message) throws Exception {
		System.out.println("connecting successfully.");
		return new MessageBody("Hello, " + message.getText() + "!");
	}
	
	// 无需 stomp client 发送请求，而stomp server 返回接收到的msg
	@SubscribeMapping("/macro")
	public MessageBody handleSubscription(String body) {
		System.out.println("msg.body = " + body);
		MessageBody messageBody = new MessageBody(body);
		return messageBody;
	}
}