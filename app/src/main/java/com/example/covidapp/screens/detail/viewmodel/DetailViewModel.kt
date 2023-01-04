package com.example.covidapp.screens.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.covidapp.common.models.CountriesResponse
import com.example.covidapp.common.models.SummaryModel
import com.example.covidapp.repository.Repository
import com.example.covidapp.common.utils.NetworkHelper
import com.example.covidapp.common.utils.Source
import com.example.covidapp.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val countryData = MutableLiveData<Source<SummaryModel>>()

    val _countryLiveData = MutableLiveData<String>()
    private val countryLiveData: LiveData<String> get() = _countryLiveData

    private val id = countryLiveData.value


    override fun getData() {
        viewModelScope.launch {

            countryData.postValue(
                Source.loading(
                    null
                )
            )

            if (networkHelper.isInternetAvailable()) {

                if (id != null) {

                    repository.getCountryInfo(id).let {

                        if (it.isSuccessful) {
                            countryData.postValue(
                                Source.success(
                                    it.body()
                                )
                            )

                        } else {

                            countryData.postValue(
                                Source.error(
                                    it.errorBody().toString(),
                                    null
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}