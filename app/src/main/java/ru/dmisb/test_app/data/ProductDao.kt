package ru.dmisb.test_app.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("select * from Product where lower(name) like '%'||:search||'%' order by name")
    fun select(search: String): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)
}