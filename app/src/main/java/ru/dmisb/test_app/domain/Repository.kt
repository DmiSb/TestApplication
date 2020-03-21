package ru.dmisb.test_app.domain

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dmisb.test_app.data.Db
import ru.dmisb.test_app.data.Product

object Repository {

    private lateinit var db: Db

    fun init(context: Context) {
        db = Room.databaseBuilder(context, Db::class.java, "test.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun selectAllProduct(search: String) = db.productDao().select(search)

    suspend fun insertProduct(product: Product) = withContext(Dispatchers.IO) {
        db.productDao().insert(product)
    }

    suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
        db.productDao().delete(product)
    }
}