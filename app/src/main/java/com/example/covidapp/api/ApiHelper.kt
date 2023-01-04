package com.example.covidapp.api

import com.example.covidapp.common.models.CountriesResponse
import com.example.covidapp.common.models.GraphModel
import com.example.covidapp.common.models.SummaryModel
import retrofit2.Response

/**
 * Created by halilozel1903 on 11.12.2021
 */

interface ApiHelper {
    suspend fun getCountryList(): Response<SummaryModel>
    suspend fun getGraphList(): Response<GraphModel>

    suspend fun getCountryInfo(country: String): Response<SummaryModel>
}