<?php
require('../connection/connect.php');

$id_user = $_POST['id_user'];
 
$sql = "SELECT menu.* FROM menu
JOIN favorite_menus ON menu.id_menu = favorite_menus.id_menu
WHERE favorite_menus.id_user = $id_user;";
$result = $konek->query($sql);
$data = $result->fetch_all(MYSQLI_ASSOC);

if ($result->num_rows >= 0) {
    $response = array("status" => "success", "message" => "id_user = ".$id_user,"data"=>$data);
} else {
    $response = array("status" => "failed", "message" => "id_user Tidak Ditemukan");
}

echo json_encode($response);
mysqli_close($konek);

?>
