package com.camp.ioasys.data.database

import androidx.room.TypeConverter
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.models.Company
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CompaniesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun companyToString(company: CompaniesResponse): String {
        return gson.toJson(company)
    }

    @TypeConverter
    fun stringToCompany(data: String): CompaniesResponse {
        val listType = object : TypeToken<CompaniesResponse>() {}.type
        return gson.fromJson(data, listType)
    }

}