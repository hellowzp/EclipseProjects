<?php require_once 'header.php';?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader("Please select your language", array("less/list.less"));?>
</head>
<body>
	<?php addPageHeader(false, true)?>
	<div class="row" id="content">
		<div class="column grid_4"></div>
		<div class="column grid_4">
			<ul>
				<li>
					<button type="button" class="smallButton" onclick="goToWithPost('login.php', 'language', 'Nederlands');">
						<span class="item">Nederlands</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button type="button" class="smallButton" onclick="goToWithPost('login.php', 'language', 'English');">
						<span class="item">English</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button type="button" class="smallButton" onclick="goToWithPost('login.php', 'language', 'Francais');">
						<span class="item">Fran&ccedil;ais</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button type="button" class="smallButton" onclick="goToWithPost('login.php', 'language', 'Deutsch');">
						<span class="item">Deutsch</span>
					</button>
				</li>
				<li>
					<div style="height: 10px; width: 100%;"></div>
				</li>
				<li>
					<button type="button" class="smallButton" onclick="goTo('')">
						<span class="item">Other language</span>
					</button>
				</li>
			</ul>
		</div>
	</div>
</body>

<div id="overlay-help" class="overlay">
	<div class="overlay-inner">
		<h3>Need help choosing a language?</h3>
		<img alt="Close" src="images/cross.png">
		<h4>Nederlands</h4>
		<p>Klik op deze knop om verder te gaan in het Nederlands.<p>
		<h4>English</h4>
		<p>Click this button to continue in English.</p>
		<h4>Français</h4>
		<p>Cliquez sur ce bouton pour procéder en français.</p>
		<h4>Deutsch</h4>
		<p>Klicken Sie auf diese Schaltfläche, um in der Deutsch Sprache weiter zu gehen.</p>
		<h4>Other</h4>
		<p>Druk op deze knop indien u uw taal niet kan vinden in de lijst.</p>
		<p>Cliquez sur ce bouton si vous ne trouvez pas votre langue dans la liste.</p>
		<p>Click this button if you cannot find your language in the list.</p>
		<p>Klicken Sie auf diese Schaltfläche, wenn Sie Ihre Sprache in der Liste nicht finden können.</p>
	</div>
</div>

</html>