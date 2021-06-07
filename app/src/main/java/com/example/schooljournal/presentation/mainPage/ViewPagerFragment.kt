package com.example.schooljournal.presentation.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.schooljournal.R
import com.example.schooljournal.fragments
import com.example.schooljournal.presentation.Navigation
import com.example.schooljournal.presentation.mainPage.adapters.ViewPagerAdapter
import com.example.schooljournal.presentation.scheduleCreateView.ScheduleCreateFragment
import kotlinx.android.synthetic.main.fragment_view_pager.view.*
import java.util.ArrayList

class ViewPagerFragment : Fragment() {

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragments = createFragments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
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
        view.view_pager.adapter = adapter
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