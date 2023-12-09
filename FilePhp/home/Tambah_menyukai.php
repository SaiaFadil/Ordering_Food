<?php
require('../connection/connect.php');

header("Content-Type: application/json");

// Menerima data dari aplikasi Android
$id_user = $_POST['id_user'];
$id_menu = $_POST['id_menu'];
$response = array();

// Cek apakah entri dengan id_menu tersebut sudah ada
$check_query = "SELECT * FROM favorite_menus WHERE id_user = $id_user AND id_menu = $id_menu";
$check_result = mysqli_query($konek, $check_query);

if (mysqli_num_rows($check_result) > 0) {
    // Jika entri sudah ada, kirimkan pesan ke aplikasi Android
    $response = array("status" => "failed", "message" => "Entri sudah ada");
} else {
    // Jika entri belum ada, lakukan INSERT
    $insert_query = "INSERT INTO favorite_menus (id_user, id_menu) VALUES ($id_user, $id_menu)";
    $insert_result = mysqli_query($konek, $insert_query);

    if ($insert_result) {
        $response = array("status" => "success", "message" => "Berhasil Menambahkan");
    } else {
        $response = array("status" => "failed", "message" => "Gagal");
    }
}

echo json_encode($response);
mysqli_close($konek);
?>
