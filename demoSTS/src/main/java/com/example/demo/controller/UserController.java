package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.pojo.Friend;
import com.example.demo.pojo.User;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UploadToS3Service;



@Controller
public class UserController {
	
	@Value("#{environment.ACCESS_KEY}")
	String accessKey;
	@Value("#{environment.SECRET_KEY}")
	String secretKey;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private FriendRepository friendRepo;
	
	@Autowired
	private UploadToS3Service uploadToS3;
	
	@GetMapping(value = "/")
	public ModelAndView renderPage() {
		ModelAndView indexPage = new ModelAndView();

		indexPage.setViewName("index");
		return indexPage;
	}
	
	
	@GetMapping(value = "/friendsList")
	public ModelAndView getFriends(HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		User user = new User();
		String userId = (String)req.getSession().getAttribute("userId");
		List<User> friendList = new ArrayList<>();
		user.setUserId(userId);
		List<Friend> friends = new ArrayList<>();
		friends = friendRepo.findByUser(user);
		for(int i=0;i<friends.size();i++) {						
			User usr = userRepo.findByUserId(friends.get(i).getFriendId());
			if(usr != null) {
				friendList.add(usr);
			}
		}
		//System.out.println(friendList);
		
		modelViewPage.addObject("friendList",friendList);
		modelViewPage.setViewName("friendsListPage");
		return modelViewPage;
	}

	@GetMapping(value = "/logout")
	public RedirectView logOut(HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		
		req.getSession().invalidate();
		
		
		return new RedirectView("/",true);
	}
	
	@PostMapping(value = "/facebookRedirect")
	public RedirectView handleRedirect(
			@RequestParam(name = "myId") String myId,
			@RequestParam(name = "myName") String myName,
			@RequestParam(name = "myFriends") String jsonString,
			HttpServletRequest req,
			RedirectAttributes redirectAttrs) {
			
		System.out.println("handle request method started ");
		
			myId = myId.trim();
			User user = new User();
			user.setUserId(myId);
			user.setName(myName);
			
					
			req.getSession().setAttribute("userId", myId);
			req.getSession().setAttribute("userName", myName);
			
			if(myId.equals("1628510370558579")) {
				return new RedirectView("/adminPage",true);
			}
			
			List<Friend> friends = new ArrayList<>();
			
			System.out.println("****************JSON DATA*********************");
			System.out.println(jsonString);
			
			try {
				
				
				JSONArray jsonFriendsArray = new JSONArray(jsonString);
				for (int i = 0; i < jsonFriendsArray.length(); i++) {
					JSONObject jsonF = jsonFriendsArray.getJSONObject(i);
					String friendId = jsonF.getString("id"); 
					friends.add(new Friend(user, friendId));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			user.setFriends(friends);
			
			if(userRepo.findByUserId(myId) != null) {
				System.out.println("***************VIEW PROFILE CONDITION************"+user.toString());
				return new RedirectView("/viewProfile",true);
			}else {
				redirectAttrs.addFlashAttribute(user);
				userRepo.save(user);
				return new RedirectView("/createProfile",true);
				
			}			
	}	

}
