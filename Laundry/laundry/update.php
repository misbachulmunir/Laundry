<?php
require("koneksi.php");

$response=array();

//jika ada yang idikirim
if($_SERVER["REQUEST_METHOD"]=="POST"){
//tangkap data
$id=$_POST["id"];
$nama=$_POST["nama"];
$alamat=$_POST["alamat"];
$telepon=$_POST["telepon"];


//masukan data
$perintah = "update tbl_laundry set nama='$nama',alamat='$alamat',telepon='$telepon' where id='$id'";
$eksekusi=mysqli_query($konek,$perintah);
$cek=mysqli_affected_rows($konek);
//jika cek berhasil
if($cek>0){
	$response["kode"]=1;
	$response["pesan"]="Data Berhasil Di Update";
}
else{
$response["kode"]=0;
	$response["pesan"]="Gagal di Simpan";
}


}

//jika tidak ada
else{
	$response["kode"]=0;
	$response["pesan"]="Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($konek);
