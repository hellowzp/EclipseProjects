 <?php

include 'temp.php';
?>
<!DOCTYPE HTML>
<html lang="en">
<head>
<link rel="stylesheet" href="css/style.css">
<?php 
if (isset($_POST['RedON']))
{
     echo $_POST["RedON"]."Relay is on";
exec("python /var/www/pifaceon.py");
exec("D:/xampp/htdocs/2_on.py");

}

if (isset($_POST['RedOFF']))
{
    echo $_POST["RedON"]."Relay is off";
exec("python /var/www/pifaceoff.py");
exec("D:/xampp/htdocs/2_off.py");
}

if (isset($_POST['plugon']))
{
exec("/usr/local/bin/tdtool -n 1");
}
if (isset($_POST['plugoff']))
{
exec("/usr/local/bin/tdtool -f 1");
}

if (isset($_POST['plugon1']))
{
exec("/usr/local/bin/tdtool -n 101");
}

if (isset($_POST['plugoff1']))
{
exec("/usr/local/bin/tdtool -f 101");
}

if (isset($_POST['allon']))
{
exec("python /var/www/pifaceon.py");
exec("/usr/local/bin/tdtool -n 1");
exec("/usr/local/bin/tdtool -n 101");
}
if (isset($_POST['alloff']))
{
exec("/usr/local/bin/tdtool -f 1");
exec("/usr/local/bin/tdtool -f 101");
exec("/usr/local/bin/tdtool -f 4");
exec("python /var/www/pifaceoff.py");
}

if (isset($_POST['microon']))
{
exec("/usr/local/bin/tdtool -n 7");
}

if (isset($_POST['microoff']))
{
exec("/usr/local/bin/tdtool -f 7");
}

if (isset($_POST['livingroom']))
{
exec("/usr/local/bin/tdtool -n 4");
}

if (isset($_POST['livingroom']))
{
exec("/usr/local/bin/tdtool -f 4");
}




?>

  <title>Tell Stick Plug Control</title>
</head>
<body>
<h1 style="text-align: center;">GroupT Headquarter</h1>

<div class="container">
    <div class="de">
        <div class="den">
          <div class="dene">
            <div class="denem">
              <div class="deneme">
            <span><? echo $temp; ?></span><strong>&deg;c</strong>
              </div>
            </div>
          </div>
        </div>
    </div>
</div>


<p>Select the device you wish to turn on and off?</p>
<br>
<?
//$ipaddress = $_SERVER["SERVER_NAME"];
//echo $ipaddress;
?>
<form method="post">
  <table
 style="width: 75%; text-align: left; margin-left: auto; margin-right: auto;"
 border="0" cellpadding="2" cellspacing="2">
    <body>

</div>


<div height: 50%;>
      <tr>
        <td style="text-align: center;"><b>Device On</b></td>
        <td style="text-align: center;"><b>Device Off</b></td>
    
      </tr>
      <tr>
          <td style="text-align: center;"><button class="button" name="RedON" type="submit">Relay On</button></td>
        <td style="text-align: center;"><button class="button" name="RedOFF" type="submit">Relay Off</button></td>
<tr/>
<tr>
        <td style="text-align: center;"><button class="button" name="livingroom1" type="submit">Youtube On</button></td>
        <td style="text-align: center;"><button class="button" name="livingroom1" type="submit">Youtube Off</button></td>
      </tr>
    </body>
  </table>
</form>

<p>

<?

?>
</p>
</div>

</body>
</html>