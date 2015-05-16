<?php require_once 'header.php';?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader("Payment method" . (isset($_SESSION["recharge"]) ? " (&#8364; " . $_SESSION["recharge"] . ")" : ""), array());?>
</head>
<body>
	<?php addPageHeader(true, true);?>
	<div class="row">
		<div class="column grid_1"></div>
		<div class="column grid_4">
			<button type="button" class="iconButton" onclick="goToWithPost('<?php echo getOriginPage();?>', 'confirmRecharge', 'yes');">
				<img src="images/Paypal.png" class="iconButtonIcon"/>
			</button>
		</div>
		<div class="column grid_2"></div>
		<div class="column grid_4">
			<button type="button" class="iconButton" onclick="goToWithPost('<?php echo getOriginPage();?>', 'confirmRecharge', 'yes');">
				<img src="images/mastercard.png" class="iconButtonIcon"/>
			</button>
		</div>
		
		<div class="column grid_7"></div>
		
	</div>
	
	<div class="row" style="height: 110px;" id="header">
		<div class="column grid_1">
		</div>
		
		<div class="column grid_2">
				<button type="button" class="smallButton" onclick="goTo('rechargeBalance.php')">
					BACK
				</button>
		</div>
	</div>
	
	<div id="overlay-help" class="overlay">
		<div class="overlay-inner">
			<h3>Choose a payment method.</h3>
			<img alt="Close" src="images/cross.png">
			<p>Currently two payment methods are supported: PayPal and debit/credit card transfer.</p>
			<h4>PayPal</h4>
			<p>Choose this option if you have a PayPal account and what to pay through this method. If you have no idea what this is, this is not the option for you.<p>
			<h4>Debit & Credit card transfer</h4>
			<p>This option allows you to pay with you debit or credit card. We currently support Bancontact, Maestro, Mastercard and Visa.<br>
			If you have another type of card (American Express for example), please contact the BeWell assistant.</p>
			<h4>Back</h4>
			<p>Choose back if you want to recharge a different amount.</p>
		</div>
	</div>

</body>
</html>