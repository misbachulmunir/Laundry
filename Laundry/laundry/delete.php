<?php
require("koneksi.php");

$response=array();

//jika ada yang idikirim
if($_SERVER["REQUEST_METHOD"]=="POST"){
//tangkap data
$id=$_POST["id"];


//masukan data
$perintah = "delete from tbl_laundry where id='$id'";
$eksekusi=mysqli_query($konek,$perintah);
$cek=mysqli_affected_rows($konek);
//jika cek berhasil
if($cek>0){
	$response["kode"]=1;
	$response["pesan"]="Data Berhasil Dihapus";
}
else{
$response["kode"]=0;
	$response["pesan"]="Gagal Menghapus Data";
}


}

//jika tidak ada
else{
	$response["kode"]=0;
	$response["pesan"]="Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($konek);
