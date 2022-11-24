package com.example.mycloset.ui.home

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mycloset.R
import com.example.mycloset.database.ItemsRepository
import com.example.mycloset.ui.home.ClosetList.Companion.itemPassed
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import java.io.File
import com.example.mycloset.database.Items

class DetailFragment : Fragment() {
    private val picasso = Picasso.get()
    //TODO add text to this page
    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if(File(context?.filesDir, itemPassed.photoFileName).exists()){
            picasso.load(File(context?.filesDir, itemPassed.photoFileName))
                .resize(350,350)
                .into(detail_Img)
        }
        deleteButton.setOnClickListener{
            viewModel.deleteItem(itemPassed)
            itemDeletedAlert(itemPassed)
        }
    }

    fun itemDeletedAlert(items: Items) {
        val msg = resources.getString(R.string.deleted_alert, items.name)
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle(R.string.alert)
            setMessage(msg)
            setPositiveButton("OK", null)
            show()
        }
    }

}