<?php require_once 'header.php';?>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome</title>
		<link rel="stylesheet/less" href="less/grid.less" type="text/css" />
		<link rel="stylesheet/less" type="text/css" href="less/welcome.less"/>
		<script type="text/javascript" src="js/less.js"></script>
		<script src="js/jquery.js"></script>
		<script type="text/javascript">
		$(function() {
			setTimeout("location.href= 'home.php'", 4000);
			$('.pulsate p').hide();
			$('.pulsate p').fadeIn(1000);
            setTimeout(function() {$('.pulsate p').fadeOut(1000)}, 2000);
		});
		</script>
    </head>
    
    <body>
		<div class="pulsate">
				<p><?php echo getString(121) .  ", " . $_SESSION["firstname"] . "!"?></p>
		</div>
    </body>
</html>