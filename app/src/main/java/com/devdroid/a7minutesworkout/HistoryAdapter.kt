package com.devdroid.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devdroid.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.viewHolder>() {

    class viewHolder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root){
        val llHistoryItemMain =binding.llHistoryItemMain
        val tvItem =binding.tvItem
        val tvPosition =binding.tvPosition



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val date :String =items.get(position)
        holder.tvPosition.text=(position+1).toString()
        holder.tvItem.text= date
        if(position % 2==0){
            holder.llHistoryItemMain.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context,R.color.lightGray)
            )
        }else{
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

    }
}