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
	padding: 30px 20px 15px 15px;
	margin: 10px auto 5px auto;
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

.button {
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    width: 114px;
    padding: 3px 15px;    /* vertical and horizental padding */
    margin: 5px 4px 0px auto;
    border: 1px solid #ffea03;
    border-radius: 5px;
    background: #4f57e3;
    background: -webkit-gradient(linear, left top, left bottom, from(#4f57e3), to(#24d648));
    background: -moz-linear-gradient(top, #4f57e3, #24d648);
    background: linear-gradient(to bottom, #4f57e3, #24d648);
    text-shadow: #591717 1px 1px 1px;
    font: normal normal bold 30px times new roman;
    color: #ffffff;
    text-decoration: none;
}
.button:hover,
.button:focus {
    background: #5f68ff;
    background: -webkit-gradient(linear, left top, left bottom, from(#5f68ff), to(#2bff56));
    background: -moz-linear-gradient(top, #5f68ff, #2bff56);
    background: linear-gradient(to bottom, #5f68ff, #2bff56);
    color: #ffffff;
    text-decoration: none;
}
.button:active {
    background: #2f3488;
    background: -webkit-gradient(linear, left top, left bottom, from(#2f3488), to(#24d648));
    background: -moz-linear-gradient(top, #2f3488, #24d648);
    background: linear-gradient(to bottom, #2f3488, #24d648);
}
.button#print:before{
    content:  "\0000a0";
    display: inline-block;
    height: 32px;
    width: 32px;
    line-height: 32px;
    margin: 0 4px -6px -4px;
    position: relative;
    top:  0px;
    left: 0px;
    background: url("images/print.png");
    background-size: 100% 100%;
}
.button#send:before{
    content:  "\0000a0";
    display: inline-block;
    height: 32px;
    width: 32px;
    line-height: 32px;
    margin: 0 4px -6px -4px;
    position: relative;
    top:  0px;
    left: 0px;
    background: url("images/mail.png");
    background-size: 100% 100%;
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
		
		$("#placeholder").bind("plothover", function (event, pos, item) {
			if (item) {
				$("#tooltip").hide();				
				var x = item.datapoint[0].toFixed(2),
					y = item.datapoint[1].toFixed(2),
					i = Math.round(x) - 1;

				var value = res[curResId]["data"][0]["data"][i][1],
					time  = res[curResId]["time"][i];

				$("#tooltip").html("Result " + value + "<br>Time " + time)
					.css({top: item.pageY+5, left: item.pageX+5})
					.fadeIn(200);
				console.log(item.pageY+5 + " " + item.pageX+5);
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
//		console.log($("label #"+curResId));
//	    console.log($("label #"+curResId).click);
//		$("label #"+curResId).css({"background-color" : "#00CC88", "opacity" : 0.7});
		plot_ajax(curResId);
		
		function plot_ajax(test_id) {
			if(res[test_id] == undefined) {
				$.ajax({
					type: 'get',
//					asyn: false,
					cache: false,
					url: 'php/get_result.php', 
					data: {
						test: test_id   //tests[id-1]
					},
					success: function(data_json_str) { 
						console.log(data_json_str);
						var data_json_object = JSON.parse(data_json_str);
						
						if(data_json_object["value"].length < 1) {
							$("#placeholder").empty();
							$("#tooltip").hide();
							
							$("<div class='error'><p>Sorry, this test has not been taken yet!</p><br><p>You can go to the home page to do a test and then come here to see the result again.</p></div>")
							.css({								
								width : "560px",
								margin: "20px",
								"font-size" : "20px"
							}).appendTo("#placeholder").fadeIn(300);
							return;
						}
						
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
						
						plot();
					} 
			   });  //end of ajax
				
			} else {  //end of test not defined
				$("#placeholder").empty();
				plot();
			}
					
			function plot() {
//				console.log(res);					
				$.plot("#placeholder", res[test_id]["data"], options);	
				$("table").css({
								top : "-22px",
								left: "190px",
								"font-size" : "20px"
						   });
				$(".legend div").css( {display : "none"} );
				
				var i =  res[test_id]["time"].length - 1;
				value =  res[test_id]["data"][0]["data"][i][1],
				time  =  res[test_id]["time"][i];
				$("#tooltip").html("Last test result " + value + "<br>Time " + time).css({top: "260px", left: "910px"}).fadeIn(100);
				
				
				//reset x-axis ticker labels
				var first = $("#placeholder .flot-tick-label").first();
				var leftPos = parseInt(first.css("left")) - 20 + "px";
				first.html( parsePHPTimeString( res[test_id]["time"][0] ) ).css( {left: leftPos} );
				for(i=1; i<res[test_id]["time"].length; i++ ) {
					first = first.next();
					leftPos = parseInt(first.css("left")) - 25 + "px";
					first.html( parsePHPTimeString( res[test_id]["time"][i] ) ).css( {left: leftPos} );
				}			
			}
			
			function parsePHPTimeString(time_string) {
				var res = time_string.slice(0,10);
				res = res.split("-");
				return res[2] + "/" + res[1] + "/" + res[0];
			}
							
		} // end of plot_ajax()
		
	});

	</script>

</head>


<body style="background:url('images/background.jpg'); width:950px; margin:0 auto;">


<div id="title" style="height:115px;width:920px;margin:10px auto">
	<!-- 	<p class="float_left title_text" >Previous Results</p> -->
	<p style="display:inline-block;float:left;width=:50%;font-size:45px;margin-top:30px">Previous Results</p>
	
	<!-- 	<div class="float_right title_icon"> -->
	<div style="display:inline-block;float:right;width:250px">
		<a href="home.html"><img src="images/Home.png"></a>
		<a href="help_previous.php"><img src="images/questionmark.png"></a>
	</div>
</div>

<div id="content" style="width:750px; margin-left: 50px">

	<div style="display:inline-block;width:150px;float:left">
	<div id="plot_nav" style="width:100%; margin:10px 0 110px 0;">
		<label class="tab" id="blood_pressure_test">Blood pressure</label>
		<label class="tab" id="weight">Weight</label>
		<label class="tab" id="3">Test C</label>
		<label class="tab" id="4">Test D</label>
		<label class="tab" id="5">Test E</label>
		<label class="tab" id="6">Test F</label>
		<label class="tab" id="7">Test G</label>
	</div>
	
	<a class="button" id="print" href="#">Print</a>
	<a class="button" id="send" href="#">Email</a>
	</div>
	
		
		
<!-- 		<button style="display: block; width: 130px; margin: 20px auto; font-size: 20px">
			<img src="images/mail.png">Send -->
<!-- 		</button> -->
	
	<div id="plot_container">
		<div id="placeholder"></div>
	</div>
	
</div>

<div id="hint" style="width:750px; margin: 0px auto; text-align: center">
	<p style="font-size:20px">Hint: you can hover on the data points to see the detailed test results.</p>	
</div>
	
</body>

</html>