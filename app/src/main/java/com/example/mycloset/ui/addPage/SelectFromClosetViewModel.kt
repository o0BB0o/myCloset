package com.example.mycloset.ui.addPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mycloset.database.ItemsRepository


class SelectFromClosetViewModel(application:Application) : AndroidViewModel(application) {
    init {
        ItemsRepository.initialize(application)
    }
    private val itemRepository = ItemsRepository.get()
    val items = when(addPageFragment.add_categoryPassed) {
        1 -> itemRepository.getTop()
        2 -> itemRepository.getAcc()
        3 -> itemRepository.getBottom()
        4 -> itemRepository.getShoes()
        else -> itemRepository.getAllItems()
    }
}
