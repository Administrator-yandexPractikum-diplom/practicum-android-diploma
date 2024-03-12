package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.industries.IndustriesViewModel
import ru.practicum.android.diploma.ui.country.CountryViewModel
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.filters.FilterViewModel
import ru.practicum.android.diploma.ui.region.RegionViewModel
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.similarvacancies.viewmodel.SimilarViewModel
import ru.practicum.android.diploma.ui.vacancydetail.viewmodel.DetailViewModel
import ru.practicum.android.diploma.ui.workplace.WorkplaceViewModel

val viewModelModule = module {

    viewModel {
        DetailViewModel(get(), get())
    }

    viewModel {
        FavoriteViewModel(get())
    }
    viewModel {
        WorkplaceViewModel()
    }

    viewModel {
        IndustriesViewModel(get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        CountryViewModel(get())
    }

    viewModel {
        RegionViewModel(get())
    }

    viewModel {
        SimilarViewModel(get())
    }

    viewModel {
        FilterViewModel(androidContext())
    }
}
