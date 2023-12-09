package com.tif22.orderingfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsersModel(
    @Expose
    @SerializedName("id_user")
    val id_user: Int,
    @Expose
    @SerializedName("nama_lengkap")
    val nama_lengkap: String,
    @Expose
    @SerializedName("no_telpon")
    val no_telpon: String,
    @Expose
    @SerializedName("jenis_kelamin")
    val jenis_kelamin: String,
    @Expose
    @SerializedName("tanggal_lahir")
    val tanggal_lahir: String,
    @Expose
    @SerializedName("tempat_lahir")
    val tempat_lahir: String,
    @Expose
    @SerializedName("role")
    val role: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("alamat")
    val alamat: String


);