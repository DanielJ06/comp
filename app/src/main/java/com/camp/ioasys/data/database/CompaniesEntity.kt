package com.camp.ioasys.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.models.Company
import com.camp.ioasys.util.Constants.Companion.COMPANIES_TABLE

@Entity(tableName = COMPANIES_TABLE)
class CompaniesEntity (
    var company: CompaniesResponse
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}