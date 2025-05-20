package com.march.xml.template.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.march.xml.template.databinding.ItemContentBinding
import com.march.xml.template.databinding.ItemHeaderBinding

/*
 * ListAdapter를 적용하지 않은 HomeAdapter 버전
 */
class HomeOldAdapter(
    private val addBtnClick: () -> Unit,
    private val deleteBtnClick: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        return when (viewType) {
            VIEW_TYPE_HEADER -> HomeHeaderViewHolder(
                ItemHeaderBinding.inflate(inflater, parent, false),
                addBtnClick,
            )

            VIEW_TYPE_CONTENT -> HomeContentViewHolder(
                ItemContentBinding.inflate(inflater, parent, false),
                deleteBtnClick
            )

            else -> throw ClassCastException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeHeaderViewHolder) {
            holder.onBind()
        }
        if (holder is HomeContentViewHolder) {
            val itemPosition = position - HEADER_COUNT
            holder.onBind(itemList[itemPosition], itemPosition)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size + HEADER_COUNT
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_CONTENT
        }
    }

    fun addItemList(newItems: List<String>) {
        this.itemList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position + HEADER_COUNT)
        notifyItemRangeChanged(position + HEADER_COUNT, itemCount)
    }

    fun submitList(newItems: List<String>) {
        itemList.clear()
        itemList.addAll(newItems)
        notifyDataSetChanged()
    }

    companion object {
        private const val HEADER_COUNT = 1

        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_CONTENT = 1
    }
}