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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycloset.R
import com.example.mycloset.databinding.FragmentAddpageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.wearingtoday_items.view.*


class addPageFragment : Fragment() {

    private lateinit var itemsRecycler: RecyclerView
    private lateinit var adapter: ItemsAdapter

    private var _binding: FragmentAddpageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            //TODO SELECT AN ITEM AND REPLACE THE IMG
            itemImageButton.setImageResource(R.drawable.ic_baseline_insert_drive_file_130)
            itemImageButton.setOnClickListener{
                Toast.makeText(context, "yeah", 1).show()
            }
        }

        fun bind(s: String) {
            // TODO SAME AS ABOVE
            this.items = s
            itemText.text = s
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
        _binding = null
    }
}