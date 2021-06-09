package com.example.rest.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rest.Constants
import com.example.rest.databinding.FragmentRestBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    private var _binding: FragmentRestBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GifViewModel by viewModel()
    private val adapter by lazy {
        GifAdapter {
            val intent = Intent(context, GifDetailActivity::class.java)
            intent.putExtra(Constants.CURRENT_GIF_URL, it.images.original.url)
            intent.putExtra(Constants.CURRENT_GIF_TITLE, it.title)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestBinding.inflate(inflater, container, false)
        val view = binding.root
        initSearchView()
        observeConnectionException()
        initRecyclerView()
        initTrendGifs()
        return view
    }

    private fun initRecyclerView() {
        recyclerView = binding.gifRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun initSearchView() {
        searchView = binding.searchView
        searchView.setOnQueryTextListener(this)
    }

    private fun initTrendGifs() {
        viewModel.getTrendGifs()
        viewModel.gifTrendResponse.observe(viewLifecycleOwner, Observer { main ->
            main.data.let {
                adapter.setData(it)
            }
        })
    }

    private fun observeConnectionException() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            binding.errorTv.text = getString(it)
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            initSearchGifs(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            initSearchGifs(newText)
        }
        return true
    }

    private fun initSearchGifs(query: String) {
        viewModel.getGifs(query)
        viewModel.gifResponse.observe(viewLifecycleOwner, Observer { main ->
            main.data.let { adapter.setData(it) }
        })
    }
}
