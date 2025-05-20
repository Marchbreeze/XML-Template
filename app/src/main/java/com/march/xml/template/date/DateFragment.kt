package com.march.xml.template.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.march.xml.template.R
import com.march.xml.template.databinding.FragmentDateBinding
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateFragment : Fragment() {
    private var _binding: FragmentDateBinding? = null
    private val binding: FragmentDateBinding
        get() = requireNotNull(_binding) { "binding object is not initialized" }

    private val viewModel: DateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_date, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPagerAdapter()
        initViewPagerChangeCallback()
        initDateChangeBtnsListener()
        connectTabLayout()
        observeCurrentIndex()
    }

    private fun initViewPagerAdapter() {
        binding.vpDate.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = viewModel.dates.size
            override fun createFragment(position: Int) =
                SingleDateFragment.newInstance(viewModel.dates[position])
        }
    }

    private fun initViewPagerChangeCallback() {
        binding.vpDate.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.setCurrent(position)
                }
            }
        )
    }

    private fun initDateChangeBtnsListener() {
        binding.btnPrev.setOnClickListener { viewModel.goPrev() }
        binding.btnNext.setOnClickListener { viewModel.goNext() }
    }

    private fun connectTabLayout() {
        TabLayoutMediator(binding.tabDate, binding.vpDate) { tab, pos ->
            tab.text = viewModel.dates[pos].format(
                DateTimeFormatter.ofPattern("MM월 dd일 (E)", Locale.KOREA)
            )
        }.attach()
    }

    private fun observeCurrentIndex() {
        viewModel.currentIndex.observe(viewLifecycleOwner) { index ->
            if (binding.vpDate.currentItem != index) {
                binding.vpDate.setCurrentItem(index, true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}