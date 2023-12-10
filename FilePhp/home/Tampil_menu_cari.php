<?php
require('../connection/connect.php');

$nama_menu = $_POST['nama_menu'];
 
$sql = "SELECT * FROM menu AS m JOIN restoran AS r ON r.id_restoran = m.id_restoran WHERE m.nama_menu LIKE '%".$nama_menu."%' ORDER BY m.harga ASC;";
$result = $konek->query($sql);
$data = $result->fetch_all(MYSQLI_ASSOC);

if ($result->num_rows >= 0) {
    $response = array("status" => "success", "message" => "nama_menu = ".$nama_menu,"data"=>$data);
} else {
    $response = array("status" => "failed", "message" => "nama_menu Tidak Ditemukan");
}

echo json_encode($response);
mysqli_close($konek);

?>
