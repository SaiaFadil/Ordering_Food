package com.tif22.orderingfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelPoster(
    @Expose
    @SerializedName("poster")
    val poster: String
)

