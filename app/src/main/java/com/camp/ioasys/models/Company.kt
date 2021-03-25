package com.camp.ioasys.models

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("id")
    val id: Int,

    @SerializedName("enterprise_name")
    val enterpriseName: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("enterprise_type")
    val enterpriseType: CompanyType
)

data class CompanyType(
    @SerializedName("enterprise_type_name")
    val enterprise_type_name: String,
    @SerializedName("id")
    val id: Int
)