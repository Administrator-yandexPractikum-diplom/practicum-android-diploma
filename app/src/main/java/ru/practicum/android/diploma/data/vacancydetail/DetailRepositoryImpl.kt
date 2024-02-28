package ru.practicum.android.diploma.data.vacancydetail

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancy
import ru.practicum.android.diploma.data.converters.VacancyDetailConverter.toVacancyDetail
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailRequest
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailResponse
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail
import ru.practicum.android.diploma.domain.models.main.Vacancy

class DetailRepositoryImpl(
    private val networkClient: NetworkClient,
): DetailRepository {

    override fun searchDetailInformation(vacancyId: String): Flow<Resource<VacancyDetail>> = flow {
        val response = networkClient.doRequest(DetailRequest(vacancyId))
        Log.d("vacancyId", "ответ = ${response.resultCode}")

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    val vacancy = (response as VacancyDetailDtoResponse).toVacancyDetail()
                    Log.d("vacancyId", vacancy.name)
                    emit(Resource.Success(vacancy))
                } catch (e: Throwable) {
                    emit(Resource.Error(response.resultCode.code))
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}
