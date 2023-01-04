package com.example.covidapp.common.models

data class SummaryModel(
    val Countries: List<Country>,
    val Date: String,
    val Global: Global
)