package com.example.mycloset.ui.addPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.mycloset.R
import com.example.mycloset.databinding.FragmentAddpageBinding
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class addPageFragment : Fragment() {

    private var _binding: FragmentAddpageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(addPageViewModel::class.java)

        _binding = FragmentAddpageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.addNewButton.setOnClickListener{
            Navigation.findNavController(root).navigate(R.id.action_navigation_addPage_to_navigation_addnewItem)
        }
        val textView: TextView = binding.textAddPage
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}