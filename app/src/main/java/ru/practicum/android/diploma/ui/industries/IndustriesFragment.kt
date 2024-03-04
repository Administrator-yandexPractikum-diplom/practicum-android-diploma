package ru.practicum.android.diploma.ui.industries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.data.response.Industries
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.presentation.industries.IndustriesViewModel


class IndustriesFragment : Fragment() {

    private val viewModel by viewModel<IndustriesViewModel>()
    private lateinit var binding: FragmentIndustryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val industry = ArrayList<Industries>()

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesState.Content -> industry.addAll(state.vacancyDetail)
                is IndustriesState.Error -> ""
                is IndustriesState.Loading -> ""
            }
        }

//        industry.add(Industries("1", "Отрасль 1"))
//        industry.add(Industries("2", "Отрасль 2"))
//        industry.add(Industries("3", "Отрасль 3"))
//        industry.add(Industries("4", "Отрасль 4"))
//        industry.add(Industries("5", "Отрасль 5"))
//        industry.add(Industries("6", "Отрасль 6"))
//        industry.add(Industries("7", "Отрасль 7"))
//        industry.add(Industries("8", "Отрасль 8"))
//        industry.add(Industries("9", "Отрасль 9"))
//        industry.add(Industries("10", "Отрасль 10"))
//        industry.add(Industries("11", "Отрасль 11"))
//        industry.add(Industries("12", "Отрасль 12"))
//        industry.add(Industries("13", "Отрасль 13"))

        val adapter = IndustriesAdapter(industry)
        binding.regionRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.adapter = adapter
    }
}
