package com.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jivesoftware.util.Blowfish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.pojo.Spitter;
import com.springmvc.pojo.Spittle;
import com.springmvc.repo.ChatRepository;

@Controller
@RequestMapping(value = "/smacker")
public class SmackerController {

	private static final String MAX_LONG_AS_STRING = "9223372036854775807";
	private ChatRepository repository;
	private int limit = 5;
	private int offset = 0;
	private int pagenum = 0;
	
	@Autowired
	public SmackerController(ChatRepository repository) {
		this.repository = repository;
	}
	
	// login route for post request.
	/*@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login_back(
			@RequestParam String username, 
			@RequestParam String password,
			HttpSession session) {
		
		String pass = repository.findPassByUsername(username);
		if(pass == null) {
			return "login";
		}
		// decrypt password.
		Blowfish fish = new Blowfish("9H0h7m84FNo7X6c");
		String plainPass = fish.decrypt(pass);
		
		if(password.equals(plainPass)) {
			session.setAttribute("cur_user", username);
			return "home";
		} else {
			return "login";
		}
	}*/
	
	// register route for GET request.
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("spitter", new Spitter());
		return "registerForm";
	}
	
	// register route for POST request.
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(
			@RequestPart(name="profilePicture",required=false) MultipartFile profilePicture, 
			@Valid Spitter spitter,
			HttpServletRequest request,
			RedirectAttributes model,
			Errors errors) {
		
		if (errors.hasErrors()) {
			return "registerForm";
		}
		
		String catalina = System.getProperty("catalina.base");
		repository.save(spitter);
		
		// profilePicture.getContentType() == image/jpeg.
		// set the path of storing is the key.		
		String filetype = profilePicture.getContentType().substring(6);
		String headPath= catalina + File.separator + "webapps" + File.separator + 
				"profileHead" + File.separator +  
				spitter.getUsername() + "." + filetype;
		try {
			if(profilePicture.getSize() > 0) {
				profilePicture.transferTo(new File(headPath));
			}
		} catch (IOException e) {			
			return "registerForm";
		}
		model.addFlashAttribute("headPath", headPath);		
		return "redirect:/spitter/" + spitter.getUsername();
	}
	
	// spitter 列表
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String spittles(Model model, HttpSession session) {
		if (pagenum == 0) {
			int sum = repository.getItemSum();
			pagenum = sum % limit == 0 ? sum / limit : sum / limit + 1;
			session.setAttribute("spitter_pagenum", pagenum);
		}
		model.addAttribute("spitters", repository.findSpitters(limit, offset));
		return "spitters";
	}
	
	// spitter paging.
	@RequestMapping(value = "/paging", method = RequestMethod.GET)
	public String spitters(@RequestParam(value = "curpage", defaultValue = "1") int curpage, Model model) {
		offset = (curpage - 1) * limit;
				
		model.addAttribute("mySpitters", repository.findSpitters(limit, offset));
		model.addAttribute("spitter_nextpage", curpage);
		model.addAttribute("curpage", curpage);
		return "paging_spitters";
	}
	
	// request for single user info.
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showSingleSpitter(
			@RequestParam String username, Model model) {		
		model.addAttribute("username", username);
		return "redirect:/spitter/{username}";
	}
	
	@RequestMapping(value = "/transmit", method = RequestMethod.GET)
	public String processRegistration2(RedirectAttributes model) {		
		model.addAttribute("username", "jaychou");
		model.addFlashAttribute("spitter", new Spitter("jaychou", "root", "pacoson", "tang", "test@163.com"));
		
		return "redirect:/spitter/{username}";
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String showSpitterProfile(
			@PathVariable String username, Model model) {
		model.addAttribute("spitter", repository.findByUsername(username));
		return "profile";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String removeSpitter(
			@RequestParam String username, 
			@RequestParam(name="curpage") String curpage) {
		repository.delete(username);
		
		return "redirect:/spitter/paging?curpage=" + curpage;
	}
}
