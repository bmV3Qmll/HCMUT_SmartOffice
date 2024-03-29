<?php

$hostname = "localhost";
$username = "root";
$password = "";
$dbname = "lab";

$handle = mysqli_connect($hostname, $username, $password);
mysqli_select_db($handle, $dbname);

$records = mysqli_query($handle, "SELECT * FROM RollCall ORDER BY time;");
while ($row = mysqli_fetch_assoc($records)) {
	$result[] = $row
}
print(json_encode($result))
mysql_close($handle)
?>
