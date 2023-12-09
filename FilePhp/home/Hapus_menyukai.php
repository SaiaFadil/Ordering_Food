<?php
require('../connection/connect.php');

header("Content-Type: application/json");

// Menerima data dari aplikasi Android
$id_user = $_POST['id_user'];
$id_menu = $_POST['id_menu'];
$response = array();

    $perintah = "DELETE FROM favorite_menus WHERE id_user = '$id_user' AND id_menu = '$id_menu';";
    $eksekusi = mysqli_query($konek, $perintah);

    if ($eksekusi) {
        $response = array("status" => "success", "message" => "Berhasil Menghapus");
    } else {
        $response = array("status" => "failed", "message" => "Gagal");
    }


echo json_encode($response);
mysqli_close($konek);
?>
