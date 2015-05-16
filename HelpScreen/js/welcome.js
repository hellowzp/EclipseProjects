$(function() {
    $('#dutch').hide();
    $('#french').hide();
    $('#english').hide();
	animation_loop();
});

var lang = 0;
var i = 0;

function animation_loop() {
    switch(lang) {
        case 0:
            $('#dutch').fadeIn(1000);
            $('#dutch').fadeOut(1000);
            break;
        case 1:
            $('#french').fadeIn(1000);
            $('#french').fadeOut(1000);
            break;
        case 2:
            $('#english').fadeIn(1000);
            $('#english').fadeOut(1000);
            break;
        default:
            // Do nothing
    }
    
    lang++;
    if(lang >= 3) {
        lang = 0;
    }
	
	setTimeout(function() { i++; if (i < 1000) { animation_loop(); } }, 2300);
}

window.onclick = function() {
	setTimeout("window.location.href= 'chooseLanguage.html'",0);
}