package com.example.mycloset.database
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(items:Items)

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Delete
    fun deleteItem(items: Items)

    @Query("SELECT * FROM item_table LIMIT 1")
    fun getAnyItem(): Array<Items>

    @Query("SELECT * FROM item_table ORDER BY id DESC")
    fun getAllItems(): LiveData<List<Items>>

    @Query("SELECT * FROM item_table WHERE category = 'Top' ")
    fun getTop():LiveData<List<Items>>

    @Query("SELECT * FROM item_table WHERE category = 'Bottom' ")
    fun getBottom():LiveData<List<Items>>

    @Query("SELECT * FROM item_table WHERE category = 'Accessory' ")
    fun getAcc():LiveData<List<Items>>

    @Query("SELECT * FROM item_table WHERE category = 'Shoes' ")
    fun getShoes():LiveData<List<Items>>

    @Update
    fun updateItem(items: Items)
}