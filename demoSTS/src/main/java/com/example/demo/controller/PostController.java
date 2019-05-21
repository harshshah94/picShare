package com.example.demo.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Friend;
import com.example.demo.pojo.Notification;
import com.example.demo.pojo.Post;
import com.example.demo.pojo.User;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UploadToS3Service;

@Controller
public class PostController {
	
	@Autowired
	private UploadToS3Service uploadToS3;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private NotificationRepository notificationRepo;
	
	@GetMapping(value = "/createPost")
	public ModelAndView createPost(HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		
		modelViewPage.setViewName("createPost");
		return modelViewPage;
	}
	
	@GetMapping(value = "/viewUserPosts")
	public ModelAndView viewUserPosts(
			@RequestParam(name = "userId") String userId,
			HttpServletRequest req) {
		ModelAndView modelViewPage = new ModelAndView();
		String currentUserId = (String)req.getSession().getAttribute("userId");
		//String name = (String)req.getSession().getAttribute("userName");
		User u = new User();	
		u = userRepo.findByUserId(userId);
		
		System.out.println("here userid in viewUser Posts "+userId);
		
		List<Post> posts =  postRepo.findByUser(u);
		
		modelViewPage.addObject("userId",userId);
		modelViewPage.addObject("posts",posts);
		
		
		modelViewPage.setViewName("viewUserPosts");
		return modelViewPage;
	}

	@GetMapping(value = "/viewUserPosts1")
	public ModelAndView viewUserPosts1(
			HttpServletRequest req) {
		
		Map<String, ?> inputFlashMap = RequestContextUtils
	            .getInputFlashMap(req);
		
		
		
		ModelAndView modelViewPage = new ModelAndView();
		String currentUserId = (String)req.getSession().getAttribute("userId");
		//String name = (String)req.getSession().getAttribute("userName");
		String userId = (String) inputFlashMap.get("userId");
		User u = new User();	
		u = userRepo.findByUserId(userId);
		
		System.out.println("here userid in viewUser Posts "+userId);
		
		List<Post> posts =  postRepo.findByUser(u);
		
		modelViewPage.addObject("userId",userId);
		modelViewPage.addObject("posts",posts);
		
		
		modelViewPage.setViewName("viewUserPosts");
		return modelViewPage;
	}

	
	@GetMapping(value = "/post")
	public ModelAndView viewPost(
			HttpServletRequest req,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		Map<String, ?> inputFlashMap = RequestContextUtils
	            .getInputFlashMap(req);
		
		ModelAndView viewPost = new ModelAndView();
		Post post = new Post();
		String postId = (String) inputFlashMap.get("postId");
		String notificationId = (String) inputFlashMap.get("notificationId");
		postId = postId.trim();
		System.out.println("notification id in post :"+notificationId);
		System.out.println("string postId:"+ postId);
		post = postRepo.findByPostId(Long.parseLong(postId));
		if(!(notificationId == null) && !notificationId.equals("")) {
			Notification notification = notificationRepo.findByNotificationId(Long.parseLong(notificationId));
			notification.setRead(true);
			notificationRepo.save(notification);
		}
		viewPost.addObject("post",post);
		viewPost.setViewName("viewPost");
		return viewPost;
	}
	
	@PostMapping(value="/deletePost")
	public RedirectView deletePost(
			@RequestParam(name = "postId") String postId, 
			@RequestParam(name = "userId") String userId,
			HttpServletRequest req,
			RedirectAttributes redirectAttrs
			) {
		
		postRepo.delete(Long.parseLong(postId));
		redirectAttrs.addFlashAttribute(userId);
		return new RedirectView("/viewUserPosts1",true);
	}
	
