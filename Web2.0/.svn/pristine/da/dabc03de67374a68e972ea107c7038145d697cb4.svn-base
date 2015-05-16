// JSON string format
// [{"userID":1,"year":2014,"month":10,"day":8,"hour":17,"minute":27,"area":0,"measurementUnit":"cm/kg","sex":"male","age":44,"weight":76.9,"height":178.9,"bmi":26.0,"bodyFatRate":25.3,"basalMetabolism":1633,"visceralFatLevel":9.0,"bodyAge":55,"skeletalMuscleRate":33.6}]



function BodyCompositionResult(year, month, day, hour, minute,
			       measurementUnit, sex, age,
			       weight, height, bmi, bodyFatRate,
			       basalMetabolism, visceralFatLevel, 
			       bodyAge, skeletalMuscleRate){
    this.year = year;
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
    this.measurementUnit = measurementUnit;
    this.sex = sex;
    this.age = age;
    this.weight = weight;
    this.height = height;
    this.bmi = bmi;
    this.bodyFatRate = bodyFatRate;
    this.basalMetabolism = basalMetabolism;
    this.visceralFatLevel = visceralFatLevel;
    this.bodyAge = bodyAge;
    this.skeletalMuscleRate = skeletalMuscleRate;
}

// FUNCTION TO BE CALLED
function getBodyCompositionResults()
{
    var json_string = httpGet("http://localhost:8000/BCM/download");
    var json_data = JSON.parse(json_string);
    validateBodyCompositionJSONData(json_data);
    var composition = parseBodyCompositionJSONData(json_data);
    return composition;
}

function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false);
    xmlHttp.send(null);
    return xmlHttp.responseText;
}

function validateBodyCompositionJSONData(json_data)
{
    if (!isValidBodyCompositionJSONData(json_data)){
	throw ({'msg':'invalid body composition JSON string'});
    }
}

function isValidBodyCompositionJSONData(json_data)
{
    return (json_data instanceof Array) &&
	(json_data.length == 1) &&
	hasBodyCompositionProperties(json_data)
}

function hasBodyCompositionProperties(json_data)
{
    var results = json_data[0];
    return results.hasOwnProperty('year') &&
	results.hasOwnProperty('month') &&
	results.hasOwnProperty('day') &&
	results.hasOwnProperty('hour') &&
	results.hasOwnProperty('minute') &&
	results.hasOwnProperty('second') &&
	results.hasOwnProperty('measurementUnit') &&
	results.hasOwnProperty('sex') && 
	results.hasOwnProperty('age') &&
	results.hasOwnProperty('weight') &&
	results.hasOwnProperty('height') &&
	results.hasOwnProperty('bmi') &&
	results.hasOwnProperty('bodyFatRate') &&
	results.hasOwnProperty('basalMetabolism') &&
	results.hasOwnProperty('visceralFatLevel') &&
	results.hasOwnProperty('bodyAge') &&
	results.hasOwnProperty('skeletalMuscleRate');
}

function parseBodyCompositionJSONData(json_data)
{
    var results = json_data[0];
    var composition = new BodyCompositionResult(results.year, results.month, results.day,
				      results.hour, results.minute, results.second,
				      results.unit, results.systric, results.diastric,
				      results.pulse);
    validateBodyCompositionResult(composition);
    return composition;
}

function validateBodyCompositionResult(composition)
{
    if (!isValidBodyCompositionResult(composition)){
    	throw ({'msg':'invalid blood pressure results'});
    }
}

function isValidBodyCompositionResult(composition)
{
    return (composition.year > 0) &&
	(composition.month >= 1) &&
	(composition.month <= 12) &&
	(composition.day >= 1) &&
	(composition.day <= 31) &&
	(composition.hour >= 0) &&
	(composition.hour < 24) &&
	(composition.minute >= 0) &&
	(composition.minute < 60) &&
	(composition.sex == "male" || 
	 composition.sex == "female") &&
	(composition.age > 0) &&
	(composition.weight > 0.0) &&
	(composition.height > 0.0) &&
	(composition.bmi > 0.0) &&
	(composition.bodyFatRate > 0.0) &&
	(composition.basalMetabolism > 0) &&
	(composition.visceralFatLevel > 0.0) &&
	(composition.bodyAge > 0) &&
	(composition.skeletalMuscleRate > 0.0);
}
