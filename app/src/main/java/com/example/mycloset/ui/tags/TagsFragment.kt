package com.example.mycloset.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycloset.database.Items
import com.example.mycloset.databinding.FragmentTagsBinding
import com.example.mycloset.ui.home.ClosetList
import com.example.mycloset.ui.home.DetailViewModel
import kotlinx.android.synthetic.main.fragment_tags.*
import java.io.File
import com.squareup.picasso.Picasso

class TagsFragment : Fragment() {

    private var _binding: FragmentTagsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val picasso = Picasso.get()
    companion object {
        var lastItemTagged = Items()
    }
    private lateinit var viewModel: TagsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val tagsViewModel =
                ViewModelProvider(this).get(TagsViewModel::class.java)

        _binding = FragmentTagsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        tagsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(File(context?.filesDir, lastItemTagged.photoFileName).exists()){
            picasso.load(File(context?.filesDir, lastItemTagged.photoFileName))
                .resize(350,350)
                .into(tag_photo)
        }
        val priceText = "ValueSaved: "+(lastItemTagged.price/10).toString()
        cumulative_saved.setText(priceText)
        tag_name.setText(lastItemTagged.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}