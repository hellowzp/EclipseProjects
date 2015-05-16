<?php
session_start();
if(isset($_POST['loginid'])) {
	require_once 'medoo.php';
	$db = new medoo();
	$result = $db->select("users", array("id", "firstname", "lastname", "balance"), array("id"=>$_POST['loginid']));
	$_SESSION["id"] = intval($result[0]["id"]);
	$_SESSION["firstname"] = $result[0]["firstname"];
	$_SESSION["lastname"] = $result[0]["lastname"];
	$_SESSION["balance"] = doubleval($result[0]["balance"]);
	unset($_POST['loginid']);
}

if(isset($_POST['language'])) {
	$_SESSION['language'] = $_POST['language'];
	unset($_POST['language']);
	echo "TEST";
}

if(isset($_POST['test'])) {
	$_SESSION['test'] = intval($_POST['test']) + 1;
	unset($_POST['test']);
}

if(isset($_POST["recharge"])) {
	$_SESSION["recharge"] = intval($_POST["recharge"]);
	unset($_POST["recharge"]);
}

if(isset($_SESSION['recharge']) && isset($_POST['confirmRecharge']) && $_POST['confirmRecharge'] == "yes") {
	increaseBalance($_SESSION["recharge"]);
	unset($_SESSION["recharge"]);
	unset($_GET["confirmRecharge"]);
}

if(isset($_POST['pay'])) {
	decreaseBalance($_POST["pay"]);
	unset($_POST["pay"]);
}

if(isset($_POST['date']) && isset($_POST['type']) && isset($_POST['data'])) {
	require_once 'medoo.php';
	$db = new medoo();
	$db->insert("measurements", array("profileid" => $_SESSION['id'], "date" => $_POST['date'], "type" => $_POST['type'], "data" => $_POST['data']));
}

function increaseBalance($amount) {
	if(isset($_SESSION["balance"])) {
		require_once 'medoo.php';
		$_SESSION["balance"] += $amount;
		$db = new medoo();
		$db->update("users", array("balance[+]"=>$amount), array("id"=>$_SESSION["id"]));
	}
}

function decreaseBalance($amount) {
	if(isset($_SESSION["balance"])) {
		require_once 'medoo.php';
		$_SESSION["balance"] -= $amount;
		$db = new medoo();
		$db->update("users", array("balance[-]"=>$amount), array("id"=>$_SESSION["id"]));
	}
}?>