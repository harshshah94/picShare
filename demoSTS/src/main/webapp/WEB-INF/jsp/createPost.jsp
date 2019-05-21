<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
  <title>Create Post</title>
	<link href="https://vjs.zencdn.net/6.6.3/video-js.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/videojs-record/2.1.0/css/videojs.record.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/video.js/6.7.2/video.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/RecordRTC/5.4.6/RecordRTC.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/adapterjs/0.15.0/adapter.min.js"></script>
	<script src="https://collab-project.github.io/videojs-record/dist/wavesurfer.min.js"></script>
	<script src="https://collab-project.github.io/videojs-record/dist/wavesurfer.microphone.min.js"></script>
	<script src="https://collab-project.github.io/videojs-record/dist/videojs.wavesurfer.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/videojs-record/2.1.2/videojs.record.min.js"></script>
	  <style>
  /* change player background color */
  #myAudio {
      background-color: #9FD6BA;
  }
  </style>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body>
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
      <li class="nav-item">
	  <a class="nav-link" href="/friendsList">Friends<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active">
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
	<br />
	<div class="container">
	
			<div class="row">
	<form action="/savePost" method="POST" >
		<div class="form-group">
				<!-- <label for="recording">Add Text</label> -->
				<input type="hidden" class="form-control" id="recording" name="recording">
		</div>
		<div class="form-group">
				<!-- <label for="recording">Add Text</label> -->
				<input type="hidden" class="form-control" id="img" name="img">
		</div>
		<div >
			<video id="myImage" class="video-js vjs-default-skin"></video>
		</div>
		<div >
		<audio id="myAudio" class="video-js vjs-default-skin"></audio>
	</div>
	<div class="form-group">
			<label for="formControlTextarea1">Add Text</label>
			<input type="text" class="form-control" id="formControlTextarea1" name="formControlTextarea1" placeholder="text annotation here...">
		</div>
		<button type="submit" id="submitPost" class="btn btn-primary">Post</button>
	</form>
	</div>
	
	</div>
	
	<script>
var player = videojs("myAudio", {
    controls: true,
    width: 600,
    height: 300,
    plugins: {
        wavesurfer: {
            src: "live",
            waveColor: "#36393b",
            progressColor: "black",
            debug: true,
            cursorWidth: 1,
            msDisplayMax: 20,
            hideScrollbar: true
        },
        record: {
            audio: true,
            video: false,
            maxLength: 20,
            debug: true
        }
    }
}, function(){
    // print version information at startup
    videojs.log('Using video.js', videojs.VERSION,
        'with videojs-record', videojs.getPluginVersion('record'),
        '+ videojs-wavesurfer', videojs.getPluginVersion('wavesurfer'),
        'and recordrtc', RecordRTC.version);
});

// error handling
player.on('deviceError', function() {
    console.log('device error:', player.deviceErrorCode);
});

// user clicked the record button and started recording
player.on('startRecord', function() {
    console.log('started recording!');
});

// user completed recording and stream is available
player.on('finishRecord', function() {
    // the blob object contains the recorded data that
    // can be downloaded by the user, stored on server etc.
    console.log('finished recording: ', player.recordedData);
    var reader = new FileReader();
    var base64Data;
    reader.readAsDataURL(player.recordedData);
    reader.onloadend = function() {
    	base64Data = reader.result;
    	console.log(base64Data);
    	$("#recording").val(base64Data);
    }
});

/* $(document).ready(function(){
	$("#saveButton").on("click",function(){
		alert("called");
		$("#submitPost").submit();
	});
}); */
var aplayer = videojs("myImage", {
    controls: true,
    width: 320,
    height: 240,
    fluid: false,
    controlBar: {
        volumePanel: false,
        fullscreenToggle: false
    },
    plugins: {
        record: {
            image: true,
            debug: true
        }
    }
}, function(){
    // print version information at startup
    videojs.log('Using video.js', videojs.VERSION,
        'with videojs-record', videojs.getPluginVersion('record'));
});

// error handling
aplayer.on('deviceError', function() {
    console.warn('device error:', aplayer.deviceErrorCode);
});

aplayer.on('error', function(error) {
    console.log('error:', error);
});

// snapshot is available
aplayer.on('finishRecord', function() {
    // the blob object contains the image data that
    // can be downloaded by the user, stored on server etc.
    console.log('snapshot ready: ', aplayer.recordedData);
    $("#img").val(aplayer.recordedData);
    
});




//This is called with the results from from FB.getLoginStatus().
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
	
	


	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
</body>
</html>