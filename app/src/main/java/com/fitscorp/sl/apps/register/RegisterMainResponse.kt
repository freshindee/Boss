package com.fitscorp.sl.apps.register

import com.google.gson.annotations.SerializedName


data class RegisterMainResponse (

    @SerializedName("response") val response : RegisterMainResponseData
)


data class RegisterMainResponseData (

    @SerializedName("code") val code : Int,
    @SerializedName("success") val success : Boolean,
    @SerializedName("data") val data : List<String>
)