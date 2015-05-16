<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Previous Result</title>

<!-- <link href="flot/examples.css" rel="stylesheet" type="text/css"> -->

<style>

* {	padding: 0; margin: 0;}

#plot_container {
	display:inline-block;
	box-sizing: border-box;
	width: 600px;
	height: 400px;
	padding: 30px 15px 15px 15px;
	margin: 15px auto 30px auto;
	border: 1px solid #ddd;
	background: #fff;
	background: linear-gradient(#f6f6f6 0, #fff 50px);
	background: -o-linear-gradient(#f6f6f6 0, #fff 50px);
	background: -ms-linear-gradient(#f6f6f6 0, #fff 50px);
	background: -moz-linear-gradient(#f6f6f6 0, #fff 50px);
	background: -webkit-linear-gradient(#C5EFEA 0px, #5FEAA0 50px);
	box-shadow: 0 3px 10px rgba(0,0,0,0.15);
	-o-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
	-ms-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
	-moz-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
	-webkit-box-shadow: 0 3px 10px rgba(0,0,0,0.1); 
/* 	background-clor: #FFFF99 */
}

#placeholder {
	width: 100%;
	height: 100%;
	font-size: 14px;
	line-height: 1.2em;
}

.tab {
	display: block;
	width: 150px;
	margin: 1px auto;
	font-size:22px;
	background-color: #00CCFF;
	text-align : center;
	opacity: 0.50;
}

label:hover {
	cursor: pointer;
	background-color: #0000FF;
}


</style>

<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="../../excanvas.min.js"></script><![endif]-->
<script type="text/javascript" src="flot/jquery.js"></script>
<script type="text/javascript" src="flot/jquery.flot.js"></script>

<script type="text/javascript">

	$(function() {

		var res = {};       // used for cash result
		var options = {
			lines: {
				show: true
			},
			points: {
				show: true
			},
			grid: {
				hoverable: true,
			},
			xaxis: {
				tickDecimals: 0,
				tickSize: 1
			},
			yaxis: {
				min: 0,
				max: 1
			}
		};
				
		<?php   
			session_start();
			if(!isset($_SESSION["test"])) $_SESSION["test"] = "blood_pressure_test";
		?>		
		var curResId = <?php echo json_encode($_SESSION["test"]) ?>;
		console.log(curResId);
		
		<?php  session_unset(); session_destroy(); ?>   //remember to do this otherwise $_SESSION["test"] will always remain the same
		
		$("<div id='tooltip'></div>").css({
			position: "absolute",
			display: "none",
			border: "1px solid #fdd",
			padding: "2px",
			"background-color": "#fee", //THE quotation mark of bc can't be left out here
			opacity: 0.80
		}).appendTo("body");
		
		$("#tooltip").html("time " + " of " + 14 + " = " + 0.8)
		.css({top: 180, left: 500});

		$("#placeholder").bind("plothover", function (event, pos, item) {
			if (item) {
				$("#tooltip").hide();				
				var x = item.datapoint[0].toFixed(2),
					y = item.datapoint[1].toFixed(2);

				$("#tooltip").html(item.series.label + " of " + x + " = " + y)
					.css({top: item.pageY+5, left: item.pageX+5})
					.fadeIn(200);
			} 
		});
		
		$(".tab").bind("click", function() {
			$(".tab").css( {"background-color" : "#00CCFF", "opacity" : 0.5} );
			$(this).css({"background-color" : "#00CC88", "opacity" : 0.7}).fadeIn(200);
//			console.log("clicking");
			curResId = this.id;
			plot_ajax(curResId);
		});	
		
//		$("label #"+curResId).click();
		console.log($("label #"+curResId));
//	    console.log($("label #"+curResId).click);
		$("label #"+curResId).css({"background-color" : "#00CC88", "opacity" : 0.7});
		plot_ajax(curResId);
		
		function plot_ajax(test_id) {
			var finished = true;
			if(res[test_id] == undefined) {
				finished = false;
				$.ajax({
					type: 'get',
//					asyn: false,
					cache: false,
					url: 'get_result.php', 
					data: {
						test: test_id   //tests[id-1]
					},
					success: function(data_json_str) { 
						console.log(data_json_str);
						var data_json_object = JSON.parse(data_json_str);
						
						var data = [], time = [], points = [];

						var min = eval( data_json_object["value"][0] ), max = min;						
						for(var i=1; i<=data_json_object["value"].length; i++) {
							var value = eval( data_json_object["value"][i-1] );
							points.push( [i, value] );
							time.push(data_json_object["time"][i-1]);
							
							if(value<min) min = value;
							if(value>max) max = value;
						}
						
						data.push({
							label : test_id,  //tests[id-1],
							data :  points    //an 2d array
						});
						
						console.log(data);
						res[test_id] = {
							data : data,
							time : time
						}
						
						//reset min and max range for y-axis
						min = Math.round(min);
						max = Math.ceil(max);
						options.yaxis.min = min;
						options.yaxis.max = max;
						
						finished = true;
					} 
			   });
			} 
			
			function checkFinished() {
				console.log(res);
				if(finished) {
					clearInterval(timer);
					$.plot("#placeholder", res[test_id]["data"], options);	
					$("table").css({
									top : "-22px",
									left: "190px",
									"font-size" : "20px"
							   });
//					$(".legend div").css( {display : "none"} );
				}
			}
			if(!finished) {
				var timer = setInterval(checkFinished, 20);
			}
							
		}
		
	});

	</script>

</head>


<body style="background:url('images/background.jpg'); width:950px; margin:0 auto;">


<div id="title" style="height:115px;width:920px;margin:20px auto">
	<!-- 	<p class="float_left title_text" >Previous Results</p> -->
	<p style="display:inline-block;float:left;width=:50%;font-size:45px;margin-top:30px">Previous Results</p>
	
	<!-- 	<div class="float_right title_icon"> -->
	<div style="display:inline-block;float:right;width:250px">
		<a href="home.html"><img src="images/Home.png"></a>
		<a href="help_previous.php"><img src="images/questionmark.png"></a>
	</div>
</div>

<div id="content">

	<div id="plot_nav" style="display:inline-block;width:150px;margin-top:14px;float:left">
		<label class="tab" id="blood_pressure_test">Blood pressure</label>
		<label class="tab" id="weight">Weight</label>
		<label class="tab" id="3">Test C</label>
		<label class="tab" id="4">Test D</label>
		<label class="tab" id="5">Test E</label>
		<label class="tab" id="6">Test F</label>
		<label class="tab" id="7">Test G</label>
	</div>
	
	<div id="plot_container">
		<div id="placeholder"></div>
	</div>
	
</div>
	
	
<!-- <div id="header"> -->
<!-- 	<p style="display:inline; margin-left:10px; font-size:28px">Choose a test</p> -->
<!-- 	<select id="choose_test" style="margin:5px 0 0 10px; font=size:30px"> -->
<!-- 		<option>Blood Pressure</option> -->
<!-- 		<option>Heart Beat</option> -->
<!-- 		<option>Weight</option> -->
<!-- 	</select> -->
<!-- </div> -->


<!-- 	<span id="hoverdata"></span> -->

<!-- 	<p>One of the goals of Flot is to support user interactions. Try pointing and clicking on the points.</p> -->

<!-- 	<p> -->
<!-- 		<label><input id="enablePosition" type="checkbox" checked="checked"></input>Show mouse position</label> -->
<!-- 		<span id="hoverdata"></span> -->
<!-- 		<span id="clickdata"></span> -->
<!-- 	</p> -->

<!-- 	<p>A tooltip is easy to build with a bit of jQuery code and the data returned from the plot.</p> -->

<!-- 	<p><label><input id="enableTooltip" type="checkbox" checked="checked"></input>Enable tooltip</label></p> -->


</body>

</html>