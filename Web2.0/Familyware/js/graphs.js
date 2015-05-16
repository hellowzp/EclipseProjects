var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"];
var colors = ["#369EAD", "#C24642", "#7F6084", "#86B402"];
var backGroundColor = "white";

var max = new Array();
var min = new Array();
var data = new Array();
for(var i = 0; i < datatypes.length; i++) {
	max[i] = -10000;
	min[i] = 10000;
	data[i] = new Array();
	if(datatypes[i].datafields > 1) {
		for(var j = 0; j < datatypes[i].datafields; j++) {
			data[i][j] = new Array();
		}
	}
}

String.prototype.strToDate = function() {
	return new Date(parseInt(this.substring(0,4)), parseInt(this.substring(5,7)) - 1, parseInt(this.substring(8)));
}

Date.prototype.dateToStr = function() {
	return months[this.getMonth()] + " " + this.getDate() + " " + this.getFullYear();
}

function parseData() {
	for(var i = 0; i < measurements.length; i++) {
		var m = measurements[i];
		if(datatypes[m.type - 1].datafields == 1) {
			m.x = m.date.strToDate();
			m.y = parseFloat(m.data);
			data[m.type - 1].push(m);
			min[m.type - 1] = Math.min(min[m.type - 1], m.y);
			max[m.type - 1] = Math.max(max[m.type - 1], m.y);
		} else {
			var tempData = m.data.split("|");
			for(var j = 0; j < datatypes[m.type - 1].datafields; j++) {
				var n = new Object();
				n.x = m.date.strToDate();
				n.y = parseFloat(tempData[j]);
				data[m.type - 1][j].push(n);
				min[m.type - 1] = Math.min(min[m.type - 1], n.y);
				max[m.type - 1] = Math.max(max[m.type - 1], n.y);
			}
		}
		delete m.type;
		delete m.date;
		delete m.data;
	}
	delete measurements;
}

function minBound(min, max) {
	var val;
	var multiplier = 0.01;
	while(typeof val == 'undefined') {
		if(max - min < 10 * multiplier) {
			val = Math.floor(min / multiplier * 2) * multiplier / 2;
			if(val == min) {
				val -= multiplier / 2;
			}
		}
		multiplier *= 10;
	}
	return val;
}

function maxBound(min, max) {
	var val;
	var multiplier = 0.01;
	while(typeof val == 'undefined') {
		if(max - min < 10 * multiplier) {
			val = Math.ceil(max / multiplier * 2) * multiplier / 2;
			if(val == max) {
				val += multiplier / 2;
			}
		}
		multiplier *= 10;
	}
	return val;
}

function showContainer(index) {
	for(var i = 0; i < datatypes.length; i++) {
		if(i == index) {
			$("#button" + i).css({"border-right":"none"});
			$("#container" + i).css({"display":"block"});
		} else {
			$("#button" + i).css({"border-right":"solid #404040 1px"});
			$("#container" + i).css({"display":"none"});
		}
	}
}

function showChart(index) {
	showContainer(index);
	if(datatypes[index].datafields == 1) {
		initTooltip(data[index], datatypes[index].unit);
	} else {
		initTooltip(data[index], datatypes[index].unit, datatypes[index].seriesnames.split("|"));
	}
}

function initTooltip(data, unit, name) {
	var borderColor;
	if(data[0] instanceof Array) {
		borderColor = "128, 128, 128";
	} else {
		borderColor = "54, 158, 173";
	}
	$("div.canvasjs-chart-tooltip").css({"position":"absolute", "height":"auto", "box-shadow":"rgba(0, 0, 0, 0.0980392) 1px 1px 2px 2px", "z-index":"1000", "border-radius":"5px", "display":"block", "left":"auto", "right":"10px", "top":"10px", "bottom":"auto", "-webkit-transition":"left 0.2s ease-out, bottom 0.2s ease-out", "transition":"left 0.2s ease-out, bottom 0.2s ease-out"});
	$("div.canvasjs-chart-tooltip div").css({"width":"auto", "height":"auto", "min-width":"50px", "line-height":"20px", "margin":"0px", "padding":"5px", "font-family":"Calibri, Arial, Georgia, serif", "font-weight":"400", "font-style":"italic", "font-size":"14px", "color":"rgb(0, 0, 0)", "text-shadow":"rgba(0, 0, 0, 0.0980392) 1px 1px 1px", "text-align":"left", "border":"2px solid rgb(" + borderColor + ")", "text-indent":"0px", "white-space":"nowrap", "border-radius":"5px", "-webkit-user-select":"none", "background":"rgba(255, 255, 255, 0.901961)"});
	$("div.canvasjs-chart-tooltip div").empty();
	if(data[0] instanceof Array) {
		$("<span>" + data[0][data[0].length - 1].x.dateToStr() + "<br/></span>").css({"font-size":"20px"}).appendTo($("div.canvasjs-chart-tooltip div"));
		for(var i = 0; i < data.length; i++) {
			$("<span>" + name[i] + ":<span>").css({"font-size":"20px", "color":colors[i]}).appendTo($("div.canvasjs-chart-tooltip div"));
			$("<span> " + data[i][data[i].length - 1].y + " " + unit + "<br/></span>").css({"font-size":"20px"}).appendTo($("div.canvasjs-chart-tooltip div"));
		}
	} else {
		$("<span>" + data[data.length - 1].x.dateToStr() + ":</span>").css({"font-size":"20px", "color":"#369EAD"}).appendTo($("div.canvasjs-chart-tooltip div"));
		$("<span> " + data[data.length - 1].y + " " + unit + "</span>").css({"font-size":"20px"}).appendTo($("div.canvasjs-chart-tooltip div"));
	}
	$("div.canvasjs-chart-tooltip").fadeIn(100);
}

