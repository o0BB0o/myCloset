package com.example.mycloset.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.mycloset.database.Items
import com.example.mycloset.database.ItemsRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    init {
        ItemsRepository.initialize(application)
    }
    private val itemRepository = ItemsRepository.get()
    fun deleteItem(item: Items) {
        itemRepository.deleteItem(item)
    }
}