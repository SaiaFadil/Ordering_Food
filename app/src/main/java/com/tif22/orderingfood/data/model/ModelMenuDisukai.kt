package com.tif22.orderingfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelMenuDisukai(
    @Expose
    @SerializedName("id_menu")
    val id_menu : String,
    @Expose
    @SerializedName("id_restoran")
    val id_restoran : String,
    @Expose
    @SerializedName("id_poster")
    val id_poster : String,
    @Expose
    @SerializedName("kategori")
    val kategori : String,
    @Expose
    @SerializedName("nama_menu")
    val nama_menu : String,
    @Expose
    @SerializedName("harga")
    val harga : String,
    @Expose
    @SerializedName("gambar_menu")
    val gambar_menu : String,
    @Expose
    @SerializedName("nama_restoran")
    val nama_restoran: String?,
    @Expose
    @SerializedName("alamat")
    val alamat: String?,
    @Expose
    @SerializedName("level")
    val level: String?
)
