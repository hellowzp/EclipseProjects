document.write('<audio id="playSound" src="sounds/crystal.mp3">Audio will not play in this browser</audio>');

function goBack() {
	document.getElementById('playSound').play();
	setTimeout(function(){
		window.history.go(-1);
	}, 150);
    
}