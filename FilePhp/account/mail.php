<?php
/**
 * Digunakan untuk mengirimkan kode otp ke email user
 */

require '../vendor/autoload.php'; 
require('../connection/connect.php');
require 'EmailSender.php';

    // random kode otp
    function generateOTP($length = 6)
    {
        $otp = '';
        $characters = '0123456789';
        $charactersLength = strlen($characters);
        for ($i = 0; $i < $length; $i++) {
            $otp .= $characters[rand(0, $charactersLength - 1)];
        }
        return $otp;
    }
    
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    
        $email = $_POST['email'];
        $type = $_POST['type'];
        $action = $_POST['action'];
        $iduser = $_POST['id_user'];
        $otp = generateOTP();

        // mendapatkan data millis
        $startMillis = round(microtime(true) * 1000);
        $endMillis = $startMillis + 900000;

        // kirim otp baru
        if ($action === "new") {
            // Buat query SQL untuk memasukkan data baru
            $sql = "INSERT INTO verifikasi ( `email`, `kode_otp`, `link`, `deskripsi`, `created_at`, `update_at`, `id_user`) 
                    VALUES ('$email', '$otp', '', '$type', NOW(), '',  $iduser)";
            $result = $konek->query($sql);
        }   
        // update kode otp
        else if ($action === "update") {
            // Ambil data OTP sebelumnya
            $sql = "SELECT * FROM verifikasi WHERE email = '$email' ORDER BY created_at DESC LIMIT 1 ";
            $result = $konek->query($sql);

            // Ambil data OTP
            if ($result->num_rows == 1) {
                $data = $result->fetch_assoc();
                $oldOtp = $data['kode_otp'];
            
            }

            // cek kode otp lama berhasil didapatkan atau tidak
            if (isset($oldOtp)) {
                // Buat query SQL untuk memperbarui data
                $sql = "UPDATE verifikasi  SET kode_otp = '$otp', deskripsi = '$type',update_at = NOW()
                        WHERE email = '$email' AND kode_otp = '$oldOtp'";
                $result = $konek->query($sql);
            }
        }

        // cek apakah query berhasil dieksekusi atau tidak
        if ($result === true) {
            // Ambil data OTP setelah insert/update berhasil
            $sql = "SELECT * FROM verifikasi WHERE email = '$email' ORDER BY created_at DESC LIMIT 1";
            $result = $konek->query($sql);

            if ($result->num_rows == 1) {
                $mail = new EmailSender();
                $mail->sendEmail($email, $type, $otp);
                // Kirim kode OTP
                $response = array("status" => "success", "message" => "Kode OTP berhasil dikirimkan", "data" => $result->fetch_assoc());
            } else {
                $response = array("status" => "error", "message" => "Kode OTP gagal dikirimkan");
            }
        } else {
            $response = array("status" => "error", "message" => "Kode OTP gagal dikirimkan");
        }
        
    } else {
        $response = array("status" => "error", "message" => "method bukan post");
    }
    
    echo json_encode($response);
?>