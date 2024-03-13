package ru.practicum.android.diploma.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.region.RegionInteractor

class RegionViewModel(
    val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<RegionState>()
    fun observeState(): LiveData<RegionState> = stateLiveData

    fun loadRegion(regionId: String) {
        if (regionId.isEmpty()) {
            // Выбрать значение по умолчанию или выполнить другие действия
            renderState(RegionState.Empty)
            return
        }

        renderState(RegionState.Loading)
        viewModelScope.launch {
            regionInteractor.searchRegion(regionId)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(industriesDetail: Country?, errorMessage: Int?) {
        when {
            errorMessage != null -> {
                renderState(
                    RegionState.Error(
                        errorMessage = R.string.nothing_found
                    )
                )
            }

            else -> {
                renderState(
                    RegionState.Content(
                        regionId = industriesDetail!!
                    )
                )
            }
        }
    }

    fun renderState(regionState: RegionState) {
        stateLiveData.postValue(regionState)
    }
}
