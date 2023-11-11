<?php
require('../connection/connect.php');

header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $email = mysqli_real_escape_string($konek, $email);

    $sql = "SELECT * FROM users WHERE email = '$email' LIMIT 1";
    $result = $konek->query($sql);

    if ($result->num_rows == 1) {
        $user = $result->fetch_assoc();

            $role_db = $user['role'];

            if ($role_db == 'admin') {
                $response = array("status" => "admin", "message" => "login admin success" ,"data" => $user);
            } else if($role_db == 'user'){
                $response = array("status" => "user", "message" => "login user success" ,"data" => $user);

            }
        } else {
            $response = array("status" => "failed", "message" => "username or password is wrong");
        }
    } else {
        $response = array("status" => "failed", "message" => "yout method is not Post");
    }
 

echo json_encode($response);
mysqli_close($konek);
?>
