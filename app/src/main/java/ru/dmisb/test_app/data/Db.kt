package ru.dmisb.test_app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = true
)
abstract class Db : RoomDatabase() {
    abstract fun productDao() : ProductDao
}