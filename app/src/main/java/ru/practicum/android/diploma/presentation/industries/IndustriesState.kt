package ru.practicum.android.diploma.presentation.industries

import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal

sealed interface IndustriesState {

    data class Content(
        val industries: List<ParentIndustriesAllDeal>
    ) : IndustriesState

    data class Error(
        val errorMessage: Int
    ) : IndustriesState

    data class Empty(
        val message: Int
    ) : IndustriesState
}
