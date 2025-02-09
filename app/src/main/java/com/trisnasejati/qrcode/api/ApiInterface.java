package com.trisnasejati.qrcode.api;

import com.trisnasejati.qrcode.model.list.BarangResponse;
import com.trisnasejati.qrcode.model.login.Login;
import com.trisnasejati.qrcode.model.submit.Submit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("kirim.php")
    Call<Submit> submitResponse(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("kategori") String kategori,
            @Field("quantity") String quantity,
            @Field("price") String price
    );

    @GET("get_list_data.php")
    Call<BarangResponse> getBarangList();

    @POST("edit_data.php")
    @FormUrlEncoded
    Call<ApiResponse> updateBarang(@Field("id") String id, @Field("nama") String nama,
                                   @Field("kategori") String kategori, @Field("quantity") String quantity,
                                   @Field("price") String price);

    @POST("delete_data.php")
    @FormUrlEncoded
    Call<ApiResponse> deleteBarang(@Field("id") String id);

}
