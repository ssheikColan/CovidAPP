package com.example.covidapp.api

import com.example.covidapp.common.models.CountriesResponse
import com.example.covidapp.common.models.GraphModel
import com.example.covidapp.common.models.SummaryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaService {
//    @GET("countries/?sort=country")
//    suspend fun getCountryList(): Response<List<CountriesResponse>>
//
//    @GET("countries/{country}")
//    suspend fun getCountryInfo(
//        @Path("country") country: String
//    ): Response<CountriesResponse>
//

    @GET("summary")
    suspend fun getCountryList(): Response<SummaryModel>

    @GET("stats")
    suspend fun getGraphList(): Response<GraphModel>

    @GET("summary")
    suspend fun getCountryInfo(
        @Path("country") country: String
    ): Response<SummaryModel>
}