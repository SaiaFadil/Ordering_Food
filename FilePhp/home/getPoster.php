<?php
require('../connection/connect.php');
 
$sql = "SELECT poster FROM poster_promo;";
$result = $konek->query($sql);
$data = $result->fetch_all(MYSQLI_ASSOC);

if ($result->num_rows > 0) {
    $response = array("status" => "success", "message" => "Data Tersedia", "data" => $data);
} else {
    $response = array("status" => "failed", "message" => "Data Tidak Tersedia");
}

// Mengubah output JSON
$json_response = json_encode($response);
$json_response = str_replace('\\', '', $json_response);

header('Content-Type: application/json');
echo $json_response;
mysqli_close($konek);
?>
