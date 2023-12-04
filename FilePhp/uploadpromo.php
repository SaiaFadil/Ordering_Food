<?php
require('connection/connect.php');

$poster = $_FILES['poster'];
$uploadDirPoster = 'images/poster_promo/';

$posterFileName = $uploadDirPoster . generateUniqueFileName3($poster['name'], $uploadDirPoster);
move_uploaded_file($poster['tmp_name'], $posterFileName);

function generateUniqueFileName3($originalName, $uploadDirPoster) {
    $extension = pathinfo($originalName, PATHINFO_EXTENSION);
    $basename = pathinfo($originalName, PATHINFO_FILENAME);

    if (!file_exists($uploadDirPoster . $basename . '.' . $extension)) {
        return $basename . '.' . $extension;
    }

    $counter = 1;
    while (file_exists($uploadDirPoster . $basename . '(' . $counter . ')' . '.' . $extension)) {
        $counter++;
    }

    return $basename . '(' . $counter . ')' . '.' . $extension;
}

$today = date('Y-m-d');
$tgl_pembuatan = $today;
$tgl_berlaku = date('Y-m-d', strtotime('+7 days')); // Contoh: Tanggal berlaku ditambah 7 hari dari tanggal pembuatan

$query = "INSERT INTO poster_promo (poster, tanggal_dibuat) VALUES ('$posterFileName', '$tgl_pembuatan')";

if ($konek->query($query) === TRUE) {
    $response['status'] = 'success';
    $response['message'] = 'Data berhasil disimpan';
} else {
    $response['status'] = 'error';
    $response['message'] = 'Gagal menyimpan data: ' . $konek->error;
}

header('Content-type: application/json');
echo json_encode($response);
?>
