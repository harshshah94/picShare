package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.pojo.Post;
import com.example.demo.pojo.User;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UploadToS3Service;




@Controller
public class ProfileController {
	
	@Value("#{environment.ACCESS_KEY}")
	String accessKey;
	@Value("#{environment.SECRET_KEY}")
	String secretKey;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private NotificationRepository notificationRepo;
	
	@Autowired
	private FriendRepository friendRepo;
	
	@Autowired
	private UploadToS3Service uploadToS3;
	
	@Autowired
	private PostRepository postRepo;
	
	
	@GetMapping(value = "/adminPage")
	public ModelAndView viewAdminPage(HttpServletRequest req) {
		
		List<User> allUsers = userRepo.findAll();
		ModelAndView modelViewPage = new ModelAndView();
		
		modelViewPage.addObject("allUsers",allUsers);
		modelViewPage.setViewName("adminPage");
		return modelViewPage;
	}

	
	@GetMapping(value = "/viewProfile")
	public ModelAndView viewProfile(HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		String userId = (String)req.getSession().getAttribute("userId");
		String name = (String)req.getSession().getAttribute("userName");
		User u = new User();	
		u = userRepo.findByUserId(userId);
		
		
		List<Post> posts =  postRepo.findByUser(u);
		u.setNotifications(notificationRepo.findByRecepientId(userId));
		for(int i=0;i<u.getNotifications().size();i++) {
			System.out.println(u.getNotifications().get(i));
		}
		
		
		modelViewPage.addObject("user",u);
		modelViewPage.addObject("posts",posts);
		modelViewPage.setViewName("userProfilePage");
		return modelViewPage;
	}
	@GetMapping(value = "/friendProfile")
	public ModelAndView viewFriendProfile(
			@RequestParam(name = "friendId") String friendId,
			HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		String userId = (String)req.getSession().getAttribute("userId");
		String name = (String)req.getSession().getAttribute("userName");
		User u = new User();	
		u = userRepo.findByUserId(friendId);
		
		List<Post> posts =  postRepo.findByUser(u);
		
		modelViewPage.addObject("user",u);
		modelViewPage.addObject("posts",posts);
		modelViewPage.setViewName("friendProfilePage");
		return modelViewPage;
	}
	
	
	@GetMapping(value = "/createProfile")
	public ModelAndView createProfile(HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		
		modelViewPage.setViewName("createProfilePage");
		return modelViewPage;
	}
	
	@GetMapping(value = "/editProfile")
	public ModelAndView editProfile(HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		String userId = (String)req.getSession().getAttribute("userId");
		String name = (String)req.getSession().getAttribute("userName");
		User u = new User();	
		u = userRepo.findByUserId(userId);
		
		//List<Post> posts =  postRepo.findByUser(u);
		
		modelViewPage.addObject("user",u);
		//modelViewPage.addObject("posts",posts);
		modelViewPage.setViewName("editProfilePage");
		return modelViewPage;
	}
	
	
	@PostMapping(value = "/saveProfile")
	public ModelAndView saveProfile(
			@RequestParam(name = "inputFile1") MultipartFile image, 
			@RequestParam(name = "formControlTextarea1") String description,
			HttpServletRequest req) {
			String userId = (String)req.getSession().getAttribute("userId");
			String name = (String)req.getSession().getAttribute("userName");
			
			String profilePicLink = uploadToS3.uploadImageToS3Image(image);
			
			// HERE, GET EXISTING OBJECT FIRST AND THEN STORE
			User u = new User();
			u = userRepo.findByUserId(userId);
			u.setName(name);
			u.setDescription(description);
			u.setProfilePicLink(profilePicLink);
			userRepo.save(u);
			// AFTER THIS SAVE METHOD, REDIRECT TO THE viewProfile method
			ModelAndView modelViewPage = new ModelAndView();
			modelViewPage.addObject("user",u);
			modelViewPage.setViewName("userProfilePage");
			
			return modelViewPage;
	}
	
}
