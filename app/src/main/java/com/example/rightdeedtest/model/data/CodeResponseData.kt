package com.example.rightdeedtest.model.data

import com.google.gson.annotations.SerializedName

data class CodeResponseData(
    @SerializedName("code")
    val code: String,
    @SerializedName("status")
    val status: String,
)