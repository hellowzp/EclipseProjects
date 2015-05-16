 <?php require_once 'header.php';?>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript">	var measurements = <?php getMeasurements();?>;
									var datatypes = <?php getDataTypes();?>;
									var currentTest = 0 <?php if(isset($_SESSION["test"])) {echo "+ " . $_SESSION["test"];}?>;</script>
	<?php addHTMLHeader("Previous results", array("less/previousResults.less", "js/graphs.js", "js/canvasjs.min.js"));?>
</head>
<body>
	<?php addPageHeader(true, true);?>
	<div class="row" id="graphHolder">
		<div class="column grid_1"></div>
		<div class="column grid_2 buttonholder" id="buttonHolder">
		</div>
	</div>
	
	<div style="margin-top: 20px;"></div>
	<div class="row">
		<div class="column grid_2"></div>
		<div class="column grid_2">
			<button class="smallButton" onclick="">
				<img src="images/mail-64.png" class="smallButtonIcon">
				<span>Email</span>
			</button>
		</div>
		<div class="column grid_1"></div>
		<div class="column grid_2">
			<button class="smallButton" onclick="">
				<img src="images/print_64.png" class="smallButtonIcon">
				<span>Print</span>
			</button>
		</div>
		<div class="column grid_1"></div>
		<div class="column grid_2">
			<button class="smallButton" onclick="javascript:window.location='home.php'">
				<img src="images/back.png" class="smallButtonIcon">
				<span>Back</span>
			</button>
		</div>
		<div class="column grid_2"></div>
	</div>
	
</body>

<div id="overlay-help" class="overlay">
	<div class="overlay-inner">
		<h3>Need help with the graph?</h3>
		<img alt="Close" src="images/cross.png">
		<h4>Help 1</h4>
		<p>Dummy.</p>
		<h4>Help 2</h4>
		<p>Dummy.</p>
		<h4>Help 3</h4>
		<p>Dummy.</p>
	</div>
</div>

</html>