package com.camp.ioasys.models

import com.google.gson.annotations.SerializedName

data class CompaniesResponse(

    @SerializedName("enterprises")
    val companies: List<Company>

)