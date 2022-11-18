package com.example.mycloset.ui.addnewitem

import android.app.Application
import android.content.ClipData.Item
import androidx.lifecycle.AndroidViewModel
import com.example.mycloset.database.Items
import com.example.mycloset.database.ItemsRepository

class addNewItemViewModel(application: Application) : AndroidViewModel(application) {
    init {
        ItemsRepository.initialize(application)
    }

    private val itemRepository = ItemsRepository.get()

    fun insert(items: Items){
        itemRepository.insert(items)
    }
}