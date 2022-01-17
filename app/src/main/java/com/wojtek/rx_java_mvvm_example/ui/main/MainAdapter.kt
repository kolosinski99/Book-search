package com.wojtek.rx_java_mvvm_example.ui.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.wojtek.rx_java_mvvm_example.data.googleBook.VolumeInfo
import com.wojtek.rx_java_mvvm_example.data.googleBook.VolumeInfo.BookInfo
import com.wojtek.rx_java_mvvm_example.databinding.BookItemBinding

class MainAdapter :
    ListAdapter<VolumeInfo, MainAdapter.MyViewHolder>(BOOK_COMPARATOR) {
    inner class MyViewHolder(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: BookInfo) {
            binding.apply {

                item.imageLinks?.smallThumbnail?.let {
                    Glide.with(itemView)
                        .load(it)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .override(100, 100)
                        .into(bookImg)
                }

                bookTitle.text = item.title
                bookPublisher.text = item.publisher
                bookDescription.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item.bookInfo)
    }

    companion object {
        val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<VolumeInfo>() {
            override fun areItemsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo) =
                oldItem.bookInfo.title == newItem.bookInfo.title

            override fun areContentsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo) =
                oldItem == newItem

        }
    }
}