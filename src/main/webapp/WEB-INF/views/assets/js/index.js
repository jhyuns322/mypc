$(function(){ 
	var x = setInterval(fadeSlide, 3000);
	var i = 2;
	
	function fadeSlide() {
		if (i == 0) {
			$("#mypc-slide>img").fadeIn("slow");
			i = 2;
		} else {
			$("#mypc-slide>img").eq(i).fadeOut("slow");
			i--;
		}
	}
});