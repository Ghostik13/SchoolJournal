package com.example.schooljournal.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.schooljournal.R
import com.example.schooljournal.fragments
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val adapter =
            ViewPagerAdapter(
                fragments,
                requireActivity().supportFragmentManager,
                lifecycle
            )
        view.view_pager.adapter = adapter
        return view
    }

}