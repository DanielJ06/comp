package com.camp.ioasys.data.database

import androidx.room.Database
import androidx.room.TypeConverters

@Database(
    entities = [CompaniesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CompaniesTypeConverter::class)
abstract class CompaniesDatabase {

    abstract fun companiesDao(): CompaniesDao

}