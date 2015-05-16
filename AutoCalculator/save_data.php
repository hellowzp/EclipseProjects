
<?php
		
	$data = urldecode($_POST['data']);
	echo $_POST['data'];
	
	$file_path = "data.csv";
	if(!file_exists($file_path) ) {
		file_put_contents($file_path, "Customer, Product, Price, Currency, Quantity, Date, Phone, Email\r\n");
	}
	file_put_contents($file_path, $data, FILE_APPEND | LOCK_EX);
		
?>