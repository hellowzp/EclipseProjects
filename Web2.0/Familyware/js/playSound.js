document.write('<audio id="playSound" src="sounds/crystal.mp3">Audio will not play in this browser</audio>');

function goBack() {
	document.getElementById('playSound').play();
	setTimeout(function(){history.back();}, 150);
}

function goTo(location) {
	document.getElementById('playSound').play();
	setTimeout(function(){window.location.href=location;}, 150);
}

function goToWithPost(location, postParam, postValue) {
	$.ajax({
		  type: "POST",
		  url: "postController.php",
		  cache: false,
		  data: postParam + "=" + postValue,
		  async:false,
		  complete: function() {
			  goTo(location);
		  }
		});
}