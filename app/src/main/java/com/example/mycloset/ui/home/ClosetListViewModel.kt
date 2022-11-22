package com.example.mycloset.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.mycloset.database.Items
import com.example.mycloset.database.ItemsRepository

class ClosetListViewModel(application: Application) : AndroidViewModel(application) {
    init {
        ItemsRepository.initialize(application)
    }

    private val itemRepository = ItemsRepository.get()
    val items = when(HomeFragment.categoryPassed) {
        1 -> itemRepository.getTop()
        2 -> itemRepository.getAcc()
        3 -> itemRepository.getBottom()
        4 -> itemRepository.getShoes()
        else -> itemRepository.getAllItems()
    }

}