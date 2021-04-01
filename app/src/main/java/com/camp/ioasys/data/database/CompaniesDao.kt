package com.camp.ioasys.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CompaniesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanies(companiesEntity: CompaniesEntity)

    @Query("SELECT * FROM companies_table")
    fun readCompanies(): Flow<List<CompaniesEntity>>

}