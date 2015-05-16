<?php require_once 'header.php';
$tests = getTests();?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader("Choose test", array());?>
</head>
<body>
	<?php addPageHeader(true, true);?>
	<div class="row" id="contentRow1">
		<div class="column grid_1"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goToWithPost('confirmtest.php', 'test', 3);">
				<img src="images/<?php echo $tests[3]['icon'];?>" class="largeButtonIcon" />
				<div class = "largeButtonWrapper">
					<span class="largeButtonTitle"><?php echo $tests[3]['device'];?></span><br/>
					<span class="largeButtonText">&#8364; <?php echo $tests[3]['price'];?></span>
				</div>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goToWithPost('confirmtest.php', 'test', 4);">
				<img src="images/<?php echo $tests[4]['icon'];?>" class="largeButtonIcon" />
				<div class = "largeButtonWrapper">
					<span class="largeButtonTitle"><?php echo $tests[4]['device'];?></span><br/>
					<span class="largeButtonText">&#8364; <?php echo $tests[4]['price'];?></span>
				</div>
			</button>
		</div>
	</div>
	<div class="row" style="height: 30px;"></div>
	<div class="row" id="contentRow2">
		<div class="column grid_1"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goToWithPost('confirmtest.php', 'test', 5);">
				<img src="images/<?php echo $tests[5]['icon'];?>" class="largeButtonIcon" />
				<div class = "largeButtonWrapper">
					<span class="largeButtonTitle"><?php echo $tests[5]['device'];?></span><br/>
					<span class="largeButtonText">&#8364; <?php echo $tests[5]['price'];?></span>
				</div>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goToWithPost('confirmtest.php', 'test', 6);">
				<img src="images/<?php echo $tests[6]['icon'];?>" class="largeButtonIcon" />
				<div class = "largeButtonWrapper">
					<span class="largeButtonTitle"><?php echo $tests[6]['device'];?></span><br/>
					<span class="largeButtonText">&#8364; <?php echo $tests[6]['price'];?></span>
				</div>
			</button>
		</div>
	</div>
	<div class="row" style="height: 100px;"></div>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_2">
			<button type="button" class="smallButton" onclick="goBack();">
					BACK
			</button>
		</div>
		<div class="column grid_1"></div>
	</div>

</body>

<div id="overlay-help" class="overlay">
	<div class="overlay-inner">
		<h3>Need help choosing a category?</h3>
		<img alt="Close" src="images/cross.png">
		<h4>Blood tests</h4>
		<p>This category of tests contains tests like cholesterol level testing and lipid testing.<p>
		<h4>Respiratory tests</h4>
		<p>These tests measure your lung volume and health.</p>
		<h4>Visual tests</h4>
		<p>Tests to check your eyesight.</p>
		<h4>Muscular tests</h4>
		<p>These tests measure your strength and reflexes.</p>
	</div>
</div>	

</html>