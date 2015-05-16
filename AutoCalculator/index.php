<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<title>L-POST_XL</title>

<script src="jquery-1.11.1.min.js"></script>
<link rel="stylesheet" href="jquery.datepicker.css">
<script type="text/javascript" src="jquery.datepicker.min.js"></script>

<script>
function fillProduct() {
	var product = document.getElementById("product");
	var opt = product.value;
	if(opt == "Others" ) {
		opt = prompt("Please enter the product name");
		if(opt != null) {
			var option = document.createElement("option");
		    option.text = opt;
		    product.add(option,product[product.selectedIndex]);
			product.value = opt;
		    //$("#product").val(opt);
		}
	}
}

function calculate() {
	var customer =  $("#name").val();
	if(customer.length<1) {
		alert("The customer name cannot be empty!");
		return;
	}
    var product = $("#product").val();
	if(product == "Others") {
		alert("Please choose a valid product.");
		return;
	}
	var price = parseFloat( $("#price").val() );
	if( isNaN(price) || price<0 ) {
		alert("Invalid price");
		return;
	}
	var quantity = parseInt( $("#quantity").val() );
	if( isNaN(quantity) || quantity<1) {
		alert("Invalid quantity");
		return;
	} 

	var phone = $("#phone").val();
	phone = phone.length<1 ? "unknown" : phone;
	var email = $("#email").val();
	email = email.length<1 ? "unknown" : email;

	var result = customer + ", " + product+ ", "
			   + price + ", " + $("#currency").val() + ", "
			   + quantity + ", " + $("#date").val() + ","
			   + phone + "," + email + "\r\n";
	
	$.ajax({
		type: 'post',
		cache: false,
		url: 'save_data.php', 
		data: {
			data: encodeURIComponent(result),
		},
		success: function(data) { 
			alert("Data has been saved successfully!");
			console.log(data);
		}
	});
}

$(document).ready(function() {
	//$.datePicker.show();
	//$("table").css("borderSpacing","20px");
});

</script>

<style>

body {
	font-size: 22px;
	font-weight:normal;
	color:#1EA02E;
	letter-spacing:0pt;
	word-spacing:2pt;
	font-family:arial, helvetica, sans-serif;
	line-height:1;
}

td {
	colspan: 10px;
}

button {
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	font-family: Arial;
	color: #1EA02E;
	font-size: 18px;
	background: #EDF1F1;
	padding: 2px 3px 2px 3px;
	text-decoration: none;
	margin: 8px 0px;
	width: 100px;
	text-align: center;
}

button:hover {
	font-size: 20px;
	background: #A4CDE7;
}

p {
	font-weight:normal;
	color:#0A0A0A;
	letter-spacing:0pt;
	word-spacing:2pt;
	font-size:24px;
	text-align:left;
	font-family:arial, helvetica, sans-serif;
	line-height:1;
	background: honeydew;
}

form {
	width: 300px;
	margin: 100px auto;
}

</style>

</head>

<body>


<form action="save_data.php" method="get" autocomplete="on">

<fieldset>
<legend style="font-size: 22px;">Order information</legend>

<table>
<tr>
	<td>Customer</td>
	<td><input type="text" id="name" autofocus="autofocus"></td>
</tr>

<tr>
<td>Phone</td>
<td><input type="text" id="phone">
</td>
</tr>

<tr>
<td>Email</td>
<td><input type="text" id="email">
</td>
</tr>

<tr>
<td>Product</td>
<td><select id="product" style="width: 155px" onchange="fillProduct()">
<option>Apple</option>
<option>Laptop</option>
<option>Others</option>
</select></td>
</tr>

<tr>
<td>Unit Price</td>
<td><div style="display: inline-table;">
		<input type="text" id="price" style="width: 85px;"> 
		<select id="currency">
		<option>Euro</option>
		<option>Dollar</option>
		<option>RMB</option>
	</select></div>
</td> 
</tr>

<tr>
<td>Quantity</td>
<td><input type="number" id="quantity" min="1" value="1">
</td>
</tr>

<tr> 
<td>Date</td>
<td><input type="text" id="date" value=<?php echo json_encode(date("Y-m-d H:i:s"))?>></td>
</tr>

<tr>
	<td><Button type="button" onclick="calculate()">Calculate</Button></td>
	<td><Button type="button" onclick="save()" style="margin-left: 20px">Save</Button>
</td>
</tr>

</table>

</fieldset>

</form>

</body>

</html>