	@PostMapping(value="/savePost")
	public RedirectView savePost(
			@RequestParam(name = "img") String image, 
			@RequestParam(name = "recording") String recording, 
			@RequestParam(name = "formControlTextarea1") String text,
			//@RequestParam(name = "formControlTextarea1") String description,
			HttpServletRequest req
			) throws Exception {
		String userId = (String)req.getSession().getAttribute("userId");
		String name = (String)req.getSession().getAttribute("userName");
				
		// PROCESS IMAGE AND SAVE IMAGE HERE
		Decoder decoder = Base64.getDecoder();
		if(image.isEmpty())
			throw new Exception("image data is null");
		/*if(text.isEmpty())
			throw new Exception("text annotation data is null");*/
		byte[] decodedByte = decoder.decode(image.split(",")[1]);
		FileOutputStream fos = new FileOutputStream("Myimage.png");
		fos.write(decodedByte);
		fos.close();
		String uniqueID0 = UUID.randomUUID().toString();
		uniqueID0 = uniqueID0.replaceAll("-", "_");
		String imageLink = uploadToS3.uploadImageToS3(uniqueID0+".png",new FileInputStream("Myimage.png"));
		
		

		// PROCESS AUDIO/TEXT AND SAVE AUDIO/TEXT HERE
		
		System.out.println("/base64Audio method started here...");
		Decoder decoder1 = Base64.getDecoder();
		if(recording.isEmpty())
			throw new Exception("recording data is null");
		/*if(text.isEmpty())
			throw new Exception("text annotation data is null");*/
		byte[] decodedByte1 = decoder1.decode(recording.split(",")[1]);
		FileOutputStream fos1 = new FileOutputStream("MyAudio.webm");
		fos1.write(decodedByte1);
		fos1.close();
		String uniqueID = UUID.randomUUID().toString();
		uniqueID = uniqueID.replaceAll("-", "_");
		String audioUrl = uploadToS3.uploadRecording(uniqueID+".webm",new FileInputStream("MyAudio.webm"));
		User user = new User();
		user = userRepo.findByUserId(userId);
		System.out.println("******************** FRIENDS OF USER IN save post ************* "+user.getFriends().size());
		Post postObj = new Post(imageLink, "", audioUrl, new ArrayList<Comment>(), user, new Date(), false);
		postRepo.save(postObj);
		Long postId = postRepo.findByPostImageLink(imageLink).getPostId();
		String notificationUrl = name+ " added new post";
		
		List<Friend> list = user.getFriends();
		for (int i=0 ; i<list.size() ; i++) {
			Notification notification = new Notification();
			notification.setNotificationTime(new Date());
			notification.setNotificationUrl(notificationUrl);
			notification.setRead(false);
			notification.setSenderId(userId);
			notification.setPostId(postId);
			String friendId = list.get(i).getFriendId();
			if(userRepo.existsByUserId(friendId)) {
				notification.setRecepientId(list.get(i).getFriendId());
				notificationRepo.save(notification);
			}
			
		}
		
		
		return new RedirectView("/viewProfile",true);
	}
	
	
	@PostMapping(value="/viewPost")
	public RedirectView viewPost(
			@RequestParam(name = "postId") String postId, 
			@RequestParam(name = "notificationId") String notificationId, 
			RedirectAttributes redirectAttrs,
			HttpServletRequest req
			) throws Exception {
		
		System.out.println("notification Id in view post:"+notificationId);
		redirectAttrs.addFlashAttribute("postId",postId);
		redirectAttrs.addFlashAttribute("notificationId",notificationId);
		return new RedirectView("/post", true);
	}
	
	@PostMapping(value="/addComment")
	public RedirectView addComment(
			@RequestParam(name = "postId") String postId, 
			@RequestParam(name = "comment") String commentText,
			RedirectAttributes redirectAttrs,
			HttpServletRequest req
			) throws Exception {
		String userId = (String)req.getSession().getAttribute("userId");
		String name = (String)req.getSession().getAttribute("userName");
		Post post = postRepo.findByPostId(Long.parseLong(postId));
		Comment comment = new Comment(post.getUser().getUserId(),name, userId,commentText, new Date() , new Post(post.getPostId()));
		System.out.println("size of comments before adding comment "+post.getComments().size());
		post.getComments().add(comment);
		System.out.println("size of comments after adding comment "+post.getComments().size());
		post.setComments(post.getComments());
		postRepo.save(post);
		String notificationUrl = userId+" added new comment on"+ postId+"'s post";
		
		Set<String> set = new HashSet<>();
		
		post = postRepo.findByPostId(Long.parseLong(postId));
		List<Comment> list = post.getComments();
		
		System.out.println("comments size in list:"+ list.size());
		set.add(post.getUser().getUserId());
		for (int i=0;i<list.size();i++) {
			System.out.println("user"+i+" in list:"+list.get(i).getSenderId());
			
			set.add(list.get(i).getSenderId());
		}
		
		System.out.println("set size before removing user: "+set.size() );
		System.out.println(set.toString());
		set.remove(comment.getSenderId());
		System.out.println("set size after removing user: "+set.size() );
		System.out.println(set.toString());
			for (String id: set) {
				
				Notification notification = new Notification();
				notification.setNotificationTime(new Date());
				notification.setNotificationUrl(notificationUrl);
				notification.setRead(false);
				notification.setSenderId(userId);
				notification.setPostId(Long.parseLong(postId));
				if(userRepo.existsByUserId(id)) {
					notification.setRecepientId(id);
					notificationRepo.save(notification);
				}
		}
		

		redirectAttrs.addFlashAttribute("postId",postId);
		return new RedirectView("/post", true);
	}


}
