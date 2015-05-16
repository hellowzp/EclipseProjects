<?php require_once 'header.php';
?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader(getString(142), array());?>
</head>
<body>
	<?php addPageHeader(false, true);?>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goTo('chooseTest.php')">
				<img src="images/SelectTest.png" class="largeButtonIcon"/>
				<span><?php echo getString(143)?></span>
			</button>
		</div>
		<div class="column grid_2"></div>
		
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goTo('previous_result.php')">
				<img src="images/PreviousResults.png" class="largeButtonIcon"/>
				<span><?php echo getString(144)?></span>
			</button>
		</div>
	</div>
	
	<div class="row" style="height: 30px;"></div>
	
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goTo('rechargeBalance.php')">
				<img src="images/RechargeBalance.png" class="largeButtonIcon"/>
				<span><?php echo getString(145)?></span>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_4">
			<button type="button" class="largeButton" onclick="goTo('welcome.php')">
				<img src="images/LogOut.png" class="largeButtonIcon"/>
				<span><?php echo getString(146)?></span>
			</button>
		</div>
	</div>
</body>
<?php addHelp(getString(141));?>
</html>