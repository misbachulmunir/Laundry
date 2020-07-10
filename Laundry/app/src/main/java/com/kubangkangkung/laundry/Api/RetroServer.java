package com.kubangkangkung.laundry.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseUrl="http://192.168.43.234/laundry/";
    private static Retrofit retro;

    //method koneksi retreo
    public static Retrofit konekRetrofit(){
        if(retro==null){
            retro=new Retrofit.Builder()
                    .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retro;
    }
}
