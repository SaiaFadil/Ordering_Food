package com.tif22.orderingfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyModel(

    @Expose
    @SerializedName("email")
    val email: String,

    @Expose
    @SerializedName("kode_otp")
    val otp: String,

    @Expose
    @SerializedName("link")
    val link: String,

    @Expose
    @SerializedName("deskripsi")
    val deskripsi: String,

    @Expose
    @SerializedName("created_at")
    val created_at: String,

    @Expose
    @SerializedName("updated_at")
    val updated_at: String,


    @Expose
    @SerializedName("resend")
    val resend: Int

)
