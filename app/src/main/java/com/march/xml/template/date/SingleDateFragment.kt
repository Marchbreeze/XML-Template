package com.march.xml.template.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.march.xml.template.R
import com.march.xml.template.databinding.FragmentSingleDateBinding
import java.time.LocalDate

class SingleDateFragment : Fragment() {
    private var _binding: FragmentSingleDateBinding? = null
    private val binding: FragmentSingleDateBinding
        get() = requireNotNull(_binding) { "binding object is not initialized" }

    private val viewModel: SingleDateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_single_date, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDateFromBundle()
    }

    private fun getDateFromBundle() {
        val date = arguments?.getString(ARG_DATE)?.let { LocalDate.parse(it) } ?: LocalDate.now()
        viewModel.setDateText(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_DATE = "arg_date"

        fun newInstance(date: LocalDate): SingleDateFragment {
            return SingleDateFragment().apply {
                arguments = bundleOf(ARG_DATE to date.toString())
            }
        }
    }
}