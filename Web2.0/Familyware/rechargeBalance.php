<?php require_once 'header.php';
saveOriginPage();?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader("Please select an amount", array("less/list.less"));?>
</head>
<body>
	<?php addPageHeader(true, true);?>
	<div class="row">
		<div class="column grid_4"></div>
		<div class="column grid_4">
			<ul>
				<li>
					<button class="smallButton" onclick="goToWithPost('paymentMethod.php', 'recharge', 10);">
						<span class="item">&#8364; 10</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button class="smallButton" onclick="goToWithPost('paymentMethod.php', 'recharge', 20);">
						<span class="item">&#8364; 20</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button class="smallButton" onclick="goToWithPost('paymentMethod.php', 'recharge', 50);">
						<span class="item">&#8364; 50</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button class="smallButton" onclick="goToWithPost('paymentMethod.php', 'recharge', 100);">
						<span class="item">&#8364; 100</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button class="smallButton" onclick="goToWithPost('paymentMethod.php', 'recharge', 200);">
						<span class="item">&#8364; 200</span>
					</button>
				</li>
	
			</ul>
		</div>
	</div>

</body>

<div id="overlay-help" class="overlay">
	<div class="overlay-inner">
		<h3>Unsure what to do?</h3>
		<img alt="Close" src="images/cross.png">
		<h4>Recharging</h4>
		<p>Tap one of the buttons to recharge the amount you want to transfer to your BeWell card.<p>
		<h4>Not sure of the amount you need?</h4>
		<p>Each test indicates what it's price is. If you are unsure what test you want to take,
		you can opt to recharge &#8364; 10, if you need more, you can always come back here.</p>
	</div>
</div>

</html>