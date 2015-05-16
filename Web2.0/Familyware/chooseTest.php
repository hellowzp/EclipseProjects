<?php require_once 'header.php';
$tests = getTests();?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader(getString(161), array());?>
</head>
<body>
	<?php addPageHeader(true, true);?>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goToWithPost('confirmtest.php', 'test', 0);">
				<img src="images/<?php echo $tests[0]['icon'];?>" class="largeButtonIcon"/>
				<div class = "largeButtonWrapper">
					<span class="largeButtonTitle"><?php echo $tests[0]['device'];?></span><br/>
					<span class="largeButtonText">&#8364; <?php echo $tests[0]['price'];?></span>
				</div>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goToWithPost('confirmtest.php', 'test', 1);">
				<img src="images/<?php echo $tests[1]['icon'];?>" class="largeButtonIcon"/>
				<div class = "largeButtonWrapper">
					<span class="largeButtonTitle"><?php echo $tests[1]['device'];?></span><br/>
					<span class="largeButtonText">&#8364; <?php echo $tests[1]['price'];?></span>
				</div>
			</button>
		</div>
	</div>
	<div class="row" style="height: 30px;"></div>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goToWithPost('confirmtest.php', 'test', 2);">
				<img src="images/<?php echo $tests[2]['icon'];?>" class="largeButtonIcon"/>
				<div class = "largeButtonWrapper">
					<span class="largeButtonTitle"><?php echo $tests[2]['device'];?></span><br/>
					<span class="largeButtonText">&#8364; <?php echo $tests[2]['price'];?></span>
				</div>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goTo('otherTests.php');">
				<img src="images/OtherTests.png" class="largeButtonIcon"/>
					<span><?php echo getString(162)?></span>
			</button>
		</div>
	</div>
	<div class="row" style="height: 100px;"></div>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_2">
			<button type="button" class="smallButton" onclick="goBack();">
					<?php echo getString(163)?>
			</button>
		</div>
		<div class="column grid_1"></div>
	</div>

</body>

<?php addHelp(getString(164));?>

</html>