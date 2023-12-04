-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 04 Des 2023 pada 10.11
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ordering_food`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_menu`
--

CREATE TABLE `detail_menu` (
  `id_detail_menu` int(11) NOT NULL,
  `id_restoran` int(11) DEFAULT NULL,
  `id_menu` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `menu`
--

CREATE TABLE `menu` (
  `id_menu` int(11) NOT NULL,
  `id_restoran` int(11) NOT NULL,
  `id_poster` int(11) DEFAULT NULL,
  `kategori` enum('makanan','minuman','snack','lainnya') NOT NULL,
  `nama_menu` varchar(255) DEFAULT NULL,
  `harga` int(50) DEFAULT NULL,
  `gambar_menu` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `menu`
--

INSERT INTO `menu` (`id_menu`, `id_restoran`, `id_poster`, `kategori`, `nama_menu`, `harga`, `gambar_menu`) VALUES
(8, 19, NULL, 'makanan', 'Nasi + Paha Ayam', 14000, 'images/gambar_menu/nasi_paha_ayam_geprek_laila.jpeg'),
(9, 19, NULL, 'makanan', 'Ayam krispy', 12000, 'images/gambar_menu/ayam_krispi_laila.jpeg'),
(10, 20, NULL, 'makanan', 'Bakso Beranak', 7000, 'images/gambar_menu/bakso_beranak_cakno.jpeg'),
(11, 20, NULL, 'minuman', 'Es Oyen', 4000, 'images/gambar_menu/es_oyen_cakno.jpg'),
(12, 19, NULL, 'snack', 'Kentang Goreng', 10000, 'images/gambar_menu/kentang_goreng_laila.jpg'),
(13, 19, NULL, 'snack', 'Pisang Coklat', 7000, 'images/gambar_menu/pisang_coklat_laila.jpeg'),
(14, 21, NULL, 'makanan', 'Ayam Bakar', 8000, 'images/gambar_menu/ayam_bakar_setyo.jpeg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `order`
--

CREATE TABLE `order` (
  `id_order` int(11) NOT NULL,
  `id_restoran` int(11) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `waktu_pemesanan` datetime NOT NULL DEFAULT current_timestamp(),
  `status` enum('dipesan','diproses','diantar','selesai') NOT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `poster_promo`
--

CREATE TABLE `poster_promo` (
  `id_poster` int(11) NOT NULL,
  `poster` text NOT NULL,
  `tanggal_dibuat` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `poster_promo`
--

INSERT INTO `poster_promo` (`id_poster`, `poster`, `tanggal_dibuat`) VALUES
(5, 'images/poster_promo/promo1.jpg', '2023-11-28'),
(7, 'images/poster_promo/promo2.jpg', '2023-11-28');

-- --------------------------------------------------------

--
-- Struktur dari tabel `restoran`
--

CREATE TABLE `restoran` (
  `id_restoran` int(11) NOT NULL,
  `nama_restoran` varchar(255) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `level` enum('Restoran','Angkringan') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `restoran`
--

INSERT INTO `restoran` (`id_restoran`, `nama_restoran`, `alamat`, `level`) VALUES
(19, 'Ayam Geprek Layla', 'Jl. A.R. Saleh No.70, Kauman, Kec. Nganjuk, Kabupaten Nganjuk, Jawa Timur 64411', 'Restoran'),
(20, 'Cak No Bakso', 'Pokok e nganjuk,Jawatimur,Indonesia', 'Angkringan'),
(21, 'Angkringan Setyo', 'Jl.Letjend Suprapto 1B', 'Angkringan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  `no_telpon` varchar(15) NOT NULL,
  `jenis_kelamin` enum('laki-laki','perempuan') NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `tempat_lahir` varchar(45) NOT NULL,
  `role` enum('admin','user') NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(191) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id_user`, `nama_lengkap`, `no_telpon`, `jenis_kelamin`, `tanggal_lahir`, `tempat_lahir`, `role`, `email`, `password`, `alamat`) VALUES
(1, 'akun pemulihan', '089999999999', 'laki-laki', '2023-11-01', 'Surga', 'admin', 'akunceer1satu@gmail.com', '1234567890poiuytrewqasdfghjkllkmnbvcxzz', ''),
(15, 'Fadillah wahyu', '083671662618', 'laki-laki', '2004-02-27', 'Nganjuk', 'admin', 'Fadillahwahyunugraha@gmail.com', '$2y$10$gmaD4WlI2fNc8Sr0ltqwNO5fW4SJuiiUudMNueVJOaTi0rMHhmeOK', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `verifikasi`
--

CREATE TABLE `verifikasi` (
  `id_verifikasi` int(10) NOT NULL,
  `email` varchar(45) NOT NULL,
  `kode_otp` varchar(6) NOT NULL,
  `link` varchar(50) NOT NULL,
  `deskripsi` enum('email','password') DEFAULT NULL,
  `send` int(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  `resend` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `verifikasi`
--

INSERT INTO `verifikasi` (`id_verifikasi`, `email`, `kode_otp`, `link`, `deskripsi`, `send`, `created_at`, `update_at`, `id_user`, `resend`) VALUES
(38, 'fadillahwahyunugraha@gmail.com', '471894', '', '', NULL, '2023-11-22 02:08:37', '2023-11-22 02:11:41', 1, NULL),
(39, 'fadillahwahyunugraha@gmail.com', '795108', '', '', NULL, '2023-11-22 02:15:25', '2023-11-22 02:26:30', 1, NULL),
(40, 'fadillahwahyunugraha@gmail.com', '544615', '', '', NULL, '2023-11-22 03:01:47', '0000-00-00 00:00:00', 1, NULL),
(41, 'fadillahwahyunugraha@gmail.com', '084935', '', '', NULL, '2023-11-22 03:14:38', '2023-11-22 03:15:00', 1, NULL),
(42, 'fadillahwahyunugraha@gmail.com', '965846', '', '', NULL, '2023-11-22 03:52:17', '2023-11-22 03:55:46', 1, NULL),
(43, 'fadillahwahyunugraha@gmail.com', '584753', '', '', NULL, '2023-11-22 04:55:27', '0000-00-00 00:00:00', 1, NULL),
(44, 'fadillahwahyunugraha@gmail.com', '652610', '', '', NULL, '2023-11-27 16:45:10', '0000-00-00 00:00:00', 1, NULL),
(45, 'fadillahwahyunugraha@gmail.com', '355323', '', '', NULL, '2023-11-30 11:24:10', '0000-00-00 00:00:00', 1, NULL),
(46, 'fadillahwahyunugraha@gmail.com', '481393', '', '', NULL, '2023-11-30 11:24:25', '0000-00-00 00:00:00', 1, NULL),
(47, 'fadillahwahyunugraha@gmail.com', '336623', '', '', NULL, '2023-11-30 11:24:58', '0000-00-00 00:00:00', 1, NULL),
(48, 'fadillahwahyunugraha@gmail.com', '293197', '', '', NULL, '2023-11-30 11:24:58', '0000-00-00 00:00:00', 1, NULL);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `detail_menu`
--
ALTER TABLE `detail_menu`
  ADD PRIMARY KEY (`id_detail_menu`),
  ADD KEY `id_restoran` (`id_restoran`),
  ADD KEY `id_menu` (`id_menu`);

--
-- Indeks untuk tabel `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`),
  ADD KEY `id_restoran` (`id_restoran`),
  ADD KEY `id_poster` (`id_poster`);

--
-- Indeks untuk tabel `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id_order`),
  ADD UNIQUE KEY `id_user` (`id_user`),
  ADD KEY `id_restoran` (`id_restoran`),
  ADD KEY `id_menu` (`id_menu`);

--
-- Indeks untuk tabel `poster_promo`
--
ALTER TABLE `poster_promo`
  ADD PRIMARY KEY (`id_poster`);

--
-- Indeks untuk tabel `restoran`
--
ALTER TABLE `restoran`
  ADD PRIMARY KEY (`id_restoran`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- Indeks untuk tabel `verifikasi`
--
ALTER TABLE `verifikasi`
  ADD PRIMARY KEY (`id_verifikasi`),
  ADD KEY `id_user` (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_menu`
--
ALTER TABLE `detail_menu`
  MODIFY `id_detail_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `menu`
--
ALTER TABLE `menu`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `order`
--
ALTER TABLE `order`
  MODIFY `id_order` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `poster_promo`
--
ALTER TABLE `poster_promo`
  MODIFY `id_poster` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `restoran`
--
ALTER TABLE `restoran`
  MODIFY `id_restoran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT untuk tabel `verifikasi`
--
ALTER TABLE `verifikasi`
  MODIFY `id_verifikasi` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_menu`
--
ALTER TABLE `detail_menu`
  ADD CONSTRAINT `detail_menu_ibfk_1` FOREIGN KEY (`id_restoran`) REFERENCES `restoran` (`id_restoran`),
  ADD CONSTRAINT `detail_menu_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`);

--
-- Ketidakleluasaan untuk tabel `menu`
--
ALTER TABLE `menu`
  ADD CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`id_restoran`) REFERENCES `restoran` (`id_restoran`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`id_restoran`) REFERENCES `restoran` (`id_restoran`),
  ADD CONSTRAINT `order_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`);

--
-- Ketidakleluasaan untuk tabel `verifikasi`
--
ALTER TABLE `verifikasi`
  ADD CONSTRAINT `verifikasi_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
