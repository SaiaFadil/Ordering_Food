package com.tif22.orderingfood.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tif22.orderingfood.data.model.ModelDetailMenu
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.data.model.UsersModel

data class ResponseDetailMenu(
    @Expose
    @SerializedName("status")
    val status : String,
    @Expose
    @SerializedName("message")
    val message : String,
    @Expose
    @SerializedName("data")
    val data: ModelDetailMenu


)