function getDataElement(data, name) {
	if(data[0] instanceof Array) {
		var result = new Array();
		for(var i = 0; i < data.length; i++) {
			result.push({type: "line", name: name[i], dataPoints: data[i]});
		}
		return result;
	} else {
		return [{
			type: "line",
			dataPoints: data
		}];
	}
}

function createGraph(container, title, unit, minimum, maximum, data, name) { // if multiple series: data should be an array of arrays containing the data, data[0] should contain the highest values, name[] should match the index names
	var borderColor;
	if(data[0] instanceof Array) {
		borderColor = "grey";
	} else {
		borderColor = colors[0];
	}
	var chart = new CanvasJS.Chart(container, {
		backgroundColor: backGroundColor,
		title: {
			text: title
		}, toolTip: {
			animationEnabled: true,
			shared: true,
			borderColor: borderColor ,
			content: function(e) {
				var content;
				$("div.canvasjs-chart-tooltip").css({"right":"auto", "top":"auto"});
				if(e.entries.length > 1) {
					content = "<span style='font-size: 20px;'>" + e.entries[0].dataPoint.x.dateToStr();
					for(var i = 0; i < e.entries.length; i++) {
						content += "<br/><span style='color: " + e.entries[i].dataSeries.color + ";'>" + e.entries[i].dataSeries.name + ":</span> " + e.entries[i].dataPoint.y + " " + unit;
					}
					content += "</span>";
				} else {
					content = "<span style='font-size: 20px;'><span style='color: " + e.entries[0].dataSeries._colorSet[0] + ";'>"
					+ e.entries[0].dataPoint.x.dateToStr() + ":</span> " + e.entries[0].dataPoint.y + " " + unit + "</span>";
				}
				return content;
			}
		}, axisX: {
			title: "Date",
			gridThickness: 0
		}, axisY: {
			title: title + " (" + unit + ")",
			minimum: minBound(minimum, maximum),
			maximum: maxBound(minimum, maximum)
		}, data: getDataElement(data, name)
	});
	chart.render();
}

window.onload = function() {
	var j = 0;
	var buttonheight = Math.ceil(450 / datatypes.length);
	var containerheight = buttonheight * datatypes.length - 4;
	$('<button></button>').addClass("top button").css({"height":buttonheight + "px"}).attr('id', 'button' + j).click(function() {showChart(this.id.substring(this.id.length - 1))}).appendTo($("#buttonHolder"));
	$('<img></img>').addClass("buttonIcon").attr('src', 'images/' + datatypes[j].icon).appendTo($("#button" + j));
	$('<span>' + datatypes[j].name + '</span>').appendTo($("#button" + j));
	for(j = 1; j < datatypes.length - 1; j++) {
		$('<button></button>').addClass("button").css({"height":buttonheight + "px"}).attr('id', 'button' + j).click(function() {showChart(this.id.substring(this.id.length - 1))}).appendTo($("#buttonHolder"));
		$('<img></img>').addClass("buttonIcon").attr('src', 'images/' + datatypes[j].icon).appendTo($("#button" + j));
		$('<span>' + datatypes[j].name + '</span>').appendTo($("#button" + j));
	}
	$('<button></button>').addClass("bottom button").css({"height":buttonheight + "px"}).attr('id', 'button' + j).click(function() {showChart(this.id.substring(this.id.length - 1))}).appendTo($("#buttonHolder"));
	$('<img></img>').addClass("buttonIcon").attr('src', 'images/' + datatypes[j].icon).appendTo($("#button" + j));
	$('<span>' + datatypes[j].name + '</span>').appendTo($("#button" + j));
	for(var i = 0; i < datatypes.length; i++) {
		$('<div></div>').addClass("column grid_8 graph").css({"height":containerheight + "px"}).attr('id', 'container' + i).appendTo($("#graphHolder"));
		if(datatypes[i].datafields == 1) {
			createGraph("container" + i, datatypes[i].name, datatypes[i].unit, min[i], max[i], data[i]);
		} else {
			createGraph("container" + i, datatypes[i].name, datatypes[i].unit, min[i], max[i], data[i], datatypes[i].seriesnames.split("|"));
		}
	}
	if(currentTest != null) {
		switch(parseInt(currentTest)) {
		case 1:
			showChart(0);
			break;
		case 2:
			showChart(2);
			break;
		case 3:
			showChart(3);
			break;
		default:
			showChart(0);
		}
	} else {
		showChart(0);
	}
}
parseData();