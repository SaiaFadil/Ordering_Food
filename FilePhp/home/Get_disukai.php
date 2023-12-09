<?php
require('../connection/connect.php');

$id_user = $_POST['id_user'];
$id_menu = $_POST['id_menu'];
 
$sql = "SELECT menu.* FROM menu
JOIN favorite_menus ON menu.id_menu = favorite_menus.id_menu
WHERE favorite_menus.id_user = $id_user AND favorite_menus.id_menu = $id_menu;";
$result = $konek->query($sql);
$data = $result->fetch_all(MYSQLI_ASSOC);

if ($result->num_rows > 0) {
    $response = array("status" => "tersedia", "message" => "Data Tersedia");
} else {
    $response = array("status" => "tidak", "message" => "Data Tidak Ditemukan");
}

echo json_encode($response);
mysqli_close($konek);

?>
