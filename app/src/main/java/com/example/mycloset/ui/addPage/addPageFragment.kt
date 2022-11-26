package com.example.mycloset.ui.addPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycloset.R
import com.example.mycloset.database.Items
import com.example.mycloset.databinding.FragmentAddpageBinding
import com.example.mycloset.ui.addPage.SelectFromCloset.Companion.daily_itemPassed
import com.example.mycloset.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.wearingtoday_items.view.*
import java.io.File


class addPageFragment : Fragment() {
    private val picasso = Picasso.get()
    private lateinit var itemsRecycler: RecyclerView
    private lateinit var adapter: ItemsAdapter
    private val theMap = mapOf("Top" to 1, "Bottom" to 3, "Shoes" to 4, "Accessory" to 2)
    private var _binding: FragmentAddpageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        var add_categoryPassed = 0
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val apViewModel =
                ViewModelProvider(this).get(addPageViewModel::class.java)

        _binding = FragmentAddpageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.addNewButton.setOnClickListener{
            Navigation.findNavController(root).navigate(R.id.action_navigation_addPage_to_navigation_addnewItem)
        }
        binding.finishDaily.setOnClickListener{
            Navigation.findNavController(root).navigateUp()
        }

        val navView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navView.visibility = View.VISIBLE
        val textView: TextView = binding.textAddPage
        adapter=ItemsAdapter()
        itemsRecycler = root.findViewById(R.id.today_wear)
        itemsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        itemsRecycler.adapter = adapter
        apViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        apViewModel.temp.observe(viewLifecycleOwner){
            adapter.update(it)
        }
        return root
    }

    private inner class ItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var items: String
        private val itemImageButton: ImageButton = view.itemButton
        private val itemText: TextView = view.tempText

        init {
            itemImageButton.setImageResource(R.drawable.ic_baseline_insert_drive_file_130)
        }

        fun bind(s: String) {
            items = s
            itemText.text = s
            itemImageButton.setOnClickListener{
                add_categoryPassed = theMap.get(s)!!
                nav_host_fragment_activity_main.findNavController().navigate(R.id.action_navigation_addPage_to_selectFromCloset)
            }
            if(File(context?.filesDir, daily_itemPassed[theMap.get(s)!!-1].photoFileName).exists()){
                picasso.load(File(context?.filesDir, daily_itemPassed[theMap.get(s)!!-1].photoFileName))
                    .resize(300,300)
                    .into(itemImageButton)
            }
        }
    }

    private inner class ItemsAdapter : RecyclerView.Adapter<ItemsViewHolder>() {
        var s: List<String> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
            val view = layoutInflater.inflate(R.layout.wearingtoday_items, parent, false)
            return ItemsViewHolder(view)
        }

        override fun getItemCount() = s.size

        override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
            holder.bind(s[position])
        }

        fun update(newList:List<String>) {
            this.s = newList
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        daily_itemPassed = mutableListOf(Items(), Items(), Items(), Items())
        _binding = null
    }
}