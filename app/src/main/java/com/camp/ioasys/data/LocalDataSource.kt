package com.camp.ioasys.data

import com.camp.ioasys.data.database.CompaniesDao
import com.camp.ioasys.data.database.CompaniesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val companiesDao: CompaniesDao
) {

    fun readDatabase(): Flow<List<CompaniesEntity>> {
        return companiesDao.readCompanies()
    }

    suspend fun insertCompanies(companiesEntity: CompaniesEntity) {
        companiesDao.insertCompanies(companiesEntity)
    }

}