package com.demo.assignment.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.demo.assignment.R
import com.demo.assignment.databinding.AdapterRepositoryBinding
import com.demo.assignment.model.pojo.Item
import com.demo.assignment.util.load
import com.demo.assignment.util.visible

class ItemAdapter(private val context: Context, private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_repository, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repoItem = items[holder.adapterPosition]
        repoItem.owner?.let {
                holder.binding.ivAvatar.load(it.avatar_url)
        }
        holder.binding.tvName.text = repoItem.name
        if (repoItem.isSelected){
            holder.binding.clItem.setBackgroundColor(ContextCompat.getColor(context, R.color.item_selected))
        }else{
            holder.binding.clItem.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        holder.binding.clItem.setOnClickListener {
            repoItem.isSelected = !repoItem.isSelected
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterRepositoryBinding.bind(itemView)
    }

}