package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.favorite.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.data.industries.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.vacancydetail.DetailRepositoryImpl
import ru.practicum.android.diploma.data.vacancylist.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.industries.impl.IndustriesInteractorImpl

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }

    single<DetailRepository> {
        DetailRepositoryImpl(get())
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }
}
