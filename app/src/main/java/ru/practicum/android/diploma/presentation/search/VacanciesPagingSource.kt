package ru.practicum.android.diploma.presentation.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

class VacanciesPagingSource(
    private val repository: SearchRepository,
    private val params: Map<String, String>
) : PagingSource<Int, Vacancy>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val currentParams = this.params.toMutableMap()

            currentParams["page"] = nextPageNumber.toString()

            val response = repository.vacanciesPagination(currentParams)

            val (vacancies, pages) = when (response) {
                is Resource.Success -> {
                    (response.data as SearchingVacancies).vacancies to response.data.pages
                }

                else -> {
                    emptyList<Vacancy>() to 0
                }
            }

            val nextPage = if (pages - nextPageNumber <= 0) null else nextPageNumber + 1

            return LoadResult.Page(
                data = vacancies,
                prevKey = null, // Only paging forward.
                nextKey = nextPage
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
