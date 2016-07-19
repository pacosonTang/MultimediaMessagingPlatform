package com.springmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.pojo.JsonChat;
import com.springmvc.pojo.MyTextMessage;
import com.springmvc.repo.ChatRepository;
import com.springmvc.util.MyConstants;

@Controller
@Scope("prototype")
@RequestMapping(value = "/chat")
public class ChatController {

	private static final String MAX_LONG_AS_STRING = "9223372036854775807";
	private ChatRepository repository;
	@Autowired 	private LoginController loginController;
	private Chat chat;	
	
	@Autowired 
	public ChatController(ChatRepository repository) {
		this.repository = repository;
	}
	
	// access to chat room.
	@RequestMapping(value="/chatroom", method=RequestMethod.GET)
	public String showChatroom() {
		return "chatroom";
	}
	
	// ajax requests for user list (by json format).
	@RequestMapping(value="/userlist", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String checkoutUserlist(HttpSession session) {
		List<String> list = repository.checkoutUserlist();
		String from = (String) session.getAttribute("curuser");
		String[][] array = new String[2][list.size()];		
		list.toArray(array[1]);		 		
		
		int i = 0;
		for (String to : list) {			
			array[0][i++] = String.valueOf(isUserAvaible(from,to)); 
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json_result = mapper.writeValueAsString(array);
			System.out.println(json_result);
			return json_result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * judge whether the user identified with to is available or not.
	 * @param from the sender's jid.
	 * @param to the receiver's jid.
	 * @return 1 as true, 0 as false.
	 */
	public int isUserAvaible(String from, String to) {
		String urlName = MyConstants.buildPresenceURL(from, to, "text"); 

		try {
			URL url = new URL(urlName);
			URLConnection connection = url.openConnection();
			
			connection.connect();
			Scanner in = new Scanner(connection.getInputStream());
			String isAvailable = in.nextLine(); 
			// System.out.println(isAvailable.equals("available"));
			return isAvailable.equals("available") ? 1 : 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// create single chat.
	@RequestMapping(value = "/single", method=RequestMethod.GET)
	public String createSingleChat(
			@RequestParam String touser,
			HttpSession session) {
		System.out.println("executing createSingleChat " + touser);
		
		chat = loginController.getChat(touser);
		if(chat == null) {
			return "redirect:/login/loginform";
		}
		return "chatroom";
	}
	
	// ajax requests for processing chat with text sended (by json format).
	@RequestMapping(value="/sendtext", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody String sendtext(
			@RequestBody String sendtext, 
			HttpSession session) {
		System.out.println("executing sendtext method with parameter " + sendtext);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonChat bean = mapper.readValue(sendtext, JsonChat.class); // jsonStr2JavaObj
			if(chat == null) {
				String touser = (String)session.getAttribute("touser");
				createSingleChat(touser, session);
			}
			chat.sendMessage(bean.getSendtext());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String json_result;
		try {
			json_result = mapper.writeValueAsString("success");
			return json_result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@MessageMapping("/sendtext")
	@SendToUser("/queue/textmsg") // 发送msg 到 特定用户.
	public MyTextMessage sendtext(MyTextMessage message) throws Exception {		
		System.out.println("access sendtext successfully.");
		try {
			chat.sendMessage(message.getText());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return message;
	}
}
