package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.request.CountryRequest
import ru.practicum.android.diploma.data.request.RegionByIdRequest
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.response.AreasResponse
import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailRequest
import ru.practicum.android.diploma.data.vacancylist.dto.SimilarVacanciesRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest

class RetrofitNetworkClient(
    private val context: Context,
    private val jobVacancySearchApi: JobVacancySearchApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ResponseCodes.NO_CONNECTION }
        }

        if (dto !is VacanciesSearchRequest
            && dto !is DetailRequest
            && dto !is IndustriesRequest
            && dto !is CountryRequest
            && dto !is RegionByIdRequest
            && dto !is SimilarVacanciesRequest
        ) {
            return Response().apply { resultCode = ResponseCodes.ERROR }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesSearchRequest -> async {
                        jobVacancySearchApi.getFullListVacancy(dto.queryMap) }
                    is SimilarVacanciesRequest -> async {
                        jobVacancySearchApi.getSimilarVacancies(dto.id, dto.pageNumber) }
                    is IndustriesRequest -> async { getIndustries() }
                    is CountryRequest -> async { getAreas() }
                    is RegionByIdRequest -> async { jobVacancySearchApi.getAreaId(dto.countryId) }
                    else -> async { jobVacancySearchApi.getVacancyDetail((dto as DetailRequest).id) }
                }.await()
                response.apply { resultCode = ResponseCodes.SUCCESS }
            } catch (e: Throwable) {
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        }
    }

    private suspend fun getIndustries(): Response {
        return try {
            val industries = jobVacancySearchApi.getAllIndustries()
            if (industries.isNotEmpty()) {
                IndustriesResponse(industries).apply { resultCode = ResponseCodes.SUCCESS }
            } else {
                // Создание экземпляра вашего класса Response с кодом ошибки сервера
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        } catch (e: Exception) {
            Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
        }
    }

    private suspend fun getAreas(): Response {
        return try {
            val areas = jobVacancySearchApi.getAllAreas()
            if (areas.isNotEmpty()) {
                AreasResponse(areas).apply { resultCode = ResponseCodes.SUCCESS }
            } else {
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        } catch (e: Exception) {
            Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
