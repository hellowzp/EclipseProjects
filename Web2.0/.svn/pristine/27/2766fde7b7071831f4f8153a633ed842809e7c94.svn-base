function processResult() {
	console.log("processing");
	var type = test.id;
	console.log("type=" + type);
	switch(parseInt(type)) {
	case 1: //Bloodpressure
		console.log("BP process");
		var result = getBloodPressureResults();
		$.post('postController.php', {date: result.year + "-" + result.month + "-" + result.day, type: "1", data: result.systric + "|" + result.diastric});
		$.post('postController.php', {date: result.year + "-" + result.month + "-" + result.day, type: "2", data: result.pulse});
		break;
	case 2: //Weight
		console.log("weight process");
		var result = getBodyCompositionResults();
		$.post('postController.php', {date: result.year + "-" + result.month + "-" + result.day, type: "3", data: result.weight});
		break;
	}
}