<?php
require('../connection/connect.php');

    $email = $_POST['email'];
 
    
    $sql = "SELECT kode_otp FROM verifikasi WHERE email = '$email' order by created_at desc LIMIT 1";
    $result = $konek->query($sql);
    $otp = $result->fetch_assoc();
    
    if ($result->num_rows == 1) {
        $response = array("status" => "success", "message" => "Berhasil","otp"=>$otp['kode_otp']);
           
    } else {
        $response = array("status" => "failed", "message" => "Gagal");
    }
echo json_encode($response);
mysqli_close($konek);
?>
