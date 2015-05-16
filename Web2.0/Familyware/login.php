<?php require_once 'header.php';?>
<!DOCTYPE html>
<html>
<head>
	<?php addHTMLHeader(getString(102), array());?>
	<script type="text/javascript">
	$(function() {
		setTimeout("goToWithPost('hello.php', 'loginid', 1);", 3500);
	});
	</script>
</head>
<body>
<body>
	<?php addPageHeader(false, true);?>
<div class="row" id="content">
	<div class="column grid_4"></div>
	<div class="column grid_4">
		<video width="640" height="360" autoplay loop>
		  <source src="videos/insertCard.mp4" type="video/mp4">
		  <?php echo getString(103);?>
		</video>
	</div>
	<div class="column grid_4"></div>
</div>
</body>
<?php addHelp(getString(101));?>
</html>