package com.example.covidapp.repository

import com.example.covidapp.api.ApiHelper
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
class Repository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getCountryList() = apiHelper.getCountryList()
    suspend fun getGraphList() = apiHelper.getGraphList()

    suspend fun getCountryInfo(country: String) = apiHelper.getCountryInfo(country)


}