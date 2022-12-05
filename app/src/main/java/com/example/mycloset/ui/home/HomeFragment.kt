package com.example.mycloset.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mycloset.R
import com.example.mycloset.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    companion object {
        var categoryPassed = 0
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.imageButton.setOnClickListener{
            categoryPassed = 1
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_closetList)
        }
        binding.imageButton2.setOnClickListener{
            categoryPassed = 2
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_closetList)
        }
        binding.imageButton3.setOnClickListener{
            categoryPassed = 3
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_closetList)
        }
        binding.imageButton4.setOnClickListener{
            categoryPassed = 4
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_closetList)
        }
        // TODO REPALCE THE ICONS
        binding.imageButton.setImageResource(R.drawable.shirts)
        binding.imageButton2.setImageResource(R.drawable.pants)
        binding.imageButton3.setImageResource(R.drawable.shoes)
        binding.imageButton4.setImageResource(R.drawable.other)
        /*
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}