package com.tif22.orderingfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsersModel(
  @Expose
  @SerializedName("id_user")
  val idUser : Int
);