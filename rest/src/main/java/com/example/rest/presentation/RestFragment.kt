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
import com.example.rest.R
import kotlinx.android.synthetic.main.fragment_rest.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

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
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rest, container, false)
        initSearchView(view)
        observeConnectionException(view)
        initRecyclerView(view)
        initTrendGifs(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.gif_rv
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun initSearchView(view: View) {
        searchView = view.search_view
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    private fun initTrendGifs(view: View) {
        viewModel.getTrendGifs()
        viewModel.gifTrendResponse.observe(viewLifecycleOwner, Observer { main ->
            main.data.let {
                adapter.setData(it)
            }
        })
    }

    private fun observeConnectionException(view: View) {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            view.error_tv.text = getString(it)
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
