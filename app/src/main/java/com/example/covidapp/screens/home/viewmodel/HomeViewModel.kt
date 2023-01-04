package com.example.covidapp.screens.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.covidapp.common.models.CountriesResponse
import com.example.covidapp.common.models.GraphModel
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
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _countryData = MutableLiveData<Source<SummaryModel>>()
    val countryData: LiveData<Source<SummaryModel>> get() = _countryData

    private val _graphData = MutableLiveData<Source<GraphModel>>()
    val graphData: LiveData<Source<GraphModel>> get() = _graphData

    init {
        getData()
        getGraphData()
    }

    override fun getData() {
        viewModelScope.launch {
            _countryData.postValue(Source.loading(null))

            if (networkHelper.isInternetAvailable()) {
                repository.getCountryList().let { response ->
                    if (response.isSuccessful) {
                        _countryData.postValue(Source.success(response.body()))
                    } else {
                        _countryData.postValue(Source.error(response.errorBody().toString(), null))
                    }
                }
            } else {
                _countryData.postValue(Source.error(MESSAGE_ERROR, null))
            }
        }
    }




    private fun getGraphData(){
        viewModelScope.launch {
            _graphData.postValue(Source.loading(null))

            if (networkHelper.isInternetAvailable()){
                repository.getGraphList().let { response ->
                if (response.isSuccessful){
                    _graphData.postValue(Source.success(response.body()))
                } else{
                    _graphData.postValue(Source.error(response.errorBody().toString(), null))
                }

                }
            } else{
                _graphData.postValue(Source.error(MESSAGE_ERROR, null))
            }
        }

    }

    companion object {
        private const val MESSAGE_ERROR = "Internet connection not found"
    }
}