<?php
require('../connection/connect.php');

header("Content-Type: application/json");
// Menerima data dari aplikasi Android
$email = $_POST['email'];
$passwordBaru = $_POST['password_baru'];

$cek_iduser = "SELECT * FROM `users` WHERE email = '$email' LIMIT 1";
$eksekusi_cek = mysqli_query($konek, $cek_iduser);
$jumlah_cek = mysqli_num_rows($eksekusi_cek);

$response = array();

if ($jumlah_cek > 0) {
    $user = $eksekusi_cek->fetch_assoc();
    

        // Enkripsi password baru
        $passwordBaruHashed = password_hash($passwordBaru, PASSWORD_DEFAULT);

        // Update password dalam database dengan password baru yang di-hash
        $perintah = "UPDATE `users` SET password = '$passwordBaruHashed' WHERE email = '$email'";
        $eksekusi = mysqli_query($konek, $perintah);

        if ($eksekusi) {
            $response = array("status" => "success", "message" => "Berhasil Update");
        } else {
            $response = array("status" => "failed", "message" => "Gagal Update");

        }
    
} else {
    $response = array("status" => "gagal", "message" => "Gagal Update");

}

echo json_encode($response);
mysqli_close($konek);
?>
