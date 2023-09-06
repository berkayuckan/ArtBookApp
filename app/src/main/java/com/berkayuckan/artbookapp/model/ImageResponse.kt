package com.berkayuckan.artbookapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("hits")
    val hits: List<Hit>,

    @SerializedName("total")
    val total: Int,

    @SerializedName("totalHits")
    val totalHits: Int
)