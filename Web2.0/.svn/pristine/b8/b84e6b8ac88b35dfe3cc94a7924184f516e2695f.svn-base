<script type="text/javascript" src="js/interfaceWithBloodPressureDevice.js"></script>
<script type="text/javascript">
	function processBloodpressure() {
		var results = getBloodPressureResults();
		$.post("postController.php", {type: "1", date: results.year + "-" + results.month + "-" + results.day, data: results.systric + "|" + results.diastric});
		$.post("postController.php", {type: "2", date: results.year + "-" + results.month + "-" + results.day, data: results.pulse});
	}
</script>