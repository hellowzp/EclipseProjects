<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet/less" type="text/css" href="less/header&content.less" media="screen" />
<link rel="stylesheet/less" href="less/grid.less" type="text/css" />
<script type="text/javascript" src="js/less.js"></script>
<title>help</title>
</head>
<body>

<!---------------------- HEADER --------------------------->
<div class="row" style="height: 110px;" id="header">
	<div class="column grid_1"></div>
	<div class="column grid_8">
		HELP
	</div>
	<div class="column grid_1">
		<img src="images/Home.png" class="clickImage" onclick="location.href='home.php'">
	</div>
<!-- 	<div class="column grid_1"> -->
		<img src="images/questionmark.png" class="clickImage" onclick="location.href='help.php'">
<!-- 	</div> -->
</div>

<div class="row" style="height: 2px;">
	<div class="column grid_1"></div>
	<div class="column grid_10" style="background-color: #404040;"></div>
</div>
<div class="row" style="height: 20px;"></div>


<!---------------------- CONTENT --------------------------->

<div id="help_previous" style="width:80%; margin:5px auto">
<h2>Previous Result</h2>
<p>Choose a test in the accordion window to have a look at your previous results </p>
<p>If you want to go take a new test, click the home button.</p>

	<div class="column grid_3">
			<!--- BACK BUTTON --->
			<div class="button">
				<a href="chooseTest.php">BACK</a>
			</div>
		</div>

		<div class="column grid_9"></div>

</div>


</body>
</html>