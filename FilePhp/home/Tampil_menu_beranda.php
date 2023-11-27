<?php
require('../connection/connect.php');

$kategori = $_POST['kategori'];
 
$sql = "SELECT * FROM menu AS m
        JOIN restoran AS r ON r.id_restoran = m.id_restoran
        WHERE m.kategori = '$kategori';";
$result = $konek->query($sql);
$data = $result->fetch_all(MYSQLI_ASSOC);

if ($result->num_rows >= 1) {
   
    $response = array("status" => "success", "message" => "Kategori = ".$kategori,"data"=>$data);
} else {
    $response = array("status" => "failed", "message" => "Kategori Tidak Ditemukan");
}

echo json_encode($response);
mysqli_close($konek);

?>
