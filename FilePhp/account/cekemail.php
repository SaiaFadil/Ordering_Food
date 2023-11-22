<?php
require('../connection/connect.php');

    $email = $_POST['email'];
 
    
    $sql = "SELECT * FROM users WHERE email = '$email' LIMIT 1";
    $result = $konek->query($sql);
 
    if ($result->num_rows == 1) {
        $response = array("status" => "ada", "message" => "Email Sudah Terdaftar");
           
    } else {
        $response = array("status" => "tidak", "message" => "Lanjut");
    }
echo json_encode($response);
mysqli_close($konek);
?>
