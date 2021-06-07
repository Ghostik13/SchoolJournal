package com.example.schooljournal.presentation.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.schooljournal.*
import com.example.schooljournal.presentation.mainPage.adapters.DayAdapter
import kotlinx.android.synthetic.main.fragment_main_page.view.*

class MainPageFragment : Fragment() {

    private var weekId: Int = 1

    private lateinit var viewModel: MainPageViewModel
    private lateinit var recyclerViewDays: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(WEEK_ID).let {
            if (it != null) {
                weekId = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        initRecyclerView(view)
        changeWeeks(view)
        initAddNoteFab(view)
        return view
    }

    private fun initAddNoteFab(view: View) {
        view.fab.setOnClickListener {
            val dialogFragment = NoteDialogFragment()
            val bundle = Bundle()
            bundle.putInt("weekId", weekId)
            dialogFragment.arguments = bundle
            dialogFragment.show(parentFragmentManager, "Note Dialog")
        }
    }

    private fun changeWeeks(view: View) {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        view.next_week.setOnClickListener {
            viewPager?.currentItem = weekId
        }
        view.previous_week.setOnClickListener {
            if (weekId > 1) {
                viewPager?.currentItem = weekId - 2
            }
        }
    }

    private fun initRecyclerView(view: View) {
        val dayAdapter = DayAdapter(requireContext(), viewModel)
        recyclerViewDays = view.recycler_view
        recyclerViewDays.adapter = dayAdapter
        recyclerViewDays.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel.getDays(weekId)
        viewModel.days.observe(viewLifecycleOwner, Observer { days ->
            dayAdapter.setData(days)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(weekId: Int) =
            MainPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(WEEK_ID, weekId)
                }
            }
    }
}