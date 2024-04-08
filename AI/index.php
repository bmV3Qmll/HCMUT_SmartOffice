<?php
$conn = new mysqli("localhost", "root", "", "project");
$sql = "SELECT * FROM RollCall ORDER BY time;";
$res = $conn->query($sql);

while ($row = $res->fetch_assoc()) {
	$result[] = $row;
}
echo json_encode($result);
$conn->close();
?>
