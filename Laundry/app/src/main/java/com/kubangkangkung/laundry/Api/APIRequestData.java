package com.kubangkangkung.laundry.Api;

import com.kubangkangkung.laundry.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    //untuk ambil data
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    //untuk post data
    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel>ardCreateData(
            @Field("nama")String nama,
            @Field("alamat")String alamat,
            @Field("telepon")String telepon
    );
}
