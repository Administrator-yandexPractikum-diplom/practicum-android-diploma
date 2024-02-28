package ru.practicum.android.diploma.domain.models.main.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.main.SearchInteractor
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies

class SearchInteractorImpl(
    val repository: SearchRepository
) : SearchInteractor {

    override suspend fun searchTrack(queryMap: Map<String, String>): Flow<Pair<SearchingVacancies?, Int?>> {
        return repository.makeRequest(queryMap).map {
            when (it) {
                is Resource.Success -> {
                    Pair(it.data, null)
                }

                is Resource.Error -> {
                    Pair(null, it.message)
                }
            }
        }
    }
}
