package com.camp.ioasys.data.database

import androidx.room.TypeConverter
import com.camp.ioasys.models.Company
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CompaniesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun companyToString(company: Company): String {
        return gson.toJson(company)
    }

    @TypeConverter
    fun stringToCompany(data: String): Company {
        val listType = object : TypeToken<Company>() {}.type
        return gson.fromJson(data, listType)
    }

}