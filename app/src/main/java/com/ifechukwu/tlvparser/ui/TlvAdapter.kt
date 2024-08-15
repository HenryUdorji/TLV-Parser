package com.ifechukwu.tlvparser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ifechukwu.tlvparser.R
import com.ifechukwu.tlvparser.data.model.Tag
import com.ifechukwu.tlvparser.databinding.ItemTlvLayoutBinding

/**
 * @Author: ifechukwu.udorji
 * @Date: 8/15/2024
 */
class TlvAdapter : ListAdapter<Tag, TlvAdapter.TagViewHolder>(TagDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder =
        TagViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tlv_layout, parent, false)
        )

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTlvLayoutBinding.bind(itemView)

        fun bind(tag: Tag) {
            binding.tvTag.text = tag.tag
            binding.tvTagValue.text = tag.value
            binding.tvTagDescription.text = tag.tagDescription ?: "N/A"
            binding.tvTagLength.text = tag.length.toString()
        }
    }

    class TagDiffUtil : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.tag == newItem.tag
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }
    }
}