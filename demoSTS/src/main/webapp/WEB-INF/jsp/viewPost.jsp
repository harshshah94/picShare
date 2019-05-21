<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Post</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- Image and text -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#"><img src="/logo.jpg" class="img-thumbnail" width="70"  alt="logo"> <span>   picShare</span> </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
    <li class="nav-item">
	  <a class="nav-link" href="/friendsList">My Profile</a>
      </li>
      <li class="nav-item">
	  <a class="nav-link" href="/friendsList">Friends</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/createPost" >Create Post</a></li>
		<li class = "nav-item">
		<a class="nav-link" href="/editProfile" >Edit Profile</a></li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle"  href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Notifications
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <c:forEach var="notification" varStatus="status" items="${user.notifications}" step="1" begin="0">
          <a class="dropdown-item" href="javascript:notifClicked('${notification.postId}','${notification.notificationId }')">${notification.notificationUrl }</a>
   
          </c:forEach>
        </div>
      </li>
      <li class="nav-item">
        <fb:login-button class="fb-login-button" data-max-rows="1"
				data-size="large" data-button-type="continue_with"
				data-show-faces="false" data-auto-logout-link="true"
				data-use-continue-as="false"
				scope="public_profile,email,user_friends"
				onlogin="checkLoginState();">
			</fb:login-button>
      </li>
    </ul>

  </div>
</nav>

	
	<script>
		// This is called with the results from from FB.getLoginStatus().
		function statusChangeCallback(response) {
			console.log('statusChangeCallback');
			console.log(response);
			// The response object is returned with a status field that lets the
			// app know the current login status of the person.
			// Full docs on the response object can be found in the documentation
			// for FB.getLoginStatus().
			if (response.status === 'connected') {
				// Logged into your app and Facebook.
				testAPI();
			} else {
				window.location = '/logout';
				// The person is not logged into your app or we are unable to tell.
				
			}
		}

		// This function is called when someone finishes with the Login
		// Button.  See the onlogin handler attached to it in the sample
		// code below.
		function checkLoginState() {
			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});
		}

		window.fbAsyncInit = function() {
			FB.init({
				appId : '1966869966961992',
				cookie : true, // enable cookies to allow the server to access 
				// the session
				xfbml : true, // parse social plugins on this page
				version : 'v2.8' // use graph api version 2.8
			});

			

			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});

		};

		// Load the SDK asynchronously
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "https://connect.facebook.net/en_US/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));

		// Here we run a very simple test of the Graph API after login is
		// successful.  See statusChangeCallback() for when this call is made.
		function testAPI() {
			console.log('Welcome!  Fetching your information.... ');
			FB.api('/me?fields=id,name',
					function(response) {
								console.log(response);
								$('[name=myId]').val(response.id);
								$('[name=myName]').val(response.name);
								//$('[name=myFriends]').val(response.myFriends);
								document.getElementById('status').innerHTML = 'Thanks for logging in, '
										+ response.name + '!';
								$("#redirectForm").submit();
							});
		}

	</script>
	
	<br />
	<br />
	
	<div class="container">
		<div class="row">
			<div >
				<img src="${post.postImageLink}" alt="Profile photo"
					class="img-fluid border border-primary" width="500"/>
					<audio autoplay>
					<source src="${post.postAudio}" type="audio/webm"> 
					</audio>
			</div>
		</div>
		<c:forEach var="comment" varStatus="status" items= "${post.comments}" step="1" begin="0">
		<div class="row p-3 mb-2 bg-light text-dark">
			   		${comment.userName } : ${comment.commentText}
		</div>
		</c:forEach>
			<div class="row">
				<form action="/addComment" method="POST" >
				  <div class="form-group">
				    <input type="text" class="form-control" name="comment" id="comment" placeholder="Add comment here"/>
				    <button type="submit" class="btn btn-primary">Comment</button>
				    <input type="hidden" name="postId" value="${post.postId }"/>
				  </div>
				  </form>
			</div>
		<div class="row">
		</div>		
	<br />
		</div>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
</body>
</html>