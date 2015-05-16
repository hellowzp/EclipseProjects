<?php require_once 'header.php';
$test = getTest();?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader($test['device'], array());?>
</head>
<body>
	<?php addPageHeader(true, true);?>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_10">
			<p class="greyText">
				You are about to start a <?php echo $test['device'];?> test<br/>
				This test will take approximately <?php echo $test['duration'];?> minutes<br/>
				<?php echo getBalance() >= $test['price'] ? "This test will cost you &#8364; " . $test['price'] : "You need &#8364; " . ($test['price'] - getBalance()) . " more to perform this test";?>
			</p>
		</div>
	</div>
	<div class="row" style="height: 100px;"></div>
	<div class="row">
		<div class="column grid_1"></div>
	
		<div class="column grid_2">
			<button class="smallButton" onclick="goTo('chooseTest.php');">BACK</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_2">
			<?php if(getBalance() < $test['price']) {echo '<button class="smallButton" onclick="goTo(\'rechargeBalance.php\')">RECHARGE BALANCE</button>';}?>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_2">
			<button class="smallButton" onclick ="goToWithPost('genericTest.php', 'pay', <?php echo $test['price']?>)" <?php if(getBalance() < $test['price']) {echo "disabled";}?>><?php echo getString(179);?></button>
		</div>
	</div>
</body>

<?php addHelp(getString(180));?>

</html>