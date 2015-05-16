<?php require_once 'header.php';
$test = getTest();
$testInclude;
switch ($test['id']) {
	case 1:
		$testInclude="js/interfaceWithBloodPressureDevice.js";
		break;
	case 2:
		$testInclude="js/interfaceWithWeightScale.js";
		break;
	default:
		$testInclude = "";
		break;
}?> 
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
	<script type="text/javascript">	var test = <?php echo json_encode($test);?>;
									var amountOfSteps = test.instructions.split("|").length;</script>
	<?php addHTMLHeader($test['device'], array("less/genericTest.less", "less/demo.css", "less/jquery.flipster.css", "js/jquery.flipster.js", "less/flipsternavtabs.less", $testInclude, "js/processResult.js"));?>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="">
	<meta name="viewport" content="width=550, initial-scale=1">
<!-- Please put this function in another file and include it -->
	<script type="text/javascript">
		$(function(){
				var instructions = test.instructions.split("|");
				var pictures = test.pictures.split("|");
				for(var j = 1; j <= instructions.length; j++) {
					$('<li></li>').addClass("slide").attr('id', 'Carousel-' + j).attr('title', 'Step ' + j).appendTo($("#putThemHere"));
					$('<div></div>').css({"position":"absolute", "height":"100%", "width":"100%"}).attr('id', 'car' + j).appendTo($("#Carousel-" + j));
					$('<div> Step ' + j + '</div>').addClass("step").appendTo($("#car" + j));
					$('<span>' + instructions[j - 1] + '</span>').addClass("carouselText").appendTo($("#car" + j));
					$('<img></img>').addClass("slidePicture").attr("src", "images/" + pictures[j - 1]).appendTo($("#car" + j));
				}
				
				$("#Carousel").flipster({
					itemContainer: 	'ul', // Container for the flippin' items.
					itemSelector: 	'li', // Selector for children of itemContainer to flip
					style:			'carousel', // Switch between 'coverflow' or 'carousel' display styles
					start: 			0, // Starting item. Set to 0 to start at the first, 'center' to start in the middle or the index of the item you want to start with.
					
		
					enableTouch: 	true, // Enable swipe navigation for touch devices
					
					enableNav: 		true, // If true, flipster will insert an unordered list of the slides
					enableNavButtons: 	true, // If true, flipster will insert Previous / Next buttons
					
					onItemSwitch: 	function(){}, // Callback function when items are switches
				});
		
		});
	</script>
</head>

<body>
	<?php addPageHeader(true, true)?>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_10">
			<pre></pre>
			<div id="Main-Content">
				<div class="Container">
		
					<!-- Flipster List -->
					<div id="Carousel">
						<ul class="flip-items" id="putThemHere">
						</ul>
					</div>
					<!-- End Flipster List -->
		
				</div>
			</div>
		</div>
		<div class="column grid_1"></div>
	</div>
	
	
<div class="row" style="height: 20px;"></div>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_2">
			<button id="prevButton" class="smallButton" onclick="$('#Carousel').flipster('jump', 'left');">
				<span><?php echo getString(181);?></span>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_2">
			<button id="videoButton" class="smallButton" onclick="goTo('videoBloodPressure.php')">
				<img class="smallButtonIcon" style="margin-left:-15px;" src="images/videoIcon.png">
				<span><?php echo getString(182);?></span>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_2">
			<button id="nextButton" class="smallButton" onclick="$('#Carousel').flipster('jump', 'right');">
				<span><?php echo getString(183);?></span>
			</button>
			<button id="finishButton" style="display:none;" class="smallButton" onclick="goTo('previous_result.php')"> <!-- onclick="processResult();"-->
				<span><?php echo getString(184);?></span>
			</button>
		</div>
	</div>
</body>

<?php addHelp(getString(185));?>

</html>
