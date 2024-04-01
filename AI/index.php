<?php
$conn = new mysqli("sql6.freesqldatabase.com", "sql6695157", "Br984vLa1L", "sql6695157");
$sql = "SELECT * FROM RollCall ORDER BY time;";
$res = $conn->query($sql);

while ($row = $res->fetch_assoc()) {
	$result[] = $row;
}
echo json_encode($result);
$conn->close();
?>
