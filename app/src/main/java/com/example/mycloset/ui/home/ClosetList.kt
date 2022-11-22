package com.example.mycloset.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycloset.R
import com.example.mycloset.database.Items
import com.example.mycloset.databinding.FragmentTagsBinding
import kotlinx.android.synthetic.main.card_item.*
import kotlinx.android.synthetic.main.card_item.view.*
import java.io.File
import com.squareup.picasso.Picasso

class ClosetList : Fragment() {
    private val picasso = Picasso.get()

    companion object {
        fun newInstance() = ClosetList()
        var itemPassed = Items()
    }

    private lateinit var viewModel: ClosetListViewModel
    private lateinit var itemsRecycler: RecyclerView
    private lateinit var adapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_closet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClosetListViewModel::class.java)
        adapter=ItemsAdapter()
        itemsRecycler = view.findViewById(R.id.items_view)
        itemsRecycler.layoutManager = LinearLayoutManager(context)
        itemsRecycler.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.updateWords(it)
        })
    }

    private inner class ItemsViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        private lateinit var items: Items
        private val itemTextView: TextView = view.item_Name
        private val categoryTextView: TextView = view.item_cate
        private val photoView = view.item_Img

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemPassed = items
        }

        fun bind(items: Items) {
            this.items = items
            itemTextView.text = items.name
            categoryTextView.text = items.category
            if(File(context?.filesDir, items.photoFileName).exists()){
                picasso.load(File(context?.filesDir, items.photoFileName))
                    .resize(48,48)
                    .into(photoView)
            }
        }
    }

    private inner class ItemsAdapter : RecyclerView.Adapter<ItemsViewHolder>() {
        var items: List<Items> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
            val view = layoutInflater.inflate(R.layout.card_item, parent, false)
            return ItemsViewHolder(view)
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
            holder.bind(items[position])
        }

        fun updateWords(newItems: List<Items>) {
            this.items = newItems
            notifyDataSetChanged()
        }

        fun getWordAtPosition(position: Int): Items {
            return items[position]
        }
    }

}