
<?php
		
//	echo '[[1999, 3.0], [2000, 3.9], [2001, 2.0], [2002, 1.2], [2003, 1.3], [2004, 2.5], [2005, 2.0], [2006, 3.1], [2007, 2.9], [2008, 0.9]]';
	
	if(isset($_GET['test'])) {
		$uID = 1;
		$connection = new PDO("mysql:host=localhost;dbname=test", "root", "");
//		$query = $connection->prepare("SELECT date, value FROM ? WHERE userID = ?");
		$qString = "SELECT date, value FROM " . $_GET['test'];
		$query = $connection->prepare($qString);
//		$query->bindParam(1, $_GET['test'], PDO::PARAM_STR, 20);
//		$query->bindParam(2, $uID);
		$query->execute();
		
		$date_array = Array();
		$value_array = Array();
		while($result = $query->fetch(PDO::FETCH_ASSOC)) {
			array_push($date_array, $result["date"] );
			array_push($value_array, $result["value"] );
		}
//		print_r($date_array);
//		print_r($value_array);
		$data = Array();
		$data["value"] = $value_array;
		$data["time"] = $date_array;
//		print_r($data);
		
		echo json_encode($data);
		
		
// 		print("\nPDO::FETCH_ASSOC: ");
// 		print("Return next row as an array indexed by column name\n");
// 		$result = $query->fetch(PDO::FETCH_ASSOC);
// 		print_r($result);
// 		print("\n");
		
// 		print("PDO::FETCH_BOTH: ");
// 		print("Return next row as an array indexed by both column name and number\n");
// 		$result = $query->fetch(PDO::FETCH_BOTH);
// 		print_r($result);
// 		print("\n");
		
// 		$result = $query->fetchAll(PDO::FETCH_ASSOC);
// 		print_r($result);
// 		print("\n");

//		file_put_contents("sql_result.txt", json_encode($data) . $_GET['test'] . "\n", FILE_APPEND | LOCK_EX );
	}
	
// 	try {
// 		$dbh = new PDO('mysql:host=localhost;dbname=test', "root", "");
// 		foreach($dbh->query('SELECT * from blood_pressure_test') as $row) {
// 			print_r($row);
// 		}
// 		$dbh = null;
// 	} catch (PDOException $e) {
// 		print "Error!: " . $e->getMessage() . "<br/>";
// 		die();
// 	}
			
?>