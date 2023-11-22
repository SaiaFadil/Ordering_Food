package com.tif22.orderingfood.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseResetOtp(
    @Expose
    @SerializedName("status")
    val status : String,
    @Expose
    @SerializedName("message")
    val message : String,
    @Expose
    @SerializedName("otp")
    val otp : String
)
