<?php
session_start();
require_once 'medoo.php';
$db = new medoo();
$pageTitle = "";

function getString($id) {
	if(isset($id)) {
		global $db;
		$result = $db->select("translations", array("value"), array("AND"=>array("stringid"=>$id, "language"=>$_SESSION['language'])));
		$result = $result[0]['value'];
		return $result;
	}
}

function getMeasurements() {
	if(isset($_SESSION["id"])) {
		global $db;
		echo json_encode($db->select("measurements", array("date", "type", "data"), array("profileid"=>$_SESSION["id"])));
	}
}

function getBalance() {
	if(isset($_SESSION["balance"])) {
		return $_SESSION["balance"];
	} else {
		return false;
	}
}

function getDataTypes() {
	global $db;
	$result = $db->select("datatypes", array("[>]translations"=>array("name"=>"stringid")), array("translations.value(name)", "datatypes.datafields", "datatypes.unit", "datatypes.seriesnames", "datatypes.icon"), array("translations.language"=>$_SESSION['language']));
	echo json_encode($result);
}

function getTests() {
	global $db;
	$result = $db->select("tests",array("[>]translations"=>array("device"=>"stringid")), array("tests.id", "translations.value(device)", "tests.price", "tests.duration", "tests.icon"), array("translations.language"=>$_SESSION['language']));
	return $result;
}

function getTest() {
	if(isset($_SESSION['test'])) {
		global $db;
		$result = $db->select("tests", array("[>]translations (devicename)"=>array("device"=>"stringid"), "[>]translations (instruction)"=>array("instructions"=>"stringid")), array("tests.id", "devicename.value(device)", "tests.price", "tests.duration", "instruction.value(instructions)", "tests.pictures", "tests.icon"), array("AND"=>array("tests.id"=>$_SESSION['test'], "devicename.language"=>$_SESSION['language'], "instruction.language"=>$_SESSION['language'])));
		return $result[0];
	}
}

function saveOriginPage() {
	$last = explode("/", $_SERVER['HTTP_REFERER']);
	$last = $last[sizeof($last) - 1];
	if($last != "paymentMethod.php") {
		$_SESSION['reference'] = $last;
	}
}

function getOriginPage() {
	if(isset($_SESSION['reference'])) {
		return $_SESSION['reference'];
	} else {
		return "home.php";
	}
}

function addHTMLHeader($title, $extra_includes) {
	global $pageTitle;
	$pageTitle = $title;
	$links = array("less/header&content.less", "less/grid.less", "less/overlay.less", "less/buttons.less");
	$scripts = array("js/less.js", "js/playSound.js", "js/jquery.js", "js/overlay.js");
	foreach ($extra_includes as $include) {
		if (strpos($include, ".less") !== false || strpos($include, ".css") !== false) {array_push($links, $include);}
		else if (strpos($include, ".js") !== false) {array_push($scripts, $include);}
	}
	$header = '<meta charset="UTF-8">';
	foreach ($links as $link) {
		$header .= getLink($link);
	}
	$header .= '<script type="text/javascript">var balance = ' . 50 . ';</script>';
	foreach ($scripts as $script) {
		$header .= getScript($script);
	}
	$header .= '<title>' . $title . '</title>';
	echo $header;
}

function addPageHeader($homeVisible, $helpVisible) {
	global $pageTitle;
	echo (
			'<div class="row" style="height: 110px;" id="header">' . 
				'<div class="column grid_1"></div>' . 
				'<div class="column grid_8">' . $pageTitle . '</div>' . 
				'<div class="column grid_1">' . ($homeVisible ? '<img src="images/Home.png" class="clickImage" onclick="goTo(\'home.php\')">' : '') . '</div>' . 
				'<div class="column grid_1">' . ($helpVisible ? '<a class="overlay-trigger" data-overlay="overlay-help"><img src="images/questionmark.png" class="clickImage"></a>' : '') . '</div>' . 
			'</div>' . 
			'<div class="row" style="height: 2px;">' . 
				'<div class="column grid_1"></div>' . 
				'<div class="column grid_10" style="background-color: #404040;"></div>' . 
			'</div>' . 
			'<div class="row balanceHolder">' . 
				'<span class="grid_11 balance">' . 
					(getBalance() !== false ? getString(61) . ': &#8364; ' . getBalance() : "") . 
				'</div>' . 
			'</div>');
}

function addHelp($helpstring) {
	$help = explode("|", $helpstring);
	$htmlhelp = 
			'<div id="overlay-help" class="overlay">' . 
				'<div class="overlay-inner">' . 
					'<h3>' . $help[0] . '</h3>' . 
					'<img alt="Close" src="images/cross.png">';
	for($i = 0; $i < (sizeof($help) - 1) / 2; $i++) {
		$htmlhelp .= '<h4>' . $help[$i * 2 + 1] . '</h4><p>' . $help[$i * 2 + 2] . '</p>';
	}
	$htmlhelp .= '</div></div>';
	echo $htmlhelp;
}

function getScript($scriptFile) {
	return '<script type="text/javascript" src="' . $scriptFile . '"></script>';
}

function getLink($linkFile) {
	if(strpos($linkFile, ".less") !== false) {
		return '<link rel="stylesheet/less" type="text/css" href="' . $linkFile . '" media="screen" />';
	} else {
		return '<link rel="stylesheet" type="text/css" href="' . $linkFile . '" media="screen" />';
	}
}?>