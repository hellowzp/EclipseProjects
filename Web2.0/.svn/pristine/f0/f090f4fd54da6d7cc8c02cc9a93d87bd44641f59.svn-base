document.write('<audio id="playSound" src="sounds/crystal.mp3">Audio will not play in this browser</audio>');

$(function() {
    $('#dutch').hide();
    $('#french').hide();
    $('#english').hide();
    animation_loop();
    setInterval(animation_loop, 4250);
});

var lang = 0;
var i = 0;

function animation_loop() {
    switch(lang) {
        case 0:
            $('#dutch').fadeIn(1000);
            setTimeout(function() {$('#dutch').fadeOut(1000)}, 2000);
            break;
        case 1:
            $('#french').fadeIn(1000);
            setTimeout(function() {$('#french').fadeOut(1000)}, 2000);
            break;
        case 2:
            $('#english').fadeIn(1000);
            setTimeout(function() {$('#english').fadeOut(1000)}, 2000);
            break;
        default:
            // Do nothing
    }
    
    lang++;
    if(lang >= 3) {
        lang = 0;
    }
}

window.onclick = function() {
	document.getElementById('playSound').play();
	setTimeout("window.location.href= 'chooseLanguage.php'",150);
}