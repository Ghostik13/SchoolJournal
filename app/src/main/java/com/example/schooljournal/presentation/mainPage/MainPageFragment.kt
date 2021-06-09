package com.example.schooljournal.presentation.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.schooljournal.R
import com.example.schooljournal.utils.*
import com.example.schooljournal.databinding.FragmentMainPageBinding
import com.example.schooljournal.presentation.mainPage.adapters.DayAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainPageFragment : Fragment() {

    private var weekId: Int = 1

    private val viewModel: MainPageViewModel by viewModel()
    private lateinit var recyclerViewDays: RecyclerView

    private var _binding: FragmentMainPageBinding?= null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        val view = binding.root
        initRecyclerView()
        changeWeeks()
        initAddNoteFab()
        return view
    }

    private fun initAddNoteFab() {
        binding.fab.setOnClickListener {
            val dialogFragment = NoteDialogFragment()
            val bundle = Bundle()
            bundle.putInt("weekId", weekId)
            dialogFragment.arguments = bundle
            dialogFragment.show(parentFragmentManager, "Note Dialog")
        }
    }

    private fun changeWeeks() {
        if(weekId == 1) {
            binding.previousWeek.visibility = View.GONE
        }
        else if (weekId == 53) {
            binding.nextWeek.visibility = View.GONE
        }
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.nextWeek.setOnClickListener {
            viewPager?.currentItem = weekId
        }
        binding.previousWeek.setOnClickListener {
            if (weekId > 1) {
                viewPager?.currentItem = weekId - 2
            }
        }
    }

    private fun initRecyclerView() {
        val dayAdapter = DayAdapter(requireContext(), viewModel)
        recyclerViewDays = binding.recyclerView
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