<?php require_once 'header.php';?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader("Weight", array());?>
</head>
<body>
	<?php addPageHeader(true, true);?>
<div class="row">
	<div class="column grid_3">
	</div>
	<div class="column grid_6">
		<video width="640" height="360" autoplay>
			  <source src="videos/weight.mp4" type="video/mp4">
			  There is a little problem with the video, don't worry.<br />
			  Ask the pharmacist for help if you have any problems.
		</video>
		</div>
	</div>
	<div class="row" style="height: 50px;"></div>
	<div class="row">
		<div class="column grid_1">
		</div>
		<div class="column grid_2">
				<button type="button" class="smallButton" onclick="goBack()">
					GO BACK
				</div>
		</div>
	</div>
</body>
</html>