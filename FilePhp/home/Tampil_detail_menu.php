<?php
require('../connection/connect.php');

$id_menu = $_POST['id_menu'];
 
$sql = "SELECT * FROM menu AS m JOIN restoran AS r ON r.id_restoran = m.id_restoran WHERE m.id_menu = '$id_menu' LIMIT 1;";
$result = $konek->query($sql);
$data = $result->fetch_assoc();

if ($result->num_rows >= 0) {
    $response = array("status" => "success", "message" => "id_menu = ".$id_menu,"data"=>$data);
} else {
    $response = array("status" => "failed", "message" => "id_menu Tidak Ditemukan");
}

echo json_encode($response);
mysqli_close($konek);

?>
