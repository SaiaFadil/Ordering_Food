package com.tif22.orderingfood.api.retrofit

import com.tif22.orderingfood.data.response.UsersResponse
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded;

interface RetrofitEndPoint {

    @FormUrlEncoded
    @POST("")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : retrofit2.Call<UsersResponse>

}