package com.example.rest.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.rest.R
import com.example.rest.data.model.Data
import kotlinx.android.synthetic.main.gif_item.view.*

class GifAdapter(private val onClick: (Data) -> Unit) :
    RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    private var gifList = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.gif_item,
            parent,
            false)
        return GifViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gifList.size
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val currentGif = gifList[position]
        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide
            .with(holder.itemView.context)
            .load(currentGif.images.original.url)
            .placeholder(circularProgressDrawable)
            .into(holder.itemView.gif_element)
        holder.itemView.gif_element.setOnClickListener {
            onClick(currentGif)
        }
    }

    fun setData(gifList: List<Data>) {
        this.gifList = gifList
        notifyDataSetChanged()
    }

    class GifViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
