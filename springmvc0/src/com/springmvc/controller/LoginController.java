package com.springmvc.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.delay.packet.DelayInformation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.component.MyChatManagerListener;
import com.springmvc.util.MyConstants;

// this class encapsulates some info for
// connection, login, creating chat.
@Controller
@Scope("prototype")
@RequestMapping(value = "/login")
public class LoginController {
	private XMPPTCPConnectionConfiguration conf;
	private AbstractXMPPConnection connection;
	private ChatManager chatManager;
	private String username;
	private String password;
	private Chat chat;

	public LoginController() { }
	
	/**
	 * @param args refers to an array with ordered values as follows: user, password, host, port.
	 */
	public void init(String... args) {
		username = args[0];
		password = args[1];
		conf = getConf();
		connection = getConnection();
		chatManager = getChatManager();
	}
	
	// login route.
	@RequestMapping(value = "/loginform", method = RequestMethod.GET)
	public String showLoginForm(HttpSession session) {
		if(session.getAttribute("curuser") != null) {
			return "redict:/chat/chatroom";
		}
		return "login";
	}
	
	// login route for post request.
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {		
		disconnect();
		session.setAttribute("curuser", null);
		setLoginControllerNull(null);
		return "redirect:/login/loginform";
 	}
	
	// login route for post request.
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam String username, 
			@RequestParam String password,
			HttpSession session) {
		System.out.println("executing login with post request method.");
		init(username, password);
		try {
			connectAndLogin();
			session.setAttribute("curuser", username);
			return "redirect:/chat/chatroom";
		} catch (Exception e) {
			e.printStackTrace();
			setLoginControllerNull(null);
			session.setAttribute("curuser", null);
			return "redirect:/login/loginform";
		}  
 	}
	
	/**
	 * connect to and login in openfire server.
	 * @throws XMPPException 
	 * @throws IOException 
	 * @throws SmackException 
	 */
	public void connectAndLogin() throws SmackException, IOException, XMPPException {
		System.out.println("executing connectAndLogin method.");
		System.out.println("connection = " + connection);
		if(!connection.isConnected()) {
			connection.connect();
		}
		System.out.println("successfully connection.");
		connection.login(); // client logins into openfire server.
		System.out.println("successfully login.");
	}
	
	/**
	 * disconnect to and logout from openfire server.
	 * @throws XMPPException 
	 * @throws IOException 
	 * @throws SmackException 
	 */
	public void disconnect() {
		System.out.println("executing disconnect method.");
		try {
			if(connection != null && connection.isConnected()) {
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get chat timestamp, also time recoded when the msg starts to send.
	 * @param msg 
	 * @return timestamp.
	 */
	public String getChatTimestamp(Message msg) {
		ExtensionElement delay = DelayInformation.from(msg);
		
		if(delay == null) {
			return null;
		}
		Date date = ((DelayInformation) delay).getStamp();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return format.format(date);
	}
	
	// a series of getXXX methods.
	public XMPPTCPConnectionConfiguration getConf() {
		if(conf == null) {
			conf = XMPPTCPConnectionConfiguration.builder().setUsernameAndPassword(username, password)
					.setServiceName(MyConstants.HOST)
					.setHost(MyConstants.HOST)
					.setPort(Integer.valueOf(MyConstants.PORT))
					.setSecurityMode(SecurityMode.disabled) // (attention of this line about SSL authentication.)
					.build();
		}
		return conf;
	}

	public AbstractXMPPConnection getConnection() {
		if(connection == null) {
			if(conf == null) {
				conf = getConf();
			}
			connection = new XMPPTCPConnection(conf);
		}
		return connection;
	}

	public ChatManager getChatManager() {
		if(chatManager == null) {
			if(connection == null) {
				connection = getConnection();
			}
			chatManager = ChatManager.getInstanceFor(connection); 
			chatManager.addChatListener(new MyChatManagerListener());
		}
		return chatManager;
	}

	// set all attributes null.
	public void setLoginControllerNull(Object obj) {		
		this.conf = null;
		this.connection = null;
		this.chatManager = null;
		this.username = null;
		this.password = null;
		this.chat = null;
	}
	
	// create a chat instance with toUser as input parameter.
	public Chat getChat(String touser) {
		if(username==null || password==null) {
			return null;
		}
		touser += "@" + MyConstants.HOST;
		chat = getChatManager().createChat(touser);		
		return chat;
	}
}
