package com.kimdo.flowsampleapp.presentation.coin_list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kimdo.flowsampleapp.databinding.ListItemBinding
import com.kimdo.flowsampleapp.domain.model.Coin

class MyListAdapter(private var listener: OnItemClickListener) : RecyclerView.Adapter<MyListAdapter.ListViewHolder>() {

    private val _lists = ArrayList<Coin>()

    fun resetLists(newLists: List<Coin>) {
        _lists.clear()
        _lists.addAll( newLists )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflator = LayoutInflater.from( parent.context )
        val binding = ListItemBinding.inflate( inflator, parent, false )
        return ListViewHolder( binding )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.textLeft.text = _lists[position].name
        holder.binding.textRight.text = _lists[position].symbol

        holder.binding.linearContainer.setOnClickListener {
            listener.onItemClick(it, _lists[position], position )
        }
    }

    override fun getItemCount(): Int {
        return _lists.size
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, data:Coin, pos: Int)
    }

    inner class ListViewHolder(var binding:ListItemBinding) : RecyclerView.ViewHolder( binding.root)
    companion object {
        val TAG = "ListAdapter"
    }
}