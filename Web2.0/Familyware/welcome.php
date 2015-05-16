<?php session_start();
session_destroy();?>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome</title>
		<link rel="stylesheet/less" href="less/grid.less" type="text/css" />
		<link rel="stylesheet/less" type="text/css" href="less/welcome.less"/>
		<script type="text/javascript" src="js/less.js"></script>
		<script src="js/jquery.js"></script>
		<script type="text/javascript" src="js/welcome.js"></script>
    </head>
    
    <body>
		<div class="pulsate">
				<p id="dutch">Raak aan om te starten...</p>
	        	<p id="french">Touchez pour commencer...</p>
	        	<p id="english">Touch to start...</p>
		</div>
    </body>
</html>