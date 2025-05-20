package com.march.xml.template.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.march.xml.template.R
import com.march.xml.template.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "binding object is not initialized" }

    private var _adapter: HomeAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { "adapter object is not initialized" }


    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeNewMemoList()
    }

    private fun initAdapter() {
        _adapter = HomeAdapter(
            addBtnClick = ::initAddBtnClickListener,
            itemClick = ::initItemClickListener,
            deleteBtnClick = ::initDeleteBtnClickListener
        )
        binding.rvMemo.adapter = adapter
    }

    private fun initAddBtnClickListener() {

    }

    private fun initItemClickListener(position: Int) {

    }

    private fun initDeleteBtnClickListener(position: Int) {

    }

    private fun observeNewMemoList() {
        viewModel.memoList.flowWithLifecycle(lifecycle).onEach {
            adapter.addItemList(it)
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }
}