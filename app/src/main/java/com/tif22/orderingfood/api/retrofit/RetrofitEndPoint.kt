package com.tif22.orderingfood.api.retrofit

import com.tif22.orderingfood.data.response.ResponseDetailMenu
import com.tif22.orderingfood.data.response.ResponseMenuCari
import com.tif22.orderingfood.data.response.ResponseMenuHome
import com.tif22.orderingfood.data.response.ResponsePoster
import com.tif22.orderingfood.data.response.ResponseResetOtp
import com.tif22.orderingfood.data.response.ResponseWithoutData
import com.tif22.orderingfood.data.response.UsersResponse
import com.tif22.orderingfood.data.response.VerifyResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET

interface RetrofitEndPoint {

    @FormUrlEncoded
    @POST("account/Login.php")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : retrofit2.Call<UsersResponse>


    @FormUrlEncoded
    @POST("account/GantiPassword.php")
    fun updatePassword(
        @Field("email") email : String,
        @Field("password_baru") password_baru : String
    ) : retrofit2.Call<ResponseWithoutData>


  @FormUrlEncoded
    @POST("account/Register.php")
    fun register(
        @Field("nama_lengkap") nama_lengkap : String,
        @Field("no_telpon") no_telpon : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : retrofit2.Call<ResponseWithoutData>


  @FormUrlEncoded
    @POST("account/cekemail.php")
    fun cekemail(
        @Field("email") email : String
        ) : retrofit2.Call<ResponseWithoutData>


  @FormUrlEncoded
    @POST("account/GetOtp.php")
    fun ResetOtp(
        @Field("email") email : String
        ) : Call<ResponseResetOtp>


  @FormUrlEncoded
    @POST("account/mail.php")
    fun mail(
        @Field("email") email : String,
        @Field("type") type : String,
        @Field("action") action : String,
        @Field("id_user") id_user : String
    ) :Call<VerifyResponse>

    @FormUrlEncoded
    @POST("home/Tampil_menu_beranda.php")
    fun MenuHome(
        @Field("kategori") kategori : String
    ) : Call<ResponseMenuHome>


    @FormUrlEncoded
    @POST("home/Tampil_detail_menu.php")
    fun TampilDetailMenu(
        @Field("id_menu") id_menu : String
    ) : Call<ResponseDetailMenu>


    @FormUrlEncoded
    @POST("home/Tampil_menu_cari.php")
    fun TampilMenuCari(
        @Field("nama_menu") nama_menu : String
    ) : Call<ResponseMenuCari>

    @GET("home/getPoster.php")
    fun getPoster(): Call<ResponsePoster>


}