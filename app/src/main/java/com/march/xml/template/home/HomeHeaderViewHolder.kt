package com.march.xml.template.home

import androidx.recyclerview.widget.RecyclerView
import com.march.xml.template.databinding.ItemHeaderBinding

class HomeHeaderViewHolder(
    private val binding: ItemHeaderBinding,
    private val addBtnClick: () -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind() {
        binding.btnAddMemo.setOnClickListener {
            addBtnClick()
        }
    }
}