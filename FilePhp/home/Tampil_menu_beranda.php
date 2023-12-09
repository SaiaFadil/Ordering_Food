<?php
require('../connection/connect.php');

$kategori = $_POST['kategori'];

$sql = "SELECT m.*, r.*, fm.id_user AS fav_user_id 
        FROM menu AS m 
        JOIN restoran AS r ON r.id_restoran = m.id_restoran 
        LEFT JOIN favorite_menus AS fm ON m.id_menu = fm.id_menu 
        WHERE m.kategori = '$kategori' 
        ORDER BY m.harga ASC ;";
$result = $konek->query($sql);
$data = $result->fetch_all(MYSQLI_ASSOC);

if ($result->num_rows >= 0) {
    $response = array("status" => "success", "message" => "Kategori = " . $kategori, "data" => $data);
} else {
    $response = array("status" => "failed", "message" => "Kategori Tidak Ditemukan");
}

echo json_encode($response);
mysqli_close($konek);
?>
