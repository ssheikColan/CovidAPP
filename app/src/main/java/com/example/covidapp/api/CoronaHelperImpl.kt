package com.example.covidapp.api

import com.example.covidapp.common.models.CountriesResponse
import com.example.covidapp.common.models.GraphModel
import com.example.covidapp.common.models.SummaryModel
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
class CoronaHelperImpl @Inject constructor(private val service: CoronaService) : ApiHelper {
    override suspend fun getCountryList(): Response<SummaryModel> =
        service.getCountryList()

    override suspend fun getGraphList(): Response<GraphModel> =
        service.getGraphList()


    override suspend fun getCountryInfo(country: String): Response<SummaryModel> =
        service.getCountryInfo(country)
}