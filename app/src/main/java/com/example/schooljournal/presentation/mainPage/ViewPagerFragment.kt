package com.example.schooljournal.presentation.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.schooljournal.databinding.FragmentViewPagerBinding
import com.example.schooljournal.utils.*
import com.example.schooljournal.presentation.mainPage.adapters.ViewPagerAdapter
import java.util.ArrayList

class ViewPagerFragment : Fragment() {

    private lateinit var adapter: ViewPagerAdapter

    private var _binding: FragmentViewPagerBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragments = createFragments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        adapter =
            ViewPagerAdapter(
                fragments,
                requireActivity().supportFragmentManager,
                lifecycle
            )
        binding.viewPager.adapter = adapter
        return view
    }

    private fun createFragments(): ArrayList<Fragment> {
        val fragments = arrayListOf<Fragment>()
        for (i in 1..52) {
            fragments.add(MainPageFragment.newInstance(i))
        }
        return fragments
    }
}