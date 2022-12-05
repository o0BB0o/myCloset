package com.example.mycloset.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mycloset.MainActivity
import com.example.mycloset.R
import com.example.mycloset.R.id.costAmount
import com.example.mycloset.database.ItemsRepository
import com.example.mycloset.ui.home.ClosetList.Companion.itemPassed
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import java.io.File
import com.example.mycloset.database.Items
import com.example.mycloset.ui.tags.TagsFragment
import kotlinx.android.synthetic.main.activity_main.*

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
        costAmount.setText(itemPassed.price.toString())

        deleteButton.setOnClickListener{
            viewModel.deleteItem(itemPassed)
            itemDeletedAlert(itemPassed)
        }

        tagButton.setOnClickListener{
            TagsFragment.lastItemTagged= itemPassed
            Toast.makeText(context, "Item Tagged!", 3).show()
        }
    }

    fun itemDeletedAlert(items: Items) {
        val msg = resources.getString(R.string.deleted_alert, items.name)
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle(R.string.alert)
            setMessage(msg)
            setPositiveButton("OK", DialogInterface.OnClickListener(function = onDelete))
            show()
        }
    }

    val onDelete = {dialog:DialogInterface, which: Int ->
        findNavController().navigateUp()
        onDestroyView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}