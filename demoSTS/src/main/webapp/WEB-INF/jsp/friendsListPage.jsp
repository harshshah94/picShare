<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile</title>
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
	  <a class="nav-link" href="/viewProfile">My Profile</a>
      </li>
      <li class="nav-item active">
	  <a class="nav-link" href="/friendsList">Friends<span class="sr-only">(current)</span></a>
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
			
			if (response.status === 'connected') {
				// Logged into your app and Facebook.
				testAPI();
			} else {
				window.location = '/logout';
				// The person is not logged into your app or we are unable to tell.
				document.getElementById('status').innerHTML = 'Please log '
						+ 'into this app.';
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
<c:if test="${empty friendList}">
    <h2><small class="text-muted">You </small>don't <small class="text-muted"> have any</small> facebook friends <small class="text-muted"> registered in </small><i>picShare!!!</i></h2>
</c:if>

<c:if test="${not empty friendList}">
    <table class="table table-dark">
		
		<tbody>
			<c:forEach items="${friendList }" var="friend">
				<tr>
				<c:url value="/friendProfile" var="myURL">
   						<c:param name="friendId" value="${friend.userId}" />
					</c:url>
					<td>
						<a href="${myURL}">
							<img alt="Profile pic" src="${friend.profilePicLink}" class="img-thumbnail" width="150">
						</a>
					</td>
					<td>
						<a href="${myURL }">
						<c:out value="${friend.name}">
						</c:out>
						</a>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
</c:if>

	
</div>
</div>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
</body>
</html>