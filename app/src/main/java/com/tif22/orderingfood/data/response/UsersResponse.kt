package com.tif22.orderingfood.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tif22.orderingfood.data.model.UsersModel

data class UsersResponse(
    @Expose
    @SerializedName("status")
    val status : String,
    @Expose
    @SerializedName("message")
    val message : String,
    @Expose
    @SerializedName("data")
    val data : UsersModel
)
