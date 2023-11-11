<?php
$host = "localhost"; 
$username = "root"; 
$password = ""; 
$database = "ordering_food"; 

// Membuat koneksi
$konek = new mysqli($host, $username, $password, $database);

// Memeriksa koneksi
if ($konek->connect_error) {
    die("connection failed : " . $konek->connect_error);
}
?>
