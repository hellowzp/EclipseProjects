// JSON string format
// [{"userID":1,"year":2014,"month":11,"day":1,"hour":13,"minute":10,"second":0,"unit":"mmHg","systric":101,"diastric":62,"pulse":74,"status":0}]
console.log("included interfaceWithBloodpressureDevice.js");
function BloodPressureResult(year, month, day, hour, minute, second,
			     unit, systric, diastric, pulse){
    this.year = year;
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
    this.second = second;
    this.unit = unit;
    this.systric = systric;
    this.diastric = diastric;
    this.pulse = pulse;
}

// FUNCTION TO BE CALLED
function getBloodPressureResults()
{
	console.log("getBloodpressureresults()");
    var json_string = httpGet("http://localhost:8000/BPM/download");
    var json_data = JSON.parse(json_string);
    validateBloodPressureJSONData(json_data);
    var bpr = parseBloodPressureJSONData(json_data);
    return bpr;
}

function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false);
    xmlHttp.send(null);
    return xmlHttp.responseText;
}

function validateBloodPressureJSONData(json_data)
{
    if (!isValidBloodPressureJSONData(json_data)){
	throw ({'msg':'invalid blood pressure JSON string'});
    }
}

function isValidBloodPressureJSONData(json_data)
{
    return	(json_data instanceof Array) &&
	(json_data.length == 1) &&
	hasBloodPressureProperties(json_data)
}

function hasBloodPressureProperties(json_data)
{
    var results = json_data[0];
    return results.hasOwnProperty('year') &&
	results.hasOwnProperty('month') &&
	results.hasOwnProperty('day') &&
	results.hasOwnProperty('hour') &&
	results.hasOwnProperty('minute') &&
	results.hasOwnProperty('second') &&
	results.hasOwnProperty('unit') &&
	results.hasOwnProperty('systric') &&
	results.hasOwnProperty('diastric') &&
	results.hasOwnProperty('pulse')
}

function parseBloodPressureJSONData(json_data)
{
    var results = json_data[0];
    var bpr = new BloodPressureResult(results.year, results.month, results.day,
				      results.hour, results.minute, results.second,
				      results.unit, results.systric, results.diastric,
				      results.pulse);
    validateBloodPressureResult(bpr);
    return bpr;
}

function validateBloodPressureResult(bpr)
{
    if (!isValidBloodPressureResult(bpr)){
    	throw ({'msg':'invalid blood pressure results'});
    }
}

function isValidBloodPressureResult(bpr)
{
    return (bpr.year > 0) &&
	(bpr.month >= 1) &&
	(bpr.month <= 12) &&
	(bpr.day >= 1) &&
	(bpr.day <= 31) &&
	(bpr.hour >= 0) &&
	(bpr.hour < 24) &&
	(bpr.minute >= 0) &&
	(bpr.minute < 60) &&
	(bpr.second >= 0) &&
	(bpr.second < 60) &&
	(bpr.systric > 0) &&
	(bpr.diastric > 0) &&
	(bpr.pulse > 0);
}
