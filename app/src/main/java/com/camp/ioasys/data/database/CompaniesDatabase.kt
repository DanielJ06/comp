package com.camp.ioasys.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CompaniesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CompaniesTypeConverter::class)
abstract class CompaniesDatabase: RoomDatabase() {

    abstract fun companiesDao(): CompaniesDao

}