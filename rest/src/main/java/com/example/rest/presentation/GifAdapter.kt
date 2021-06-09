package com.example.rest.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.rest.data.model.Data
import com.example.rest.databinding.GifItemBinding

class GifAdapter(private val onClick: (Data) -> Unit) :
    RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    private var gifList = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = GifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
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
            .into(holder.binding.gifElement)
        holder.binding.gifElement.setOnClickListener {
            onClick(currentGif)
        }
    }

    fun setData(gifList: List<Data>) {
        this.gifList = gifList
        notifyDataSetChanged()
    }

    class GifViewHolder(val binding: GifItemBinding) : RecyclerView.ViewHolder(binding.root)
}
