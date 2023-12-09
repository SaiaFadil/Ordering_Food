<?php
require('../connection/connect.php');

header("Content-Type: application/json");

// Menerima data dari aplikasi Android
$nama = $_POST['nama_lengkap'];
$no_telpon = $_POST['no_telpon'];
$email = $_POST['email'];
$password = $_POST['password'];

// Periksa apakah email sudah terdaftar
$cek_email = "SELECT * FROM `users` WHERE email = '$email'";
$eksekusi_cek = mysqli_query($konek, $cek_email);
$jumlah_cek = mysqli_num_rows($eksekusi_cek);

$response = array();
if ($jumlah_cek > 0) {
    $response = array("status" => "email_failed", "message" => "Email Sudah Pernah Terdaftar" );

} else {
    $passwordHash = password_hash($password, PASSWORD_BCRYPT);

    $perintah = "INSERT INTO `users` (nama_lengkap, no_telpon, email, password, role) VALUES ('$nama', '$no_telpon', '$email', '$passwordHash', 'admin')";
    $eksekusi = mysqli_query($konek, $perintah);

    if ($eksekusi) {
        $response = array("status" => "success", "message" => "Registrasi Berhasil" );
    } else {
        $response = array("status" => "failed", "message" => "Registrasi Gagal");
    }
}

echo json_encode($response);
mysqli_close($konek);
?>
