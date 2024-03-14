package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

interface SimilarRepository {
    suspend fun similarVacanciesPagination(vacancyId: String, page: Int): Resource<SearchingVacancies>
}
