package com.march.xml.template.home

import androidx.recyclerview.widget.RecyclerView
import com.march.xml.template.databinding.ItemContentBinding

class HomeContentViewHolder(
    private val binding: ItemContentBinding,
    private val itemClick: (Int) -> (Unit),
    private val deleteBtnClick: (Int) -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(text: String, position: Int) {
        with(binding) {
            tvMemoContent.text = text
            root.setOnClickListener {
                itemClick(position)
            }
            btnMemoDelete.setOnClickListener {
                deleteBtnClick(position)

            }
        }
    }
}